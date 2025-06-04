package Caprish.Repository.enums;

import Caprish.Model.enums.MyEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface MyEnumGenericRepository<M extends MyEnum> extends JpaRepository<M, Long> {
    Optional<M> findById(String email);
    boolean existsById(String id);
}
