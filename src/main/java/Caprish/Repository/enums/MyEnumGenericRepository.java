package Caprish.Repository.enums;

import Caprish.Model.enums.MyEnum;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MyEnumGenericRepository<T extends MyEnum, Long> extends MyObjectGenericRepository<T, Long> {
    Optional<T> findByValue(String value);
}
