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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/credential")
@Validated
@Tag(name = "Credenciales", description = "Gestión de credenciales de usuario, autenticación y actualización de datos")
public class CredentialController extends MyObjectGenericController<Credential, CredentialRepository, CredentialService> {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private VerificationService verificationService;

    public CredentialController(CredentialService service) {
        super(service);
    }

    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica al usuario mediante su nombre de usuario y contraseña"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {
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

    @Operation(
            summary = "Actualizar nombre",
            description = "Actualiza el nombre (firstName) del usuario autenticado"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/updateFirstName")
    public ResponseEntity<String> updateFirstName(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String,String> payload) {

        return update(service.getIdByUsername(userDetails.getUsername()), "first_name", payload.get("firstName"));
    }

    @Operation(
            summary = "Actualizar apellido",
            description = "Actualiza el apellido (lastName) del usuario autenticado"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/updateLastName")
    public ResponseEntity<String> updateLastName(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String,String> payload) {

        return update(service.getIdByUsername(userDetails.getUsername()), "last_name", payload.get("lastName"));
    }

    @Operation(
            summary = "Actualizar contraseña",
            description = "Actualiza la contraseña del usuario autenticado"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePasswordHash(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String,String> payload) {

        return update(service.getIdByUsername(userDetails.getUsername()), "password", passwordEncoder.encode(payload.get("password")));
    }

    @Operation(
            summary = "Verificar token por correo",
            description = "Valida el código de verificación enviado por correo electrónico"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String,String> code) {

        String email = userDetails.getUsername();
        boolean ok = verificationService.verifyCode(email, code.get("code"));
        if (ok) {
            return ResponseEntity.ok("¡Verificado correctamente! Por favor ingrese para completar sus datos");
        } else {
            return ResponseEntity.badRequest().body("Código inválido o expirado.");
        }
    }

    @Operation(
            summary = "Completar datos de perfil",
            description = "Permite completar nombre y apellido luego de la verificación del correo electrónico"
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/complete-data")
    public ResponseEntity<LoginResponse> completeData(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload) {

        try {
            EmailToken token = verificationService.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Token no encontrado"));

            if (!token.isVerified()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

    @Operation(
            summary = "Registro de usuario",
            description = "Crea una nueva credencial codificando la contraseña y enviando un código de verificación por correo"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro exitoso, código enviado por correo"),
            @ApiResponse(responseCode = "400", description = "Error al registrar el usuario")
    })
    @PostMapping("/sign-up")
    public ResponseEntity<String> createObject(
            @RequestBody @Valid Credential entity){

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        try {
            return ResponseEntity.ok(verificationService.sendVerificationCode(entity.getUsername(), entity.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}