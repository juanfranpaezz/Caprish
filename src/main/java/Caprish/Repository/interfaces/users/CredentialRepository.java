package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CredentialRepository extends MyObjectGenericRepository<Credential> {
    Optional<Credential> findByUsername(String username);
}
