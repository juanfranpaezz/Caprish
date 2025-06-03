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
        public ResponseEntity<String> createObject(@RequestBody Chat entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(Long id) {
            return delete(id);
        }


        @Override
        public ResponseEntity<Chat> findObjectById(Long id) {
            return findById(id);
        }


        @GetMapping("/all")
        @Override
        public List<Chat> findAllObjects() {
            return findAll();
        }


    /** Si quieres exponer también mensajes embebidos:
     *  GET /api/chat/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Chat> findById(@PathVariable Long id) {
        return super.findById(id);
    }
}
