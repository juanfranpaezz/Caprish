package com.example.email.controller;

import com.example.email.model.SentEmail;
import com.example.email.service.GmailService;
import com.example.email.service.MailService;
import com.example.email.service.SentEmailService;
import com.example.email.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")// cambiar por caprish dsps, yo lo tengo asi pq ya guarde las requests del postman con esta direccion y me da paja cambiarlo jijox
public class EmailController {
    // inyecciones
    @Autowired
    private GmailService gmailService;
    @Autowired
    private MailService mailService;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private SentEmailService sentEmailService;

    // envio de mail
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
    // registrar
    @PostMapping("sign-up")
    public ResponseEntity<String> checkEmail(@RequestParam String email, @RequestParam String password) throws Exception {
        if (mailService.existsByMail(email)) {
            return ResponseEntity.badRequest().body("Este correo ya está registrado.");
        }
        mailService.addMail(email, password);
        verificationService.sendVerificationCode(email);
        return ResponseEntity.ok("Email agregado correctamente. Por favor verifique su email con el token que le acabamos de enviar.");
    }
    // iniciar sesion
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        mailService.verifyLogin(email, password);
        return ResponseEntity.ok("Los datos ingresados son correctos , bienvenido!");
    }
}
