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
    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // tokens/{userEmail} folder for each user
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    private final SentEmailRepository sentEmailRepository;

    public GmailService(SentEmailRepository sentEmailRepository) {
        this.sentEmailRepository = sentEmailRepository;
    }

    // Enviar email con nombre, necesita el email del usuario que envía (userEmail)
    public void sendEmailWithName(String userEmail, String to, String subject, String name, MultipartFile file) throws Exception {
        String htmlBody = generateHtmlContent(name);
        sendEmail(userEmail, to, subject, htmlBody, file);
    }

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

    public void sendEmail(String userEmail, String to, String subject, String htmlContent, MultipartFile file) throws Exception {
        File tempFile = null;
        try {
            tempFile = convertToTempFile(file);

            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            // Obtener credenciales para el usuario actual
            Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userEmail))
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            MimeMessage email = createEmail(to, userEmail, subject, htmlContent, tempFile);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail = Base64.encodeBase64URLSafeString(bytes);

            Message message = new Message();
            message.setRaw(encodedEmail);
            service.users().messages().send("me", message).execute();

            String mensajePlano = Jsoup.parse(htmlContent).text();
            if (!subject.equalsIgnoreCase("Verificación de cuenta")) {
                SentEmail log = new SentEmail(to, userEmail, subject, mensajePlano, LocalDateTime.now());
                sentEmailRepository.save(log);
            }
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

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

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId) throws IOException {
        InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Archivo de credenciales no encontrado: " + CREDENTIALS_FILE_PATH);
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                // Crea una carpeta tokens/{userId} para cada usuario
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH + "/" + userId)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
    }

    private File convertToTempFile(MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("adjunto-", file.getOriginalFilename());
            file.transferTo(tempFile);
            return tempFile;
        }
        return null;
    }
}

