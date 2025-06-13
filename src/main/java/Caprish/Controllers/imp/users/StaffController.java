package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.business.BusinessService;
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
import java.util.Optional;

@RestController
@RequestMapping("/staff")
@Validated
public class StaffController extends MyObjectGenericController<Staff, StaffRepository, StaffService> {
    @Autowired
    CredentialService credentialService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BusinessService businessService;


    public StaffController(StaffService service) {
        super(service);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStaff(@Valid @RequestBody Staff entity, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (entity == null ||
                    !userDetails.getAuthorities().toString().equals("ROLE_EMPLOYEE") ||
                    !userDetails.getAuthorities().toString().equals("ROLE_SUPERVISOR")
                ) return ResponseEntity.badRequest().build();
            entity.setId(credentialService.getIdByUsername(userDetails.getUsername()));
            return ResponseEntity.ok(String.valueOf(service.save(entity)));
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create-boss")
    public ResponseEntity<String> createBoss(
            @Valid @RequestBody Staff entity,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (entity == null || entity.getCredential() == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios: credential o business.");
        }
        Optional<Business> optionalBusiness = businessService.findByBusinessName(entity.getBusiness().getBusinessName());
        if (optionalBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body("La empresa no existe.");
        }
        entity.setBusiness(optionalBusiness.get());
        service.save(entity);
        return ResponseEntity.ok("Staff jefe creado con éxito.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteObject(@Valid @RequestBody String username , @AuthenticationPrincipal UserDetails userDetails ) {
        if (username == null) return ResponseEntity.badRequest().build();
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("La empresa no existe");
        return delete(staffId);
    }

    @PutMapping("/promote")
    public ResponseEntity<String> updateWorkRole(@PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (username == null) return ResponseEntity.badRequest().build();
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("El staff no existe");
        return update(staffId, "role", "ROLE_SUPERVISOR");
    }

}
