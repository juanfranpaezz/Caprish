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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.util.Optional;

@RestController
@RequestMapping("/business")
@Validated
@Tag(name = "Negocios", description = "Operaciones relacionadas con entidades de tipo Business")
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    @Autowired
    private StaffService staffService;
    @Autowired
    CredentialService credentialService;

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
        entity.setChats(null);
        entity.setStaff(null);
        entity.setProducts(null);
        entity.setActive(true);
        Business saved = service.save(entity);
        return ResponseEntity.ok("Guardado con ID: " + saved.getId());
    }

    @Operation(summary = "Eliminar un negocio", description = "Elimina un negocio a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Negocio eliminado correctamente")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteObject(@AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        update(businessId, "enabled", false);
        return delete(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
    }

    @Operation(summary = "Buscar negocio por nombre", description = "Obtiene un negocio usando su nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Negocio encontrado"),
            @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Business> findObjectByName(@PathVariable String name) {
        Business business = service.findByBusinessName(name);
        business.setChats(null);
        business.setStaff(null);
        return ResponseEntity.ok(business);
    }

    @GetMapping("/view-my")
    public ResponseEntity<Business> findObjectByBoss(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Business> a = service.findById(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        return ResponseEntity.ok(a.get());
    }

    @Operation(summary = "Actualizar nombre del negocio")
    @PutMapping("/updateBusinessName")
    public ResponseEntity<String> updateBusinessName(
            @RequestParam String name,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "business_name", name);
    }

    @Operation(summary = "Actualizar descripción del negocio")
    @PutMapping("/updateDescription")
    public ResponseEntity<String> updateDescription(
            @RequestParam String description,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "description", description);
    }

    @Operation(summary = "Actualizar eslogan del negocio")
    @PutMapping("/updateSlogan")
    public ResponseEntity<String> updateSlogan(
            @RequestParam String slogan,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "slogan", slogan);
    }

    @Operation(summary = "Actualizar valor del impuesto del negocio")
    @PutMapping("/updateTax")
    public ResponseEntity<String> updateTax(
            @RequestParam @Positive long tax,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "tax", tax);
    }

    @PutMapping("/updateActive")
    public ResponseEntity<String> updateActive(
            @RequestParam boolean active,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long bizId = service.resolveBusinessId(userDetails);
        try {
            service.changeActiveStatus(bizId, active);
            return ResponseEntity.ok("Estado actualizado correctamente.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

