package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.UserGenericService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class UserGenericController<M extends User, R extends UserGenericRepository<M>, S extends UserGenericService<M, R, S>> extends MyObjectGenericController<M, R, S> {

    public UserGenericController(S childService) {
        super(childService);
    }


    @PermitAll
    @PostMapping("/log")
    public ResponseEntity<String> logUser(@RequestBody M user) {
        try{
            service.log(user);
            return ResponseEntity.ok("Usuario logueado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PermitAll
    @PostMapping("/sign-up")
    @Override
    public ResponseEntity<String> createObject(@RequestBody M entity) {
        return create(entity);
    }

}

