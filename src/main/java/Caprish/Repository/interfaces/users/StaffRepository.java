package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Staff;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends MyObjectGenericRepository<Staff> {
    Optional<Staff> findByCredential(Credential credential);

    @Query(value = """
        SELECT 
            s.id AS staff_id,
            c.first_name,
            c.last_name,
            c.id_role,
            b.id AS business_id,
            b.name AS business_name
        FROM staff s
        JOIN credential c ON c.id = s.id_credential
        JOIN business b ON b.id = s.id_business
        WHERE b.id = :businessId
        """, nativeQuery = true)
    List<Object[]> findStaffByBusiness(@Param("businessId") Long businessId);
}
