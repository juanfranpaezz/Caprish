package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Service.imp.messaging.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/chat")
public class ChatController extends MyObjectGenericController<Chat, ChatRepository, ChatService> {

    public ChatController(ChatService service) {
        super(service);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody Chat entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
            return delete(id);
        }

        /*@PreAuthorize("hasRole('USER')")
        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
            return update(id);
        }*/

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Chat> findObjectById(@Valid @PathVariable Long id) {
            return findById(id);
        }

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/all")
        @Override
        public List<Chat> findAllObjects() {
            return findAll();
        }


}