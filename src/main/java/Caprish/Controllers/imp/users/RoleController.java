package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Role;
import Caprish.Repository.interfaces.users.RoleRepository;
import Caprish.Service.imp.users.RoleService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('EMPLOYEE')")
@RequestMapping("/role")
@Validated
public class RoleController extends MyObjectGenericController<Role, RoleRepository, RoleService> {

    public RoleController(RoleService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Role entity) {
        return create(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Role> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<Role> findAllObjects() {
        return List.of();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PermitAll
    @GetMapping("/all")
    public List<Role> findAllObjectss() {
        return findAll();
    }
}
