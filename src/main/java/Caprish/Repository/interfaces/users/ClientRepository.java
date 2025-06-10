package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends MyObjectGenericRepository<Client> {
   Optional<Client> findByPhone(Integer phone);
   Optional<Client> findByCredential(Credential credential);
}
