package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.users.StaffService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('SUPERVISOR')")
@RequestMapping("/employee")
public class StaffController extends UserGenericController<Staff, StaffRepository, StaffService> {

    public StaffController(StaffService service) {
        super(service);
    }

        @PreAuthorize("hasRole('SUPERVISOR')")
        @PostMapping("/create")
            @Override
            public ResponseEntity<String> createObject(@RequestBody Staff entity) {
                return create(entity);
            }

            @PreAuthorize("hasRole('BOSS')")
            @DeleteMapping("/delete/{id}")
            @Override
            public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
                return delete(id);
            }

            /*@PreAuthorize("hasRole('SUPERVISOR')")
            @PutMapping("/update/{id}")
            @Override
            public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
                return update(id);
            }*/

            @PreAuthorize("hasRole('SUPERVISOR')")
            @GetMapping("/{id}")
            @Override
            public ResponseEntity<Staff> findObjectById(@Valid @PathVariable Long id) {
                return findById(id);
            }

            @PreAuthorize("hasRole('SUPERVISOR')")
            @GetMapping("/all")
            @Override
            public List<Staff> findAllObjects() {
                return findAll();
            }

}
