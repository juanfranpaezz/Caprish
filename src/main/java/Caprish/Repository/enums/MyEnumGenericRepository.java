package Caprish.Repository.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MyEnumGenericRepository<M extends MyEnum> extends MyObjectGenericRepository<M> {
    Optional<M> findByValue(String email);
}
