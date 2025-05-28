package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserGenericRepository<M extends User> extends MyObjectGenericRepository<M> {
    Optional<M> findByEmail(String email);

    boolean existsByEmail(String email);
}
