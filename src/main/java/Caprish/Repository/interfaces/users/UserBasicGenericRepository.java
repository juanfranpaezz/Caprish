package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserBasicGenericRepository<T extends User, Long>
        extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email);
}
