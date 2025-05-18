package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.users.UserGenericService;


public abstract class UserGenericController<T extends User, R extends UserGenericRepository<T, Long>, S extends UserGenericService<T, R, S>> extends MyObjectGenericController<T, R, S> {

    public UserGenericController(S childService) {
        super(childService);
    }

}



/*
@RestController
@RequestMapping("/client")
public abstract class ClientController extends UserGenericController<Client>{

    @Autowired
    ClientService clientService;
    protected ClientController(ClientService repository) {
        super(repository);
    }

    @PostMapping("/delete")
    public ResponseEntity eliminarUsuario(@RequestBody Client user) {

    }

    @PostMapping("/log")
    public ResponseEntity logUser(@RequestBody Client user) throws UserException {
        service.log(user);
        return ResponseEntity.ok("Usuario logueado exitosamente");
    }

    @PostMapping("/login/password-change")
    public ResponseEntity changePassword(Long id, @RequestBody String newPassword){
        service.changePassword(id, newPassword);
        return ResponseEntity.ok("Contrasenia cambiada exitosamente");
    }
}
 */
