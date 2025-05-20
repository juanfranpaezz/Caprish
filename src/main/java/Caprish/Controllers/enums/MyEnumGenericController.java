package Caprish.Controllers.enums;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.enums.MyEnumGenericService;


public abstract class MyEnumGenericController<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M, R, S>> extends MyObjectGenericController<M, R, S> {
    public MyEnumGenericController(S childService) {
        super(childService);
    }

}