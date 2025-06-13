package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
@Validated
@Tag(name = "Clientes", description = "Gestión de datos de los clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClientController extends MyObjectGenericController<Client, ClientRepository, ClientService> {

    @Autowired
    CredentialService credentialService;

    public ClientController(ClientService childService) {
        super(childService);
    }

    @Operation(
            summary = "Crear cuenta de cliente",
            description = "Permite a un usuario autenticado con rol CLIENT crear su cuenta de cliente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o sin permisos")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createClient(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody Client entity) {

        try {
            if (entity == null || !userDetails.getAuthorities().toString().equals("ROLE_CLIENT")) {
                return ResponseEntity.badRequest().build();
            }
            entity.setId(credentialService.getIdByUsername(userDetails.getUsername()));
            return ResponseEntity.ok(String.valueOf(service.save(entity)));
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Eliminar cliente",
            description = "Elimina un cliente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(
            @Parameter(description = "ID del cliente a eliminar", required = true)
            @Positive @PathVariable Long id) {

        return delete(id);
    }

    @Operation(
            summary = "Actualizar teléfono",
            description = "Actualiza el número de teléfono del cliente autenticado"
    )
    @PutMapping("/updatePhone")
    public ResponseEntity<String> updatePhone(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload) {

        return update(
                service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())),
                "phone",
                payload.get("phone")
        );
    }

    @Operation(
            summary = "Actualizar identificación fiscal",
            description = "Actualiza el número de identificación fiscal del cliente autenticado"
    )
    @PutMapping("/updateTax")
    public ResponseEntity<String> updateTax(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload) {

        return update(
                service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())),
                "tax",
                payload.get("tax")
        );
    }

    @Operation(
            summary = "Buscar cliente por ID",
            description = "Obtiene un cliente por su identificador"
    )
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Client> findObjectById(
            @Parameter(description = "ID del cliente", required = true)
            @Positive @PathVariable Long id) {

        return findById(id);
    }

    @Operation(
            summary = "Listar todos los clientes",
            description = "Devuelve una lista con todos los clientes registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<Client>> findAllObjects() {
        return findAll();
    }
}
