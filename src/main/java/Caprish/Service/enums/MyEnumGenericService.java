package Caprish.Service.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;


@Slf4j
public abstract class MyEnumGenericService<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M,R,S>> {

    protected final R repository;


    protected MyEnumGenericService(R childRepository) {
        this.repository = childRepository;
    }


    public Optional<M> findById(String value) {
        return repository.findById(value);
    }

}