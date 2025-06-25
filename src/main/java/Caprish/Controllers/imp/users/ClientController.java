package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import Caprish.Model.imp.users.Credential;


@RestController
@RequestMapping("/client")
@Validated
@Tag(name = "Clientes", description = "Gestión de datos de los clientes")
public class ClientController extends MyObjectGenericController<Client, ClientRepository, ClientService> {

    @Autowired
    CredentialService credentialService;
    @Autowired
    StaffService staffService;
    @Autowired
    CartService cartService;
    @Autowired
    private ClientService clientService;
    @Autowired
    CredentialController credentialController;

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
    public ResponseEntity<String> createClient(@RequestBody Client entity, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            if (entity == null) return ResponseEntity.badRequest().build();
            Credential credential = credentialService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            if (service.findByCredential(credential).isPresent()) {
                return ResponseEntity.badRequest().body("El cliente ya existe.");
            }
            entity.setCredential(credential);
            Client saved = service.save(entity);
            return ResponseEntity.ok("Cliente creado con ID: " + saved.getId());
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
    @PutMapping("/delete")
    public ResponseEntity<String> deleteObject(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<Credential> c = credentialService.findById(credentialService.getIdByUsername(userDetails.getUsername()));
        if(c.isEmpty()) return ResponseEntity.badRequest().build();
        credentialController.update(c.get().getId(), "enabled", false);
        return ResponseEntity.ok("Borrado exitosamente");
    }

    @Operation(
            summary = "Actualizar teléfono",
            description = "Actualiza el número de teléfono del cliente autenticado"
    )
    @PutMapping("/update-phone/{phone}")
    public ResponseEntity<String> updatePhone(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable String phone) {
        return update(service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "phone", phone);
    }
    @Operation(
            summary = "Actualizar identificación fiscal",
            description = "Actualiza el número de identificación fiscal del cliente autenticado"
    )
    @PutMapping("/update-tax/{tax}")
    public ResponseEntity<String> updateTax(@AuthenticationPrincipal UserDetails userDetails,
                                            @PathVariable String tax) {
        return update(service.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "tax", tax);
    }

    @Operation(
            summary = "Buscar cliente por nombre",
            description = "Obtiene un cliente por su identificador"
    )
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @GetMapping
    public ResponseEntity<?> findObjectByUsername(@RequestParam String username, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long clientId = service.getIdByCredentialId(credentialService.getIdByUsername(username));
            Long staffId = credentialService.getIdByUsername(userDetails.getUsername());
            Long businessId = staffService.getBusinessIdByCredentialId(staffId);

            if (!cartService.existsByBusinessIdAndClientIdService(businessId, clientId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("error", "Cliente no está asociado al negocio del staff"));
            }

            return findByIdCustom(clientId);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cliente no encontrado"));
        }
    }


    @Operation(
            summary = "Ver propia cuenta de cliente",
            description = "Permite a un cliente ver sus datos "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente mostrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o sin permisos")
    })
    @GetMapping("/view-my-account")
    public ResponseEntity<Client> viewMyAccount(@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.findByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        client.getCredential().setPassword(null);
        client.setChats(null);
        return ResponseEntity.ok(client);
    }
    @Operation(
            summary = "Listar todos los clientes",
            description = "Devuelve una lista con todos los clientes registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<Client>> findAllObjects(@AuthenticationPrincipal UserDetails userDetails) {
        List<Client> clients = cartService.findClientsByBusinessId(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        for(Client client : clients) {
            client.setChats(null);
            client.getCredential().setPassword(null);
        }
        return ResponseEntity.ok(clients);
    }



}
