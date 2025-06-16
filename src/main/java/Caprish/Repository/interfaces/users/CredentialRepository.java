package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CredentialRepository extends MyObjectGenericRepository<Credential> {
    Optional<Credential> findByUsername(String username);
    @Modifying
    @Transactional
    @Query(value = """
    UPDATE credential c
    JOIN staff s ON c.id = s.id_credential
    JOIN business b ON s.business_id = b.id
    SET c.enabled = false
    WHERE b.id = :businessId
      AND c.id_role IN ('ROLE_BOSS', 'ROLE_EMPLOYEE', 'ROLE_SUPERVISOR')
""", nativeQuery = true)

    int disableCredentialsForBusiness(@Param("businessId") Long BusinessId);


}
