package Caprish.Repository.interfaces;

import Caprish.Model.imp.MyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.function.Consumer;

@NoRepositoryBean
public interface MyObjectGenericRepository<T extends MyObject, Long> extends JpaRepository<T, Long> {
}
