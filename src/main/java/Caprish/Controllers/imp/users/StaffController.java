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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/staff")
@Validated
@Tag(name = "Staff", description = "Operaciones relacionadas al personal de la empresa")
@SecurityRequirement(name = "bearerAuth")
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

    @Operation(
            summary = "Crear un nuevo empleado",
            description = "Permite al usuario autenticado con rol válido (EMPLOYEE, SUPERVISOR o BOSS) crear su propia cuenta Staff"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Staff creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o rol no permitido")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createStaff(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Entidad Staff con los datos a registrar", required = true)
            @Valid  Staff entity,

            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails) {

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

    @Operation(
            summary = "Eliminar un empleado por ID",
            description = "Elimina un registro Staff usando su identificador único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(
            @Parameter(description = "ID del Staff a eliminar", required = true)
            @Positive @PathVariable Long id) {
        return delete(id);
    }

    @Operation(
            summary = "Promover a un empleado",
            description = "Asigna el rol de SUPERVISOR a un empleado dado su nombre de usuario, si pertenece a la misma empresa"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Usuario inválido o no pertenece a la empresa")
    })
    @PutMapping("/promote/{username}")
    public ResponseEntity<String> updateWorkRole(
            @Parameter(description = "Username del empleado a promover", required = true)
            @PathVariable String username,

            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails) {

        if (username == null) return ResponseEntity.badRequest().build();

        Long bossId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long staffId = service.getBusinessIdByCredentialId(credentialService.getIdByUsername(username));

        if (bossId == null || !bossId.equals(staffId)) return ResponseEntity.badRequest().build();

        return update(staffId, "role", "ROLE_SUPERVISOR");
    }

    @Operation(
            summary = "Obtener un empleado por username",
            description = "Devuelve los datos del empleado si pertenece al mismo negocio que el usuario autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado"),
            @ApiResponse(responseCode = "400", description = "Acceso denegado")
    })
//    @GetMapping("/{username}")
//    public ResponseEntity<Staff> findObjectById(
//            @Parameter(description = "Username del empleado a buscar", required = true)
//            @PathVariable String username,
//
//            @Parameter(hidden = true)
//            @AuthenticationPrincipal UserDetails userDetails) {
//
//        Long clientId = clientService.getIdByCredentialId(credentialService.getIdByUsername(username));
//
//        if (!cartService.existsByBusinessIdAndClientId(
//                staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())),
//                clientId)) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        return findById(clientId);
//    }


    @GetMapping("/all")
    public ResponseEntity<List<Staff>> findAllObjects(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails) {

        cartService.findAllByBusinessId(
                staffService.getBusinessIdByCredentialId(
                        credentialService.getIdByUsername(userDetails.getUsername())
                )
        );

        return findAll();
    }
}