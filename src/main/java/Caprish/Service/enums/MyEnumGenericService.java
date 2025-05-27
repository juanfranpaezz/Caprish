package Caprish.Service.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.enums.MyEnumGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import java.util.Optional;

@Slf4j
public abstract class MyEnumGenericService<M extends MyEnum, R extends MyEnumGenericRepository<M>, S extends MyEnumGenericService<M,R,S>> extends MyObjectGenericService<M, R, S> {

    protected MyEnumGenericService(R childRepository) {
        super(childRepository);
    }

    public Optional<M> findByValue(String email) {
        return repository.findByValue(email);
    }
    @Override
    protected void verifySpecificAttributes(M entity) {

    }
}