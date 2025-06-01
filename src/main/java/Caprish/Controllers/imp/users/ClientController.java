package Caprish.Controllers.imp.users;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;


@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/client")
@Validated
public class ClientController extends UserGenericController<Client, ClientRepository, ClientService> {

    public ClientController(ClientService childService) {
        super(childService);
    }
    @PermitAll
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@Valid @RequestBody Client entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('ADMIN')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
            return delete(id);
        }

        @PreAuthorize("hasRole('CLIENT')")
            @PutMapping("/updatePhone/{id}/{phone}")
                public ResponseEntity<String> updatePhone(@PathVariable @Positive Long id, @PathVariable String phone) {
                    return update(id, "phone", phone);
        }

        @PreAuthorize("hasRole('CLIENT')")
            @PutMapping("/updateTax/{id}/{tax}")
                    public ResponseEntity<String> updateTax(@PathVariable @Positive Long id, @PathVariable Long tax) {
                        return update(id, "tax", tax);
                    }


        @PreAuthorize("hasRole('CLIENT')")
        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Client> findObjectById(@Positive @PathVariable Long id) {
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
