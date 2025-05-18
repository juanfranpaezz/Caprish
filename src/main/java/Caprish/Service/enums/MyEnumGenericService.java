package Caprish.Service.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import java.util.Optional;


public abstract class MyEnumGenericService<T extends MyEnum, R extends MyEnumGenericRepository<T, Long>, S extends MyEnumGenericService<T,R,S>> extends MyObjectGenericService<T, R> {

    protected MyEnumGenericService(R childRepository) {
        super(childRepository);
    }

    public Optional<T> findByValue(String email) {
        return repository.findByValue(email);
    }

    @SuppressWarnings("unchecked")
    public void changeValue(Long id, String value) {
        ((S) AopContext.currentProxy()).updateField(id, "value", value);
    }

}