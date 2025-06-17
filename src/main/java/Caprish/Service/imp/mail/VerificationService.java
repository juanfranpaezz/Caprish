package Caprish.Service.imp.mail;
import Caprish.Model.imp.mail.EmailToken;
import Caprish.Model.imp.mail.ThymeleafTemplate;
import Caprish.Repository.interfaces.mail.EmailTokenRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class VerificationService extends MyObjectGenericService<EmailToken,EmailTokenRepository,VerificationService> {

    @Autowired
    private EmailTokenRepository repo;
    @Autowired
    private GmailService gmailService;

    protected VerificationService(EmailTokenRepository childRepository) {
        super(childRepository);
    }

    public String sendVerificationCode(String userEmail,String password) throws Exception {
        String token = String.format("%06d", new Random().nextInt(900_000) + 100_000);
        String nombre = userEmail.contains("@")
                ? userEmail.substring(0, userEmail.indexOf('@'))
                : userEmail;
        EmailToken et = repo.findByEmail(userEmail).orElse(new EmailToken());
        et.setEmail(userEmail);
        et.setToken(token);
        et.setPassword(password);
        et.setExpiration(LocalDateTime.now().plusMinutes(5));
        et.setVerified(false);
        save(et);
        Map<String, Object> vars = Map.of("token", token, "username", nombre);
        String html = ThymeleafTemplate.processTemplate("verification", vars);
        gmailService.sendEmail(userEmail,
                "Verificación de cuenta",
                html,
                null);
        return "Correo con token enviado correctamente, por favor verifique su corre, tiene 5 minutos";
    }

    public String getUsernameByUserDetails(UserDetails userDetails) {
        return userDetails.getUsername();
    }

    public Optional<EmailToken> findByEmail(String userDetails) {
        return repo.findByEmail(userDetails);
    }

    public boolean verifyCode(String email, String code) {
        return repo.findByEmailAndToken(email, code)
                .filter(e -> e.getExpiration().isAfter(LocalDateTime.now()))
                .map(e -> {
                    e.setVerified(true);
                    repo.save(e);
                    return true;
                })
                .orElse(false);
    }

    @Override
    protected void verifySpecificAttributes(EmailToken entity) {

    }
}
