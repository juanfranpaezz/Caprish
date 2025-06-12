package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Staff;
import Caprish.Model.imp.users.dto.StaffViewDTO;
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
@RequestMapping("/staff")
@Validated
public class StaffController extends MyObjectGenericController<Staff, StaffRepository, StaffService> {

    public StaffController(StaffService service) {
        super(service);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Staff entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }


    @PutMapping("/updateWorkRole/{id}/{workRole}")
    public ResponseEntity<String> updateWorkRole(@PathVariable @Positive Long id, @PathVariable String workRole) {
        return update(id, "work_role", workRole);
    }

    @PreAuthorize("hasRole('BOSS')")
    @GetMapping("/staff/by-business/{businessId}")
    public ResponseEntity<List<StaffViewDTO>> getStaffByBusiness(@PathVariable Long businessId) {
        List<StaffViewDTO> staff = service.getStaffByBusiness(businessId);
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> findAllObjects() {
        return findAll();
    }

}
