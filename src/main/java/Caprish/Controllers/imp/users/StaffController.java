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
@RequestMapping("/employee")
@Validated
public class StaffController extends UserGenericController<Staff, StaffRepository, StaffService> {

    public StaffController(StaffService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Staff entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @PutMapping("/updateBusinessId/{id}/{businessId}")
    public ResponseEntity<String> updateBusinessId(@PathVariable @Positive Long id, @PathVariable @Positive Long businessId) {
        return update(id, "business_id", businessId);
    }

    @PutMapping("/updateWorkRole/{id}/{workRole}")
    public ResponseEntity<String> updateWorkRole(@PathVariable @Positive Long id, @PathVariable String workRole) {
        return update(id, "work_role", workRole);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Staff> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @GetMapping("/all")
    @Override
    public List<Staff> findAllObjects() {
        return findAll();
    }

}
