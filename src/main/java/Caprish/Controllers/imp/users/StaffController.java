package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Exception.UserException;
import Caprish.Model.enums.Role;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    private BusinessService businessService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private CredentialController credentialController;
    @Autowired private PasswordEncoder passwordEncoder;



    public StaffController(StaffService service) {
        super(service);
    }


    @Operation(summary = "Crear credencial y empleado (solo BOSS)",
            description = "Permite al usuario con rol BOSS crear un nuevo Credential y su Staff de tipo EMPLOYEE en su empresa desde un solo endpoint")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Empleado creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o no pertenece a la empresa"),
            @ApiResponse(responseCode = "403", description = "No estás autorizado para esta operación")
    })
    @PostMapping("/create-employee")
    public ResponseEntity<String> createEmployee(
            @Valid @RequestBody Map<String,String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long bossCredId = credentialService.getIdByUsername(userDetails.getUsername());
        Long bossBusinessId = service.getBusinessIdByCredentialId(bossCredId);
        String username = payload.get("username");
        if (bossBusinessId == null) {
            return ResponseEntity.badRequest().body("No se encontró la empresa para el jefe actual.");
        }
        if (credentialService.existsByUsername(username)) {
            return ResponseEntity.badRequest().body("El usuario ya existe.");
        }
        String firstName = payload.get("firstName");
        String lastName = payload.get("lastName");
        String password = payload.get("password");
        try {
            credentialService.verifyPassword(password);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios: 'username' y/o 'password'.");
        }

        Credential newCred = new Credential();
        newCred.setFirst_name(firstName);
        newCred.setLast_name(lastName);
        newCred.setUsername(username);
        newCred.setPassword(passwordEncoder.encode(password));
        newCred.setRole(new Role("ROLE_EMPLOYEE"));
        Credential savedCred = credentialService.save(newCred);
        Business bossBusiness = businessService.findById(bossBusinessId)
                .orElseThrow(() -> new IllegalStateException("Empresa inconsistente."));
        Staff newStaff = new Staff();
        newStaff.setCredential(savedCred);
        newStaff.setBusiness(bossBusiness);

        try {
            service.save(newStaff);
            return ResponseEntity.ok("Empleado y credencial creados con éxito.");
        } catch (InvalidEntityException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    @Operation(summary = "Crear un jefe", description = "Crea un jefe con sus respectivos atributos")
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
            summary = "Eliminar un empleado por nombre",
            description = "Elimina un registro Staff usando su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @PutMapping("/delete")
    public ResponseEntity<String> deleteObject(@RequestBody Map <String,String> payload, @AuthenticationPrincipal UserDetails userDetails ) {
        String username = payload.get("username");
        if (username == null) return ResponseEntity.badRequest().build();
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("La empresa no existe");
        return credentialController.update(credentialService.getIdByUsername(username),"enabled",false);
    }
    @Operation(summary = "Ver mi cuenta", description = "Muestra la cuenta del empleado, con todos sus datos")
    @GetMapping("/view-my-account")
    public ResponseEntity<Staff> viewMyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        Staff staff = staffService.findByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())).get();
        staff.getCredential().setPassword(null);
        return ResponseEntity.ok(staff);
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
    public ResponseEntity<?> updateWorkRole(@RequestBody Map <String,String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        String username = payload.get("username");
        if (username == null || credentialService.findByUsername(username).isEmpty()) return ResponseEntity.badRequest().body("Error, usuario nulo o no encontrado");
        if(!credentialService.findByUsername(username).get().getRole().getId().equals("ROLE_EMPLOYEE"))return ResponseEntity.badRequest().body("Solo se pueden ascender usuarios que sean de tipo EMPLOYEE");
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("El staff no existe");
        Long credentialId = credentialService.getIdByUsername(username);
        credentialService.promoteStaff(credentialId);
        return ResponseEntity.ok("Staff promovido correctamente");
    }
    @Operation(
            summary = "Ver empleados de una empresa ",
            description = "Devuelve los empleados de una empresa"
    )
    @GetMapping("/by-business")
    public ResponseEntity<List<StaffViewDTO>> getStaffByBusiness(@AuthenticationPrincipal UserDetails userDetails) {
        List<StaffViewDTO> staff = service.getStaffByBusinessId(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        return ResponseEntity.ok(staff);
    }
    @Operation(
            summary = "Despedir a un empleado",
            description = "Encuentra a un empleado y deja de estar habilitado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Staff despedido correctamente"),
            @ApiResponse(responseCode = "400",description = "Usuario invalido o no pertenece a la empresa")
    })
    @PutMapping("/fire-staff")
    public ResponseEntity<?>fireStaff(@RequestBody Map <String,String> payload, @AuthenticationPrincipal UserDetails userDetails) {
        String username = payload.get("username");
        if(username ==null || credentialService.findByUsername(username).isEmpty()) return ResponseEntity.badRequest().body("Error, usuario nulo o no encontrado");
        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));
        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().body("El staff no existe");
        credentialController.update(credentialService.getIdByUsername(username),"enabled",false);
        return ResponseEntity.ok("Staff despedido correctamente");
    }
}
