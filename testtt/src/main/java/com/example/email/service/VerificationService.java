package com.example.email.service;
import com.example.email.model.EmailToken;
import com.example.email.repository.EmailTokenRepository;
import com.example.email.util.ThymeleafTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

@Service
public class VerificationService {

    private final EmailTokenRepository repo;
    private final GmailService gmailService;

    public VerificationService(EmailTokenRepository repo, GmailService gmailService) {
        this.repo = repo;
        this.gmailService = gmailService;
    }
    //generador de token
    public void sendVerificationCode(String email) throws Exception {
        String token = String.format("%06d", new Random().nextInt(900_000) + 100_000);
        String nombre = email.contains("@")
                ? email.substring(0, email.indexOf('@'))
                : email;
        EmailToken et = repo.findByEmail(email).orElse(new EmailToken());
        et.setEmail(email);
        et.setToken(token);
        et.setExpiration(LocalDateTime.now().plusMinutes(5));
        et.setVerified(false);
        repo.save(et);
        Map<String,Object> vars = Map.of("token", token,"username", nombre);
        String html = ThymeleafTemplate.processTemplate("verification", vars);
        gmailService.sendEmail(email,
                "Verificación de cuenta",
                html,
                null );
    }
    //validador de token
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
}
