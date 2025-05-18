package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Client;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends UserGenericRepository<Client, Long> {
    List<Client> findByPhone(Integer phone);
}
