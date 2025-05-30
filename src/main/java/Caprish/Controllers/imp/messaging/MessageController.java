package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.messaging.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController extends MyObjectGenericController<Message, MessageRepository, MessageService> {

    public MessageController(MessageService service) {
        super(service);
    }

    /** Crea un mensaje y devuelve el objeto completo JSON */
    @PostMapping
    public ResponseEntity<Message> createA(@RequestBody Message msg) {
        return ResponseEntity.ok(service.saveA(msg));
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<List<Message>> findByChat(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByChatId(id));
    }
}
