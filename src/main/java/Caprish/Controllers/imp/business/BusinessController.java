package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.CustomBadRequestException;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/business")
@Validated
@Tag(name = "Negocios")
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    @Autowired
    private StaffService staffService;
    @Autowired
    CredentialService credentialService;

    public BusinessController(BusinessService service) {
        super(service);
    }

    @Operation(summary = "Crear una empresa", description = "Crea una empresa con sus respectivos datos.")
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
        staffService.save(new Staff(credentialService.findByUsername(userDetails.getUsername()).get(), saved));
        return ResponseEntity.ok("Guardado con ID: " + saved.getId());
    }


    @Operation(summary = "Buscar negocio por nombre", description = "Obtiene un negocio usando su nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Negocio encontrado"),
            @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Business> findObjectByName(@PathVariable String name) {
        if (name.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Business business = service.findByBusinessName(name);
        if (business == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        business.setChats(null);
        business.setStaff(null);
        return ResponseEntity.ok(business);
    }

    @Operation(summary = "Ver mi empresa", description = "Muestra la empresa vinculada al jefe")
    @GetMapping("/view-my")
    public ResponseEntity<?> findObjectByBoss(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Business> a = service.findById(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        if(a.isPresent()) {
            return ResponseEntity.badRequest().body("No existe una empresa vinculada a vos ");
        }
        return ResponseEntity.ok(a.get());
    }

    @Operation(summary = "Actualizar nombre del negocio")
    @PutMapping("/updateBusinessName")
    public ResponseEntity<String> updateBusinessName(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        String businessName = payload.get("businessName");
        if (businessName == null || businessName.isBlank()) {
            return ResponseEntity.badRequest().body("El campo 'businessName' es requerido y no puede ser vacío");
        }
        if (payload.size() != 1) {
            return ResponseEntity.badRequest().body("Sólo se permite el campo 'businessName'");
        }
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "businessName", businessName);
    }

    @Operation(summary = "Actualizar descripción del negocio")
    @PutMapping("/updateDescription")
    public ResponseEntity<String> updateDescription(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        String description = payload.get("description");
        if (description == null || description.isBlank()) {
            return ResponseEntity.badRequest().body("El campo 'description' es requerido y no puede ser vacío");
        }
        if (payload.size() != 1) {
            return ResponseEntity.badRequest().body("Sólo se permite el campo 'description'");
        }
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "description", description);
    }

    @Operation(summary = "Actualizar eslogan del negocio")
    @PutMapping("/updateSlogan")
    public ResponseEntity<String> updateSlogan(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        String slogan = payload.get("slogan");
        if (slogan == null || slogan.isBlank()) {
            return ResponseEntity.badRequest().body("El campo 'slogan' es requerido y no puede ser vacío");
        }
        if (payload.size() != 1) {
            return ResponseEntity.badRequest().body("Sólo se permite el campo 'slogan'");
        }
        Long bizId = service.resolveBusinessId(userDetails);
        return update(bizId, "slogan", slogan);
    }


    @Operation(summary = "Actualizar valor del impuesto del negocio")
    @PutMapping("/updateTax")
    public ResponseEntity<String> updateTax(
            @RequestBody Map<String, Object> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (!payload.containsKey("tax")) {
            return ResponseEntity.badRequest().body("El campo 'tax' es requerido");
        }
        if (payload.size() != 1) {
            return ResponseEntity.badRequest().body("Sólo se permite el campo 'tax'");
        }
        Object taxObj = payload.get("tax");
        Long tax;
        try {
            tax = Long.parseLong(taxObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El campo 'tax' debe ser un número válido");
        }
        Long bizId = service.resolveBusinessId(userDetails);
        if(service.existsByTax(tax)) return ResponseEntity.badRequest().body("Ya existe una empresa con ese tax");
        return update(bizId, "tax", tax);
    }

    @Operation(summary = "Eliminar un negocio", description = "Bloquea un negocio a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Negocio eliminado correctamente")
    @DeleteMapping("/delete-my-business")
    public ResponseEntity<String> deleteMyBusiness(@AuthenticationPrincipal UserDetails userDetails) {
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        if (credentialId == null) {
            throw new CustomBadRequestException("No se encontró el usuario en las credenciales.");
        }
        Long businessId = staffService.getBusinessIdByCredentialId(credentialId);
        if (businessId == null) {
            throw new CustomBadRequestException("El ID de negocio es inválido.");
        }
        try {
            update(businessId, "active", false);
            credentialService.blockStaff(businessId);
            return ResponseEntity.ok("Estado actualizado correctamente.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

