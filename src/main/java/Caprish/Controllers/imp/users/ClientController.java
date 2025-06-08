package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/client")
@Validated
public class ClientController extends MyObjectGenericController<Client, ClientRepository, ClientService> {

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
    public ResponseEntity<String> deleteObject(@PathVariable Long id) {
        return delete(id);
    }

    @PutMapping("/updatePhone/{id}/{phone}")
    public ResponseEntity<String> updatePhone(@PathVariable @Positive Long id,
                                              @PathVariable Integer phone) {
        return update(id, "phone", phone);
    }

    @PutMapping("/updateTax/{id}/{tax}")
    public ResponseEntity<String> updateTax(@PathVariable @Positive Long id,
                                            @PathVariable String tax) {
        return update(id, "tax", tax);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Client> findObjectById(@PathVariable Long id) {
        return findById(id);
    }


    @GetMapping("/all")
    @Override
    public ResponseEntity<List<Client>> findAllObjects() {
        return findAll();
    }

}
