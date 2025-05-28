package Caprish.Controllers;

import Caprish.Model.imp.MyObject;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public M create(@RequestBody M entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<M> update(@PathVariable Long id, @RequestBody M entity) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.ok("Usuario no encontrado");
        }
        service.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}

