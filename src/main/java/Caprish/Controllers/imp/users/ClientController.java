package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/clients")
public class ClientController extends UserGenericController<Client, ClientRepository, ClientService> {

    public ClientController(ClientService childService) {
        super(childService);
    }


    @GetMapping("/search/phone/{phone}")
    public ResponseEntity<Client> findByPhone(@PathVariable Integer phone) {
        return service.searchByPhone(phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}/{phone}")
    public ResponseEntity<Integer> update(@PathVariable Long id, @PathVariable Integer phone) {
        return ResponseEntity.ok(service.changePhone(id, phone));
    }

}


