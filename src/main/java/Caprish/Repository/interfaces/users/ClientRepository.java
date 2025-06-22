package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends MyObjectGenericRepository<Client> {
   Optional<Client> findByPhone(Integer phone);
   @Query("SELECT c.id FROM Client c WHERE c.credential.id = :credentialId")
   Long findIdByCredential_Id(Long credentialId);
   Client findByCredential_Id(Long credentialId);
   Optional<Client> findByCredential(Credential credential);





}
