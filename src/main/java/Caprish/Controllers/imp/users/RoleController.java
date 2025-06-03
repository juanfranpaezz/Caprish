package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Role;
import Caprish.Repository.interfaces.users.RoleRepository;
import Caprish.Service.imp.users.RoleService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/role")
public class RoleController extends MyObjectGenericController<Role, RoleRepository, RoleService> {

    public RoleController(RoleService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody Role entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Role> findObjectById(Long id) {
        return findById(id);
    }

    @Override
    public List<Role> findAllObjects() {
        return List.of();
    }

    @PermitAll
    @GetMapping("/all")
    public List<Role> findAllObjectss() {
        return findAll();
    }
}
