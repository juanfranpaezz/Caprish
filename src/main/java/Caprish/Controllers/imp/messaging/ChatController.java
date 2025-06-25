package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.messaging.ChatService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/chat")
@Validated
@Tag(name = "Chats", description = "Gestión de chats entre clientes y empresas")
public class ChatController extends MyObjectGenericController<Chat, ChatRepository, ChatService> {

    @Autowired
    BusinessService businessService;
    @Autowired
    ClientService clientService;
    @Autowired
    CredentialService credentialService;
    @Autowired
    StaffService staffService;

    public ChatController(ChatService service) {
        super(service);
    }


    @Operation(
            summary = "Obtener chat activo",
            description = """
        Obtiene el chat activo entre un cliente y una empresa.

        - Si el usuario autenticado es un CLIENT, `name` representa el nombre del negocio.
        - Si el usuario autenticado es STAFF, `name` representa el nombre de usuario del cliente.
        """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chat encontrado y retornado correctamente"),
            @ApiResponse(responseCode = "400", description = "Nombre nulo o inválido"),
            @ApiResponse(responseCode = "404", description = "Chat no encontrado")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Chat> findChat(@PathVariable String name,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        if (name == null || name.isBlank() || userDetails == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isClient = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));

        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId;
        Long businessId;

        if (isClient) {
            clientId = clientService.getIdByCredentialId(credentialId);
            Optional<Long> bizOpt = Optional.ofNullable(businessService.findIdByBusinessName(name));
            if (bizOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            businessId = bizOpt.get();
        } else {
            businessId = staffService.getBusinessIdByCredentialId(credentialId);
            Long clientCredId = credentialService.getIdByUsername(name);
            if (clientCredId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            clientId = clientService.getIdByCredentialId(clientCredId);
        }

        Chat chat = service.findByBusinessIdAndClientId(businessId, clientId);
        if (chat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(chat);
    }

}
