package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import Caprish.Service.others.GoogleGeocodingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/business")
@Validated
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    @Autowired
    private StaffService staffService;
    @Autowired
    CredentialService credentialService;
    @Autowired
    CredentialRepository credentialRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    GoogleGeocodingService geocodingService;

    public BusinessController(BusinessService service) {
        super(service);
    }


    @PostMapping("/create")
    public ResponseEntity<String> createBusiness(
            @RequestBody @Valid Business entity,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Optional<Staff> staffOpt = staffService.findByCredentialId(credentialId);
        if (staffOpt.isPresent() && staffOpt.get().getBusiness() != null) {
            return ResponseEntity
                    .badRequest()
                    .body("Solo puedes crear una empresa si aún no tienes una asociada.");
        }
        entity.setChats(null);
        entity.setStaff(null);
        entity.setProducts(null);
        try {
            service.addresValidation(entity.getAddress().getFullAddress());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Error al validar la dirección: " + e.getMessage());
        }
        if (entity.getBusinessName() == null || service.existsByBusinessName(entity.getBusinessName())) {
            return ResponseEntity
                    .badRequest()
                    .body("Ya existe una empresa con ese nombre, o el nombre es inválido.");
        }
        Business saved = service.save(entity);
        return ResponseEntity.ok("Guardado con ID: " + saved.getId());
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteObject(@AuthenticationPrincipal UserDetails userDetails) {
        //updatear campo activa o inactiva,
        return delete(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
    }


    @GetMapping("/{name}")
    public ResponseEntity<Business> findObjectByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByBusinessName(name));
    }

    @GetMapping("/view-my")
    public ResponseEntity<Business> findObjectByBoss(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Business> a = service.findById(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        return ResponseEntity.ok(a.get());
    }


    @PutMapping("/updateBusinessName/{id}/{name}")
    public ResponseEntity<String> updateBusinessName(@PathVariable @Positive Long id,
                                                     @PathVariable String name) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Credential> credentialOpt = credentialRepository.findByUsername(username);
        if (credentialOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales no encontradas.");
        }
        Optional<Staff> staff = staffRepository.findById(credentialOpt.get().getId());
        if (staff.isEmpty() || staff.get().getBusiness() == null || !staff.get().getBusiness().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para este negocio.");
        }
        return update(id, "business_name", name); // corregido el nombre del campo
    }

    @PutMapping("/updateDescription/{id}/{description}")
    public ResponseEntity<String> updateDescription(@PathVariable @Positive Long id,
                                                    @PathVariable String description) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Credential> credentialOpt = credentialRepository.findByUsername(username);
        if (credentialOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales no encontradas.");
        }
        Optional<Staff> staff = staffRepository.findById(credentialOpt.get().getId());
        if (staff.isEmpty() || staff.get().getBusiness() == null || !staff.get().getBusiness().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para este negocio.");
        }
        return update(id, "description", description);
    }

    @PutMapping("/updateSlogan/{id}/{slogan}")
    public ResponseEntity<String> updateSlogan(@PathVariable @Positive Long id,
                                               @PathVariable String slogan) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Credential> credentialOpt = credentialRepository.findByUsername(username);
        if (credentialOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales no encontradas.");
        }
        Optional<Staff> staff = staffRepository.findById(credentialOpt.get().getId());
        if (staff.isEmpty() || staff.get().getBusiness() == null || !staff.get().getBusiness().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para este negocio.");
        }
        return update(id, "slogan", slogan);
    }

    @PutMapping("/updateTax/{id}/{tax}")
    public ResponseEntity<String> updateTax(@PathVariable @Positive Long id,
                                            @PathVariable int tax) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Credential> credentialOpt = credentialRepository.findByUsername(username);
        if (credentialOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales no encontradas.");
        }
        Optional<Staff> staff = staffRepository.findById(credentialOpt.get().getId());
        if (staff.isEmpty() || staff.get().getBusiness() == null || !staff.get().getBusiness().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para este negocio.");
        }
        return update(id, "tax", tax);
    }


}

