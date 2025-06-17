package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import Caprish.Model.imp.users.dto.StaffViewDTO;

@RestController
@RequestMapping("/staff")
@Validated
@Tag(name = "Staff", description = "Operaciones relacionadas al personal de la empresa")
public class StaffController extends MyObjectGenericController<Staff, StaffRepository, StaffService> {
    @Autowired
    CredentialService credentialService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BusinessService businessService;
    @Autowired
    private StaffService staffService;


    public StaffController(StaffService service) {
        super(service);
    }


    @Operation(
            summary = "Crear un nuevo empleado",
            description = "Permite al usuario autenticado con rol válido (EMPLOYEE, SUPERVISOR o BOSS) crear su propia cuenta Staff"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Staff creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o rol no permitido")
    })
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

    @PostMapping("/create-boss")
    public ResponseEntity<String> createBoss(
            @Valid @RequestBody Staff entity,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (entity == null || entity.getCredential() == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios: credential o business.");
        }
        Optional<Business> optionalBusiness = Optional.ofNullable(businessService.findByBusinessName(entity.getBusiness().getBusinessName()));
        if (optionalBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body("La empresa no existe.");
        }
        entity.setBusiness(optionalBusiness.get());
        service.save(entity);
        return ResponseEntity.ok("Staff jefe creado con éxito.");
    }


    @Operation(
            summary = "Eliminar un empleado por ID",
            description = "Elimina un registro Staff usando su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteObject(@Valid @RequestBody String username , @AuthenticationPrincipal UserDetails userDetails ) {
        if (username == null) return ResponseEntity.badRequest().build();
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("La empresa no existe");
        return delete(staffId);
    }


    @GetMapping("/view-my-account")
    public ResponseEntity<Staff> viewMyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(staffService.findByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())).get());
    }

    @Operation(
            summary = "Promover a un empleado",
            description = "Asigna el rol de SUPERVISOR a un empleado dado su nombre de usuario, si pertenece a la misma empresa"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Usuario inválido o no pertenece a la empresa")
    })
    @PutMapping("/promote")
    public ResponseEntity<String> updateWorkRole(@PathVariable String username, @AuthenticationPrincipal UserDetails userDetails) {
        if (username == null) return ResponseEntity.badRequest().build();
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("El staff no existe");
        return update(staffId, "role", "ROLE_SUPERVISOR");
    }

    @GetMapping("/by-business/{businessId}")
    public ResponseEntity<List<StaffViewDTO>> getStaffByBusiness(@PathVariable Long businessId) {
        List<StaffViewDTO> staff = service.getStaffByBusiness(businessId);
        return ResponseEntity.ok(staff);
    }


}
