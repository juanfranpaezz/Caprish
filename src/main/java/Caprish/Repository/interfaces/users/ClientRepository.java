// src/main/java/Caprish/Repository/interfaces/users/ClientRepository.java
package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Client;
import java.util.List;

public interface ClientRepository extends UserBasicGenericRepository<Client, Long> {
    List<Client> findByPhone(Integer phone);
}
