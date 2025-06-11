package Caprish.Controllers;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.MyObject;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
public abstract class MyObjectGenericController<M extends MyObject, R extends MyObjectGenericRepository<M>, S extends MyObjectGenericService<M, R, S>> {
    protected final S service;

    protected MyObjectGenericController(S childService) {
        this.service = childService;
    }

    public ResponseEntity<String> create(M entity) {
        try {
            if (entity == null) {
                ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(String.valueOf(service.save(entity)));
        } catch (InvalidEntityException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<List<M>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    public ResponseEntity<M> findById(Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> update(Long id, String fieldName, Object objectValue) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.changeField(id, fieldName, objectValue);
        return ResponseEntity.ok("Update successful");
    }

    public ResponseEntity<String> delete(Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.badRequest().body("Objeto no encontrado");
        }
        service.deleteById(id);
        return ResponseEntity.ok("Eliminado con exito");
    }
}

