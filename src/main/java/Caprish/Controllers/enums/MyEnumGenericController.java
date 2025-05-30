package Caprish.Controllers.enums;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.enums.MyEnumGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public abstract class MyEnumGenericController<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M, R, S>> {
    protected final S service;
    protected MyEnumGenericController(S childService) {
        this.service = childService;
    }

/*
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


    public ResponseEntity<String> create(M entity){
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
    }*/
}