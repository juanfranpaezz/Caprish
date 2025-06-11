package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.messaging.ChatService;
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
import java.util.Map;

@RestController
@RequestMapping("/chat")
@Validated
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
