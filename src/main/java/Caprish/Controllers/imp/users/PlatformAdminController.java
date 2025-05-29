package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import Caprish.Service.imp.users.PlatformAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/platform_admin")
public class PlatformAdminController extends UserGenericController<PlatformAdmin, PlatformAdminRepository, PlatformAdminService> {

    public PlatformAdminController(PlatformAdminService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody PlatformAdmin entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(Long id) {
        return delete(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(Long id) {
        return update(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<PlatformAdmin> findObjectById(Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Override
    public List<PlatformAdmin> findAllObjects() {
        return findAll();
    }

}