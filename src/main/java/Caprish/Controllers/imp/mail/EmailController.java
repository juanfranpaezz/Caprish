package Caprish.Controllers.imp.mail;

import Caprish.Model.imp.mail.SentEmail;
import Caprish.Service.imp.mail.GmailService;
import Caprish.Service.imp.mail.SentEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmailController {
    @Autowired
    GmailService gmailService;
    @Autowired
    SentEmailService sentEmailService;
    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestParam String to,
                                            @RequestParam String subject,
                                            @RequestParam String name,
                                            @RequestPart(required = false) MultipartFile file) {
        try {
            gmailService.sendEmailWithName(to, subject, name, file);
            return ResponseEntity.ok("Correo enviado con exito");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    //listado de mails mandados
    @GetMapping(path = "/view-mails-sended", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SentEmail>> viewMailsSended(@RequestParam String email) {
        email = email.trim().toLowerCase(); //NO TOCAR
        List<SentEmail> mails = sentEmailService.findAllBySender(email);
        if (mails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mails);
    }
    //listado de mails recibidos
    @GetMapping(path = "/view-mails-received", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SentEmail>> viewMailsReceived(@RequestParam String email) {
        email = email.trim().toLowerCase(); // normaliza el string
        List<SentEmail> mails = sentEmailService.findAllByReceiver(email);
        if (mails.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mails);
    }
}
