package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface ClientRepository extends MyObjectGenericRepository<Client> {
   Optional<Client> findByPhone(Integer phone);
   Long findIdByCredential_Id(Long credentialId);
}
