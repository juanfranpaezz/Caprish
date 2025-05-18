package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.User;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserGenericRepository<T extends User, Long> extends MyObjectGenericRepository<T, Long> {
    Optional<T> findByEmail(String email);
}
