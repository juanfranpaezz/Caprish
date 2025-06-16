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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

            - Si el usuario autenticado es un cliente, `name` representa el nombre del negocio.
            - Si el usuario autenticado es personal (staff), `name` representa el nombre de usuario del cliente.

            Requiere autenticación y un rol válido (`CLIENT` o `STAFF`).
        """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chat encontrado y retornado correctamente"),
            @ApiResponse(responseCode = "400", description = "Nombre nulo o inválido"),
            @ApiResponse(responseCode = "404", description = "Chat no encontrado")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Chat> findObjectById(@PathVariable String name, @AuthenticationPrincipal UserDetails userDetails) {
        if(name == null) return ResponseEntity.badRequest().build();
        if(userDetails.getAuthorities().toString().equals("ROLE_CLIENT")){
            return ResponseEntity.ok(
                        service.findByBusinessIdAndClientId(
                            businessService.findIdByBusinessName(name),
                            clientService.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()))
                            )
                    );
        } else{
            return ResponseEntity.ok(
                    service.findByBusinessIdAndClientId(
                        staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())),
                        clientService.getIdByCredentialId(credentialService.getIdByUsername(name))
                        )
                    );
        }
    }

}
