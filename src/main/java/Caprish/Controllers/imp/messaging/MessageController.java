package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.EntityNotFoundCustomException;
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

@RestController
@RequestMapping("/message")
@Validated
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

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@AuthenticationPrincipal UserDetails userDetails,
                                              @RequestBody Map<String,String> payload) {

        String businessName = payload.get("businessName");
        String content      = payload.get("content");
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

        // Obtener o crear chat
        Chat chat = chatService.findByBusinessIdAndClientId(businessId, clientId);
        if (chat == null) {
            chat = new Chat();
            chat.setBusiness(businessService.findById(businessId)
                    .orElseThrow(() -> new EntityNotFoundCustomException("Negocio no encontrado")));
            chat.setClient(clientService.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundCustomException("Cliente no encontrado")));
            chat = chatService.save(chat);
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
