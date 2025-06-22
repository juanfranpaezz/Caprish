package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.UserException;
import Caprish.Model.enums.Role;
import Caprish.Model.imp.mail.EmailToken;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.LoginRequest;
import Caprish.Model.imp.users.LoginResponse;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.mail.VerificationService;
import Caprish.Service.imp.users.CredentialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/credential")
@Validated
@Tag(name = "Credenciales", description = "Gestión de credenciales de usuario, autenticación y actualización de datos")
public class CredentialController extends MyObjectGenericController<Credential, CredentialRepository, CredentialService> {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private VerificationService verificationService;
    @Autowired private CredentialService credentialService;

    public CredentialController(CredentialService service) {
        super(service);
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
    public ResponseEntity<String> createObject(@Valid @RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");

        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Los campos 'username' y 'password' son requeridos");
        }
        if (!username.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("El campo 'username' debe ser un correo válido");
        }

        if (credentialService.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        try {
            credentialService.verifyPassword(password);
            passwordEncoder.encode(password);
            return ResponseEntity.ok(verificationService.sendVerificationCode(username, password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Verificar token por correo",
            description = "Valida el código de verificación enviado por correo electrónico"
    )
    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestBody Map<String,String> code) {
        String email = code.get("email");
        if (verificationService.verifyCode(email, code.get("code"))&& code.get("role").equals("BOSS")){
            Credential cred=new Credential(email, passwordEncoder.encode(code.get("password")),new Role("ROLE_BOSS"));
            service.save(cred);
            return ResponseEntity.ok("¡Verificado correctamente! Por favor ingrese para completar sus datos");
        } else  if(verificationService.verifyCode(email, code.get("code"))&& code.get("role").equals("CLIENT")){
            Credential cred=new Credential(email, passwordEncoder.encode(code.get("password")),new Role("ROLE_CLIENT"));
            service.save(cred);

            return ResponseEntity.ok("¡Verificado correctamente! Por favor ingrese para completar sus datos");
        }
        return ResponseEntity.badRequest().body("Codigo o mail incorrecto");
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
    public ResponseEntity<?> login(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestBody LoginRequest request) {
        if (userDetails != null) {
            return ResponseEntity.badRequest().body("Ya estás logueado");
        }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        try {
            return ResponseEntity.ok(service.doLogin(request.getUsername()));
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Operation(
            summary = "Completar datos de perfil",
            description = "Permite completar nombre y apellido luego de la verificación del correo electrónico"
    )
    @PutMapping("/complete-data")
    public ResponseEntity<String> completeData(
            @RequestBody Map<String, String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Credential> cred = credentialService.findByUsername(userDetails.getUsername());

        System.out.println(cred.get().getId());
        System.out.println(cred.get().getUsername());
        update(cred.get().getId(),"first_name",payload.get("firstName"));
        update(cred.get().getId(),"last_name",payload.get("lastName"));
        return ResponseEntity.ok().body("Campos actualizados exitosamente");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetails userDetails) {
        Credential cred = service.findByUsername(userDetails.getUsername()).get();
        cred.setTokenVersion(cred.getTokenVersion() + 1);
        service.save(cred);
        return ResponseEntity.ok("Logout exitoso");
    }

    @Operation(
            summary = "Actualizar nombre",
            description = "Actualiza el nombre (firstName) del usuario autenticado"
    )
    @PutMapping("/updateFirstName")
    public ResponseEntity<String> updateFirstName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUsername(userDetails.getUsername()), "first_name", payload.get("firstName"));
    }

    @Operation(
            summary = "Actualizar apellido",
            description = "Actualiza el apellido (lastName) del usuario autenticado"
    )
    @PutMapping("/updateLastName")
    public ResponseEntity<String> updateLastName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUsername(userDetails.getUsername()), "last_name", payload.get("lastName"));
    }

    @Operation(
            summary = "Actualizar contraseña",
            description = "Actualiza la contraseña del usuario autenticado"
    )
    @PutMapping("/updatePassword")
    public ResponseEntity<String> updatePasswordHash(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestBody Map<String,String> payload) throws UserException {
        credentialService.verifyPassword(payload.get("password"));
        return update(service.getIdByUsername(userDetails.getUsername()), "password", passwordEncoder.encode(payload.get("password")));
    }

}
