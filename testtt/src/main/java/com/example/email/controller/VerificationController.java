
package com.example.email.controller;

import com.example.email.service.GmailService;
import com.example.email.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")// cambiar por caprish dsps, yo lo tengo asi pq ya guarde las requests del postman con esta direccion y me da paja cambiarlo jijox
public class VerificationController {
    //inyeccion del servicio
     @Autowired
     private GmailService gmailService;
    private final VerificationService verificationService;

    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
    //envia un token
    @PostMapping("/send-token")
    public ResponseEntity<String> sendEmailWithToken(@RequestParam String to,
                                                     @RequestPart(required = false) MultipartFile file) throws Exception {
        verificationService.sendVerificationCode(to);
        return ResponseEntity.ok("Correo con token enviado correctamente");
    }
    //verifica el token
    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestParam String email,
                                              @RequestParam String code) {
        boolean ok = verificationService.verifyCode(email, code);
        if (ok) {
            return ResponseEntity.ok("¡Verificado correctamente!");
        } else {
            return ResponseEntity.badRequest()
                    .body("Código inválido o expirado.");
        }
    }
}
