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
    public ResponseEntity<String> createObject(@Valid @RequestBody Message entity) {
        return create(entity);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Message>> findAllObjects() {
        return findAll();
    }

}
