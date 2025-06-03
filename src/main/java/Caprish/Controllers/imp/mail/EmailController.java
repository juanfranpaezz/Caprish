//package Caprish.Controllers.imp.mail;
//
//import Caprish.Model.imp.mail.SentEmail;
//import Caprish.Service.imp.mail.GmailService;
//import Caprish.Service.imp.mail.VerificationService;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import Caprish.Service.imp.mail.SentEmailService;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class EmailController {
//    final
//    GmailService gmailService;
//    final
//    SentEmailService sentEmailService;
//
//    public EmailController(GmailService gmailService, SentEmailService sentEmailService, VerificationService verificationService) {
//        this.gmailService = gmailService;
//        this.sentEmailService = sentEmailService;
//    }
//esto en vacaciones de invierno vaaaaaaaaaaa
//    @PostMapping("/send-email")
//    public ResponseEntity<String> sendEmail(@RequestParam String to,
//                                            @RequestParam String subject,
//                                            @RequestParam String name,
//                                            @RequestPart(required = false) MultipartFile file) {
//        try {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            String userEmail = auth.getName(); // obtener email del usuario logueado
//
//            gmailService.sendEmailWithName(userEmail, to, subject, name, file);
//            return ResponseEntity.ok("Correo enviado con exito");
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
//        }
//    }
//
//    //listado de mails mandados
//    @GetMapping(path = "/view-mails-sended", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<SentEmail>> viewMailsSended() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = auth.getName();// obtener email del usuario logueado
//        List<SentEmail> mails = sentEmailService.findAllBySender(userEmail.trim().toLowerCase());
//        if (mails.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(mails);
//    }
//    //listado de mails recibidos
//    @GetMapping(path = "/view-mails-received", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<SentEmail>> viewMailsReceived() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getName();// obtener email del usuario logueado
//        List<SentEmail> mails = sentEmailService.findAllByReceiver(email);
//        if (mails.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(mails);
//    }
//}
