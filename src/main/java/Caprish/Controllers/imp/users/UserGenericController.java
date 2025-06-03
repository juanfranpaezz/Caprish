package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.users.UserGenericService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class UserGenericController extends MyObjectGenericController<User, UserGenericRepository, UserGenericService> {

    public UserGenericController(UserGenericService childService) {
        super(childService);
    }


    @PermitAll
    @PostMapping("/log")
    public ResponseEntity<String> logUser(@RequestBody User user) {
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
    public ResponseEntity<String> createObject(@RequestBody User entity) {
        return create(entity);
    }

    @Override
    public ResponseEntity<String> deleteObject(Long id) {
        return delete(id);
    }

    @Override
    public ResponseEntity<User> findObjectById(Long id) {
        return findById(id);
    }

    @Override
    public List<User> findAllObjects() {
        return List.of();
    }


}

