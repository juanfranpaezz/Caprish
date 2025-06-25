package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CredentialRepository extends MyObjectGenericRepository<Credential> {
    Optional<Credential> findByUsername(String username);

    boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query("""
    UPDATE Credential c
    SET c.enabled = false
    WHERE c IN (
        SELECT s.credential
        FROM Staff s
        WHERE s.business.id = :businessId
    )
        """)
    void blockStaff(@Param("businessId") Long BusinessId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE credential SET id_role = 'ROLE_SUPERVISOR' WHERE id = :staff", nativeQuery = true)
    void promoteStaff(@Param("staff") Long staff);

}
