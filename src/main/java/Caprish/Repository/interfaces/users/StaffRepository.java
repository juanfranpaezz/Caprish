package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends MyObjectGenericRepository<Staff> {
    @Query("SELECT s.business.id FROM Staff s WHERE s.credential.id = :credentialId")
    Long getBusinessIdByCredentialId(@Param("credentialId") Long credentialId);
}
