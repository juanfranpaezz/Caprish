package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.mail.EmailToken;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.LoginRequest;
import Caprish.Model.imp.users.LoginResponse;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.mail.VerificationService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.others.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Autowired private JwtService jwtService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private VerificationService verificationService;


    public CredentialController(CredentialService service) {
        super(service);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }


    @PutMapping("/updateFirstName")
    public ResponseEntity<String> updateFirstName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUserDetails(userDetails), "first_name", payload.get("firstName"));
    }

    @PutMapping("/updateLastName")
    public ResponseEntity<String> updateLastName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUserDetails(userDetails), "last_name", payload.get("lastName"));
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePasswordHash(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUserDetails(userDetails), "password", payload.get("password"));
    }

    @PutMapping("/updateRole")
    public ResponseEntity<String> updateRoleId(@AuthenticationPrincipal UserDetails userDetails,
                                                     @RequestBody Map<String,String> payload) {
        return update(service.getIdByUserDetails(userDetails), "role_id", payload.get("roleId"));
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
    public ResponseEntity<String> completeData(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody Map<String, String> payload) {
        try {
            EmailToken token = verificationService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Token no encontrado"));

            if (!token.isVerified()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("El correo no ha sido verificado.");
            }
            Credential newUser = new Credential();
            newUser.setUsername(token.getEmail());
            newUser.setPassword(token.getPassword());
            newUser.setFirst_name(payload.get("firstName"));
            newUser.setLast_name(payload.get("lastName"));
            service.save(newUser);
            verificationService.deleteById(token.getId());
            return ResponseEntity.ok("Usuario creado correctamente con los datos completos.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al completar los datos: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Credential entity){
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        try{
                return ResponseEntity.ok(verificationService.sendVerificationCode(entity.getUsername(), entity.getPassword()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credential> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Credential>> findAllObjects() {
        return findAll();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }


}
