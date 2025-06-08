package Caprish.Controllers.enums;

import Caprish.Exception.InvalidEntityException;
import Caprish.Model.enums.MyEnum;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.enums.MyEnumGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class MyEnumGenericController<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M, R, S>> {
    protected final S service;

    protected MyEnumGenericController(S childService) {
        this.service = childService;

    }


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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
public abstract class MyEnumGenericController<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M, R, S>> extends MyObjectGenericController<M, R, S> {
    public MyEnumGenericController(S childService) {
        super(childService);
    }



    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody M entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(Long id) {
            return delete(id);
        }

        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(Long id) {
            return update(id);
        }

        @GetMapping("/{id}")
        @Override
        public ResponseEntity<M> findObjectById(Long id) {
            return findById(id);
        }

        @GetMapping("/all")
        @Override
        public List<M> findAllObjects() {
            return findAll();
        }
*/