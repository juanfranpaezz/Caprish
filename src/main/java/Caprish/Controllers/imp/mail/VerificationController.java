
package Caprish.Controllers.imp.mail;

import Caprish.Service.imp.mail.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
public class VerificationController {
    final
    VerificationService verificationService;
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
    @PostMapping("/send-token")
    public ResponseEntity<String> sendEmailWithToken(@RequestPart(required = false) MultipartFile file) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();  // El email del usuario autenticado
        verificationService.sendVerificationCode(userEmail);
        return ResponseEntity.ok("Correo con token enviado correctamente");
    }
    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestParam String code) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();  // El email del usuario autenticado
        boolean ok = verificationService.verifyCode(userEmail, code);
        if (ok) {
            return ResponseEntity.ok("¡Verificado correctamente!");
        } else {
            return ResponseEntity.badRequest()
                    .body("Código inválido o expirado.");
        }
    }
}
