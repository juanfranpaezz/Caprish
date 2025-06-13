package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StaffRepository extends MyObjectGenericRepository<Staff> {
    @Query("SELECT s.business.id FROM Staff s WHERE s.credential.id = :credentialId")
    Long getBusinessIdByCredentialId(@Param("credentialId") Long credentialId);

    @Query("SELECT s FROM Staff s WHERE s.credential.id = :credentialId")
    Staff findAllByCredentialId(Long credentialId);
}
