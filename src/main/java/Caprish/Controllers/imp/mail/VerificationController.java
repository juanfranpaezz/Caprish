
package Caprish.Controllers.imp.mail;

import Caprish.Service.imp.mail.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
public class VerificationController {
    @Autowired
    VerificationService verificationService;
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
    @PostMapping("/send-token")
    public ResponseEntity<String> sendEmailWithToken(@RequestParam String to,
                                                     @RequestPart(required = false) MultipartFile file) throws Exception {
        verificationService.sendVerificationCode(to);
        return ResponseEntity.ok("Correo con token enviado correctamente");
    }
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
