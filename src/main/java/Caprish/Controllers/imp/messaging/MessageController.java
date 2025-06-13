package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.enums.SenderType;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.enums.SenderTypeRepository;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.messaging.ChatService;
import Caprish.Service.imp.messaging.MessageService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@Validated
@Tag(name = "Mensajes", description = "Gestión de mensajes entre clientes y empresas")
@SecurityRequirement(name = "bearerAuth")
public class MessageController extends MyObjectGenericController<Message, MessageRepository, MessageService> {

    @Autowired private StaffService staffService;
    @Autowired private ClientService clientService;
    @Autowired private BusinessService businessService;
    @Autowired private CredentialService credentialService;
    @Autowired private ChatService chatService;
    @Autowired private SenderTypeRepository senderTypeRepository;

    public MessageController(MessageService service) {
        super(service);
    }

    @Operation(
            summary = "Enviar mensaje",
            description = """
            Envía un mensaje en el contexto de un chat existente entre un cliente y un negocio.
            El sistema detecta si quien envía es un CLIENT o un STAFF según los roles del usuario autenticado.
            Se requiere que ya exista un chat entre ese cliente y el negocio.
        """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensaje enviado correctamente"),
            @ApiResponse(responseCode = "400", description = "Faltan campos, negocio no encontrado o no existe chat previo"),
            @ApiResponse(responseCode = "404", description = "Tipo de remitente (SenderType) no encontrado")
    })
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload) {

        String businessName = payload.get("businessName");
        String content = payload.get("content");

        if (businessName == null || content == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Faltan businessName o content.");
        }

        if (!businessService.existsByBusinessName(businessName)) {
            return ResponseEntity
                    .badRequest()
                    .body("Negocio inexistente.");
        }

        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());

        boolean isClient = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"));

        Long businessId = isClient
                ? businessService.findIdByBusinessName(businessName)
                : staffService.getBusinessIdByCredentialId(credentialId);

        Long clientId = isClient
                ? clientService.getIdByCredentialId(credentialId)
                : clientService.getIdByCredentialId(
                credentialService.getIdByUsername(businessName)
        );

        Chat chat = chatService.findByBusinessIdAndClientId(businessId, clientId);
        if (chat == null) {
            return ResponseEntity
                    .badRequest()
                    .body("No existe un chat previo entre ese negocio y cliente.");
        }

        String senderTypeId = isClient ? "CLIENT" : "STAFF";
        SenderType senderType = senderTypeRepository
                .findById(senderTypeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SenderType no encontrado: " + senderTypeId
                ));

        Message message = new Message();
        message.setChat(chat);
        message.setContent(content);
        message.setSender_type(senderType);

        return create(message);
    }
}