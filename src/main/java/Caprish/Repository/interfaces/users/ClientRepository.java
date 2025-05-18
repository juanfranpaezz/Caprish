package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends UserGenericRepository<Client, Long> {
   Optional<Client> findByPhone(Integer phone);


}
