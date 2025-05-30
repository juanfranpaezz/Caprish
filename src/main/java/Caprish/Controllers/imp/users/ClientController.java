package Caprish.Controllers.imp.users;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;


@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/client")
public class ClientController extends UserGenericController<Client, ClientRepository, ClientService> {

    public ClientController(ClientService childService) {
        super(childService);
    }
    @PermitAll
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody Client entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
            return delete(id);
        }

        /*@PreAuthorize("hasRole('CLIENT')")
        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
            return update(id);
        }*/

        @PreAuthorize("hasRole('CLIENT')")
        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Client> findObjectById(@Valid @PathVariable Long id) {
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
