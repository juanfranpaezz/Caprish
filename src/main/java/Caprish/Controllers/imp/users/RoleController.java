package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Role;
import Caprish.Repository.interfaces.users.RoleRepository;
import Caprish.Service.imp.users.RoleService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('EMPLOYEE')")
@RequestMapping("/role")
public class RoleController extends MyObjectGenericController<Role, RoleRepository, RoleService> {

    public RoleController(RoleService service) {
        super(service);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody Role entity) {
        return create(entity);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
        return delete(id);
    }

   /* @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
        return update(id);
    }*/

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Role> findObjectById(@Valid @PathVariable Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @Override
    public List<Role> findAllObjects() {
        return List.of();
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PermitAll
    @GetMapping("/all")
    public List<Role> findAllObjectss() {
        return findAll();
    }
}
