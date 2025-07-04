package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface StaffRepository extends MyObjectGenericRepository<Staff> {
    @Query("SELECT s.business.id FROM Staff s WHERE s.credential.id = :credentialId")
    Long getBusinessIdByCredentialId(@Param("credentialId") Long credentialId);

    @Query("SELECT s FROM Staff s WHERE s.credential.id = :credentialId")
    Staff findByCredentialId(Long credentialId);

    @Query(value = """
        SELECT 
            s.id AS staff_id,
            c.first_name,
            c.last_name,
            c.id_role,
            b.id AS id_business,
            b.business_name AS business_name
        FROM staff s
        JOIN credential c ON c.id = s.id_credential
        JOIN business b ON b.id = s.business_id
        WHERE b.id = :businessId
        """, nativeQuery = true)
    List<Object[]> findStaffByBusiness(@Param("businessId") Long businessId);
}
