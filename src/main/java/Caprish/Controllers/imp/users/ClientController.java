package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Service.imp.users.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clients")
public class ClientController extends UserGenericController<Client, ClientRepository, ClientService> {

    public ClientController(ClientService childService) {
        super(childService);
    }

    @GetMapping("/search/phone/{phone}")
    public List<Client> searchByPhone(@PathVariable Integer phone) {
        return service.searchByPhone(phone);
    }
}


