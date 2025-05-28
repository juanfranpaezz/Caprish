package Caprish.Service.imp.mail;

import Caprish.Model.imp.mail.SentEmail;
import Caprish.Repository.interfaces.mail.SentEmailRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import com.google.api.client.util.Base64;


@Service
public class GmailService {

    private static final String APPLICATION_NAME = "Gmail API Java";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    // inyeccion de repo
    @Autowired
    private SentEmailRepository sentEmailRepository;

    public void sendEmailWithName(String to, String subject, String name, MultipartFile file) throws Exception {
        String htmlBody = generateHtmlContent(name);// primero se llama al constructor de thymeleaf
        sendEmail(to, subject, htmlBody, file);
    }

    // "constructor" del correo
    private static MimeMessage createEmail(String to, String from, String subject, String htmlBody, File attachment)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlBody, "text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(htmlPart);

        if (attachment != null && attachment.exists()) {
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);
            attachmentPart.setFileName(attachment.getName());
            multipart.addBodyPart(attachmentPart);
        }
        email.setContent(multipart);
        return email;
    }

    public boolean isValidEmailFormat(String email) {// metodo para validar que lo que ingresa es un mail
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // metodo que genera thymeleaf
    public static String generateHtmlContent(String name) {
        TemplateEngine templateEngine = new TemplateEngine();
        Context context = new Context();
        context.setVariable("name", name);

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine.process("emailTemplate", context);
    }

    // autenticador de credenciales de la api de gmail
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Archivo de credenciales no encontrado: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");// el mail que creamos lo tenemos que añadir al proyecto de google cloud si no no va a tener los permisos para usar la api
    }
    // metodo que crea un archivo temporal vacio (por si se quiere mandar una foto)
    private File convertToTempFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("adjunto-", file.getOriginalFilename());
            file.transferTo(tempFile);
            return tempFile;
        }
        return null;
    }

    // metodo encargado de enviar finalmente el mail
    public void sendEmail(String to, String subject, String htmlContent, MultipartFile file) throws Exception {
        File tempFile=null;
        try {
            tempFile= convertToTempFile(file);
            // esto obtiene el servicio de google y manda el correo
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            MimeMessage email = createEmail(to, "nmeacuerdo1@gmail.com", subject, htmlContent, tempFile);// este mail hardcodeado dsps lo tenemos que cambiar al mail asociado al que inició sesion
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(bytes);

            Message message = new Message();
            message.setRaw(encodedEmail);
            service.users().messages().send("me", message).execute();
            String mensajePlano = Jsoup.parse(htmlContent).text();// elimina index html para tener el mensaje claro de lo que se mandó
            if (!subject.equalsIgnoreCase("Verificación de cuenta")) {// si el tema del mail no es la verificacion de cuenta, guarda un log en la bdd
                SentEmail log = new SentEmail(to, "nmeacuerdo1@gmail.com", subject, mensajePlano, LocalDateTime.now());
                sentEmailRepository.save(log);
            }
        } finally{
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            }
        }

    }
