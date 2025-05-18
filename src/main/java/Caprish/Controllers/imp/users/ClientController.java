package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Service.imp.users.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClientController extends UserGenericController<Client, ClientService> {

    public ClientController(Caprish.Service.imp.users.ClientService service) {
        super(service);
    }

    @GetMapping("/busqueda/telefono/{phone}")
    public List<Client> searchByPhone(@PathVariable Integer phone) {
        return service.searchByPhone(phone);
    }
}


