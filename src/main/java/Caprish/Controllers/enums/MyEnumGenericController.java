package Caprish.Controllers.enums;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.enums.MyEnumGenericService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


public abstract class MyEnumGenericController<T extends MyEnum, R extends MyEnumGenericRepository<T, Long>, S extends MyEnumGenericService<T, R, S>> extends MyObjectGenericController<T, R, S> {

    public MyEnumGenericController(S childService) {
        super(childService);
    }

    @PutMapping("/update/{id}/{value}")
    public void update(@PathVariable Long id, @PathVariable String value) {
        service.changeValue(id, value);
    }

}
