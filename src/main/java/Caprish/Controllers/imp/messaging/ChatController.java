package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Service.imp.messaging.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController extends MyObjectGenericController<Chat, ChatRepository, ChatService> {

    public ChatController(ChatService service) {
        super(service);
    }
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Chat entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Chat> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/updateBusinessId/{id}/{businessId}")
    public ResponseEntity<String> updateBusinessId(@PathVariable @Positive Long id,
                                                   @PathVariable @Positive Long businessId) {
        return update(id, "business_id", businessId);
    }

    @PutMapping("/updateClientId/{id}/{clientId}")
    public ResponseEntity<String> updateClientId(@PathVariable @Positive Long id,
                                                 @PathVariable @Positive Long clientId) {
        return update(id, "client_id", clientId);
    }

    @GetMapping("/all")
    @Override
    public List<Chat> findAllObjects() {
        return findAll();
    }
}
