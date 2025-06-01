package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import Caprish.Service.imp.users.PlatformAdminService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/platform_admin")
@Validated
public class PlatformAdminController extends UserGenericController<PlatformAdmin, PlatformAdminRepository, PlatformAdminService> {

    public PlatformAdminController(PlatformAdminService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody PlatformAdmin entity) {
        return create(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateEmail/{id}/{email}")
    public ResponseEntity<String> updateEmail(@PathVariable @Positive Long id, @PathVariable String email) {
        return update(id, "email", email);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateFirstName/{id}/{firstName}")
    public ResponseEntity<String> updateFirstName(@PathVariable @Positive Long id, @PathVariable String firstName) {
        return update(id, "first_name", firstName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateLastName/{id}/{lastName}")
    public ResponseEntity<String> updateLastName(@PathVariable @Positive Long id, @PathVariable String lastName) {
        return update(id, "last_name", lastName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatePasswordHash/{id}/{passwordHash}")
    public ResponseEntity<String> updatePasswordHash(@PathVariable @Positive Long id, @PathVariable String passwordHash) {
        return update(id, "password_hash", passwordHash);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PlatformAdmin> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Override
    public List<PlatformAdmin> findAllObjects() {
        return findAll();
    }

}