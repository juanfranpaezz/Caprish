package Caprish.Repository.interfaces;

import Caprish.Model.imp.MyObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyObjectGenericRepository<M extends MyObject> extends JpaRepository<M, Long> {
}
