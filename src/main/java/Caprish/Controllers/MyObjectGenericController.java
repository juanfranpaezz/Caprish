package Caprish.Controllers;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.MyObject;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
public abstract class MyObjectGenericController<M extends MyObject, R extends MyObjectGenericRepository<M>, S extends MyObjectGenericService<M, R, S>> {

    protected final S service;

    protected MyObjectGenericController(S childService) {
        this.service = childService;
    }

    @GetMapping
    public List<M> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<M> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<String> create(@RequestBody M entity){
        try{
            if (entity == null) {
                ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(service.save(entity));
        } catch (InvalidEntityException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("guardado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }
        service.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}

