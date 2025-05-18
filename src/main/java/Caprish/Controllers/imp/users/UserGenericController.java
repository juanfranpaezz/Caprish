package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.User;
import Caprish.Service.imp.users.UserGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class UserGenericController<T extends User, S extends UserGenericService<T>> {

    protected final S service;

    protected UserGenericController(S service) {
        this.service = service;
    }

    @GetMapping
    public List<T> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entity) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
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
