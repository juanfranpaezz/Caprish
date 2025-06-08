package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.messaging.MessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@Validated
public class MessageController extends MyObjectGenericController<Message, MessageRepository, MessageService> {

    public MessageController(MessageService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Message entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Message> findObjectById(@PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/updateChatId/{id}/{chatId}")
    public ResponseEntity<String> updateChatId(@PathVariable @Positive Long id,
                                               @PathVariable @Positive Long chatId) {
        return update(id, "chat_id", chatId);
    }

    @PutMapping("/updateSenderId/{id}/{senderId}")
    public ResponseEntity<String> updateSenderId(@PathVariable @Positive Long id,
                                                 @PathVariable @Positive Long senderId) {
        return update(id, "sender_id", senderId);
    }

    @PutMapping("/updateContent/{id}/{content}")
    public ResponseEntity<String> updateContent(@PathVariable @Positive Long id,
                                                @PathVariable String content) {
        return update(id, "content", content);
    }

    @PutMapping("/updateSentAt/{id}/{timestamp}")
    public ResponseEntity<String> updateSentAt(@PathVariable @Positive Long id,
                                               @PathVariable String timestamp) {
        return update(id, "sent_at", timestamp);
    }

    @GetMapping("/all")
    @Override
    public List<Message> findAllObjects() {
        return findAll();
    }

}
