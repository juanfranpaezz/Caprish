package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.LoginRequest;
import Caprish.Model.imp.users.LoginResponse;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.others.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    @Autowired private UserDetailsService userDetailsService;


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

    @PutMapping("/updateFirstName/{id}/{firstName}")
    public ResponseEntity<String> updateFirstName(@PathVariable @Positive Long id,
                                                  @PathVariable String firstName) {
        return update(id, "first_name", firstName);
    }

    @PutMapping("/updateLastName")
    public ResponseEntity<String> updateLastName(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String,String> payload) {
        return update(service.getIdByUserDetails(userDetails), "last_name", payload.get("lastName"));
    }

    @PutMapping("/updatePasswordHash/{id}/{password}")
    public ResponseEntity<String> updatePasswordHash(@PathVariable @Positive Long id,
                                                     @PathVariable String password) {
        return update(id, "password", password);
    }

    @PutMapping("/updateRoleId/{id}/{roleId}")
    public ResponseEntity<String> updateRoleId(@PathVariable @Positive Long id, @PathVariable @Positive Long roleId) {
        return update(id, "role_id", roleId);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Credential entity) {
        return create(entity);
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
