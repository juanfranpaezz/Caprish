package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.messaging.MessageService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@PermitAll
@RestController
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController extends MyObjectGenericController<Message, MessageRepository, MessageService> {

    public MessageController(MessageService service) {
        super(service);
    }

    @PermitAll
    @PostMapping("/post")
    public ResponseEntity<Message> createA(@RequestBody Message msg) {
        return ResponseEntity.ok(service.saveA(msg));
    }

    @PermitAll
    @GetMapping("/view/{id}")
    public ResponseEntity<List<Message>> findByChat(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByChatId(id));
    }

    @GetMapping("/viewB/{id}")
    public ResponseEntity<List<Message>> findByChatB(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByChatId(id));
    }

    @PermitAll
    @PostMapping
        @Override
        public ResponseEntity<String> createObject(@RequestBody Message entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(Long id) {
            return delete(id);
        }

        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(Long id) {
            return update(id);
        }

        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Message> findObjectById(Long id) {
            return findById(id);
        }

        @GetMapping("/all")
        @Override
        public List<Message> findAllObjects() {
            return findAll();
        }



}
