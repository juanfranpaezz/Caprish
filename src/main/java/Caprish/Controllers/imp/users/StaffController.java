package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@Validated
public class StaffController extends MyObjectGenericController<Staff, StaffRepository, StaffService> {
    @Autowired
    CredentialService credentialService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ClientService clientService;


    public StaffController(StaffService service) {
        super(service);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStaff(@Valid @RequestBody Staff entity, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (entity == null ||
                    !userDetails.getAuthorities().toString().equals("ROLE_EMPLOYEE") ||
                    !userDetails.getAuthorities().toString().equals("ROLE_SUPERVISOR") ||
                    !userDetails.getAuthorities().toString().equals("ROLE_BOSS")
                ) return ResponseEntity.badRequest().build();
            entity.setId(credentialService.getIdByUsername(userDetails.getUsername()));
            return ResponseEntity.ok(String.valueOf(service.save(entity)));
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @PutMapping("/promote/{username}")
    public ResponseEntity<String> updateWorkRole(@PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (username == null) return ResponseEntity.badRequest().build();
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().build();
        return update(staffId, "role", "ROLE_SUPERVISOR");
    }

    @GetMapping("/{username}")
    public ResponseEntity<Staff> findObjectById(@Positive @PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        Long clientId = clientService.getIdByCredentialId(credentialService.getIdByUsername(username));
        if (!cartService.existsByBusinessIdAndClientId(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), clientId)) return ResponseEntity.badRequest().build();
        return findById(clientId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Staff>> findAllObjects(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.findAllByBusinessId(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        return findAll();
    }

}
