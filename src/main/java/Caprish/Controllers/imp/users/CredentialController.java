package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Service.imp.users.CredentialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credential")
@Validated
public class CredentialController extends MyObjectGenericController<Credential, CredentialRepository, CredentialService> {

    public CredentialController(CredentialService service) {
        super(service);
    }


    @PutMapping("/updateFirstName/{id}/{firstName}")
    public ResponseEntity<String> updateFirstName(@PathVariable @Positive Long id,
                                                  @PathVariable String firstName) {
        return update(id, "first_name", firstName);
    }

    @PutMapping("/updateLastName/{id}/{lastName}")
    public ResponseEntity<String> updateLastName(@PathVariable @Positive Long id,
                                                 @PathVariable String lastName) {
        return update(id, "last_name", lastName);
    }

    @PutMapping("/updateEmail/{id}/{email}")
    public ResponseEntity<String> updateEmail(@PathVariable @Positive Long id,
                                              @PathVariable String email) {
        return update(id, "email", email);
    }

    @PutMapping("/updatePasswordHash/{id}/{passwordHash}")
    public ResponseEntity<String> updatePasswordHash(@PathVariable @Positive Long id,
                                                     @PathVariable String passwordHash) {
        return update(id, "password_hash", passwordHash);
    }

    @PutMapping("/updateRoleId/{id}/{roleId}")
    public ResponseEntity<String> updateRoleId(@PathVariable @Positive Long id, @PathVariable @Positive Long roleId) {
        return update(id, "role_id", roleId);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Credential entity) {
        return create(entity);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Credential> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @GetMapping("/all")
    @Override
    public List<Credential> findAllObjects() {
        return findAll();
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }


}
