package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Service.imp.users.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios/client")
public class ClientController extends UserBasicGenericController<Client> {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        super(clientService);// para tener los metodos de servicio generico
        this.clientService = clientService;// para los metodos especificos de la clase
    }
    @GetMapping("/buscar-por-telefono")
    public ResponseEntity<List<Client>> buscarPorTelefono(@RequestParam Integer phone) {
        return ResponseEntity.ok(clientService.searchByPhone(phone));
    }
}
