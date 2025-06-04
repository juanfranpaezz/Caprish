package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.UserGenericService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public abstract class UserGenericController<M extends User, R extends UserGenericRepository<M>, S extends UserGenericService<M, R, S>> extends MyObjectGenericController<M, R, S> {

    public UserGenericController(S childService) {
        super(childService);
    }

    @PostMapping("/log")
    @Validated
    public ResponseEntity<String> logUser(@Valid @RequestBody M user) {
        try{
            service.log(user);
            return ResponseEntity.ok("Usuario logueado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateFirstName/{id}/{firstName}")
    public ResponseEntity<String> updateFirstName(@PathVariable @Positive Long id,
                                                  @PathVariable String firstName) {
        return update(id, "first_name", firstName);
    }

    @PutMapping("/updateLastName/{id}/{lastName}")
    public ResponseEntity<String> updateLastName(@PathVariable @Positive Long id,
                                                 @PathVariable String lastName) {
        return update(id, "last_name", lastName);
    }

    @PutMapping("/updateEmail/{id}/{email}")
    public ResponseEntity<String> updateEmail(@PathVariable @Positive Long id,
                                              @PathVariable String email) {
        return update(id, "email", email);
    }

    @PutMapping("/updatePasswordHash/{id}/{passwordHash}")
    public ResponseEntity<String> updatePasswordHash(@PathVariable @Positive Long id,
                                                     @PathVariable String passwordHash) {
        return update(id, "password_hash", passwordHash);
    }

    @PutMapping("/updateRoleId/{id}/{roleId}")
    public ResponseEntity<String> updateRoleId(@PathVariable @Positive Long id,
                                               @PathVariable @Positive Long roleId) {
        return update(id, "role_id", roleId);
    }

    @PostMapping("/sign-up")
    @Override
    @Validated
    public ResponseEntity<String> createObject(@Valid @RequestBody M entity) {
        return create(entity);
    }

    

}

