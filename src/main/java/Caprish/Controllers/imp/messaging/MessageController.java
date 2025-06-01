package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.messaging.MessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/message")
@Validated
public class MessageController extends MyObjectGenericController<Message, MessageRepository, MessageService> {

    public MessageController(MessageService service) {
        super(service);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@Valid @RequestBody Message entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('USER')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
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
        public ResponseEntity<Message> findObjectById(@Positive  @PathVariable Long id) {
            return findById(id);
        }

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/all")
        @Override
        public List<Message> findAllObjects() {
            return findAll();
        }



}