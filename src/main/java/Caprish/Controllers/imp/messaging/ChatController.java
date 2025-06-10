package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Service.imp.messaging.ChatService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@Validated
public class ChatController extends MyObjectGenericController<Chat, ChatRepository, ChatService> {

    public ChatController(ChatService service) {
        super(service);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Chat> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

}
