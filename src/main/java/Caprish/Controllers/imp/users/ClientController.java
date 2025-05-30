package Caprish.Controllers.imp.users;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;


@RestController
@RequestMapping("/client")
public class ClientController extends UserGenericController<Client, ClientRepository, ClientService> {

    public ClientController(ClientService childService) {
        super(childService);
    }
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody Client entity) {
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
        public ResponseEntity<Client> findObjectById(Long id) {
            return findById(id);
        }

    @Override
    public List<Client> findAllObjects() {
        return List.of();
    }

    @PermitAll
        @GetMapping("/all")
        public List<Client> findAllObjectss() {
            return findAll();
        }

}
