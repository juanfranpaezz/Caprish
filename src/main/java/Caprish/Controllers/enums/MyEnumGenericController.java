package Caprish.Controllers.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.enums.MyEnumGenericService;


public abstract class MyEnumGenericController<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M, R, S>> {
    protected final S service;

    protected MyEnumGenericController(S childService) {
        this.service = childService;
    }
}