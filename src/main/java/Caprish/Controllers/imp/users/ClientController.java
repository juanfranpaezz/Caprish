package Caprish.Controllers.imp.users;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/client")
public class ClientController extends UserGenericController<Client, ClientRepository, ClientService> {

    public ClientController(ClientService childService) {
        super(childService);
    }


    @GetMapping("/searchPhone/{phone}")
    public ResponseEntity<Client> findByPhone(@PathVariable Integer phone) {
        return  ResponseEntity.ok(service.searchByPhone(phone));
    }

    @PutMapping("/updatePhone/{id}/{phone}")
    public ResponseEntity<Integer> update(@PathVariable Long id, @PathVariable Integer phone) {
        return ResponseEntity.ok(service.changeField(id, "phone", phone));
    }

    @PostMapping("/log")
    public ResponseEntity<String> logUser(@RequestBody Client user) {
        try{
            service.log(user);
            return ResponseEntity.ok("Usuario logueado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("sign-up")
    public ResponseEntity<String> createe(@RequestBody Client entity) {
        if (entity == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            return ResponseEntity.ok(service.save(entity));
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
