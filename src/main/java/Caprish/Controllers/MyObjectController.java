package Caprish.Controllers;

import Caprish.Model.imp.MyObject;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class MyObjectController<T extends MyObject, R extends MyObjectGenericRepository<T, Long>, S extends MyObjectGenericService<T, R>> {

    protected final S service;

    protected MyObjectController(S childService) {
        this.service = childService;
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
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.ok("Usuario no encontrado");
        }
        service.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}

