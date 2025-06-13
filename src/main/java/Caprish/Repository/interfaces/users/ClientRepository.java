package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends MyObjectGenericRepository<Client> {
   Optional<Client> findByPhone(Integer phone);
   Long findIdByCredential_Id(Long credentialId);
   Client findByCredential_Id(Long credentialId);

   @Query("SELECT c FROM Client c WHERE c.credential.id = :credentialId")
   Client findByCredential(@Param("credentialId")Long credential);




}
