package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import Caprish.Service.imp.users.PlatformAdminService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/platform_admin")
public class PlatformAdminController extends UserGenericController<PlatformAdmin, PlatformAdminRepository, PlatformAdminService> {

    public PlatformAdminController(PlatformAdminService service) {
        super(service);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody PlatformAdmin entity) {
        return create(entity);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
        return delete(id);
    }

    /*@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
        return update(id);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PlatformAdmin> findObjectById(@Valid @PathVariable Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Override
    public List<PlatformAdmin> findAllObjects() {
        return findAll();
    }

}