package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.mail.EmailToken;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.LoginRequest;
import Caprish.Model.imp.users.LoginResponse;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.mail.VerificationService;
import Caprish.Service.imp.users.CredentialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/credential")
@Validated
public class CredentialController extends MyObjectGenericController<Credential, CredentialRepository, CredentialService> {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private VerificationService verificationService;

    public CredentialController(CredentialService service) {
        super(service);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );
            return ResponseEntity.ok(service.doLogin(request.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/updateFirstName")
    public ResponseEntity<String> updateFirstName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUsername(userDetails.getUsername()), "first_name", payload.get("firstName"));
    }

    @PutMapping("/updateLastName")
    public ResponseEntity<String> updateLastName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUsername(userDetails.getUsername()), "last_name", payload.get("lastName"));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePasswordHash(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody Map<String,String> payload) {
        return update(service.getIdByUsername(userDetails.getUsername()), "password", passwordEncoder.encode(payload.get("password")));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<String,String> code) {
        String email = userDetails.getUsername();
        boolean ok = verificationService.verifyCode(email, code.get("code"));
        if (ok){
            return ResponseEntity.ok("¡Verificado correctamente! Por favor ingrese para completar sus datos");
        } else {
            return ResponseEntity.badRequest().body("Código inválido o expirado.");
        }
    }


    @PutMapping("/complete-data")
    public ResponseEntity<LoginResponse> completeData(@AuthenticationPrincipal UserDetails userDetails,
                                            @RequestBody Map<String, String> payload) {
        try {
            EmailToken token = verificationService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Token no encontrado"));

            if (!token.isVerified()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .build();
            }

            Credential newUser = new Credential();
            newUser.setUsername(token.getEmail());
            newUser.setPassword(token.getPassword());
            newUser.setFirst_name(payload.get("firstName"));
            newUser.setLast_name(payload.get("lastName"));
            service.save(newUser);
            verificationService.deleteById(token.getId());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            newUser.getUsername(), newUser.getPassword()
                    )
            );
            return ResponseEntity.ok(service.doLogin(newUser.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> createObject(@Valid @RequestBody Credential entity){
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        try{
                return ResponseEntity.ok(verificationService.sendVerificationCode(entity.getUsername(), entity.getPassword()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
