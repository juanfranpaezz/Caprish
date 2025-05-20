package Caprish.Service.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import java.util.Optional;


public abstract class MyEnumGenericService<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M,R,S>> extends MyObjectGenericService<M, R> {

    protected MyEnumGenericService(R childRepository) {
        super(childRepository);
    }

    public Optional<M> findByValue(String email) {
        return repository.findByValue(email);
    }

    @SuppressWarnings("unchecked")
    public void changeValue(Long id, String value) {
        ((S) AopContext.currentProxy()).updateField(id, "value", value);
    }

}