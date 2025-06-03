package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MyObjectGenericRepository<Client> {
   Optional<Client> findByPhone(Integer phone);


}
