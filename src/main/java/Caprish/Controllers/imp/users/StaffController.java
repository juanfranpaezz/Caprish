package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.users.StaffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('SUPERVISOR')")
@RequestMapping("/employee")
@Validated
public class StaffController extends UserGenericController<Staff, StaffRepository, StaffService> {

    public StaffController(StaffService service) {
        super(service);
    }

        @PreAuthorize("hasRole('SUPERVISOR')")
        @PostMapping("/create")
            @Override
            public ResponseEntity<String> createObject(@Valid @RequestBody Staff entity) {
                return create(entity);
            }

            @PreAuthorize("hasRole('BOSS')")
            @DeleteMapping("/delete/{id}")
            @Override
            public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
                return delete(id);
            }

            @PreAuthorize("hasRole('SUPERVISOR')")
            @PutMapping("/updateRoleId/{id}/{roleId}")
                     public ResponseEntity<String> updateRoleId(@PathVariable @Positive Long id, @PathVariable Long roleId) {
                         return update(id, "role_id", roleId);
                     }

            @PreAuthorize("hasRole('BOSS')")         
            @PutMapping("/updateBusinessId/{id}/{businessId}")
                     public ResponseEntity<String> updateBusinessId(@PathVariable @Positive Long id, @PathVariable Long businessId) {
                         return update(id, "business_id", businessId);
                     }

            @PreAuthorize("hasRole('BOSS')")
            @PutMapping("/updateIdWorkRole/{id}/")
                     public ResponseEntity<String> updateIdWorkRole(@PathVariable @Positive Long id, @PathVariable Long idWorkRole) {
                         return update(id, "id_work_role", idWorkRole);
                     }

            @PreAuthorize("hasRole('SUPERVISOR')")
            @GetMapping("/{id}")
            @Override
            public ResponseEntity<Staff> findObjectById(@Positive @PathVariable Long id) {
                return findById(id);
            }

            @PreAuthorize("hasRole('SUPERVISOR')")
            @GetMapping("/all")
            @Override
            public List<Staff> findAllObjects() {
                return findAll();
            }

}
