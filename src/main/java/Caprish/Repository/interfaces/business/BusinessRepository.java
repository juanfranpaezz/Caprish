package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.business.dto.BusinessViewDTO;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends MyObjectGenericRepository<Business> {

    boolean existsByBusinessName(String businessName);
    boolean existsByTax(int tax);
    @Query(value = """
    SELECT 
        b.id AS id,
        b.business_name AS businessName,
        b.description AS description,
        b.slogan AS slogan,
        b.tax AS tax
    FROM business b
    LEFT JOIN staff s ON s.business_id = b.id
    WHERE b.id = :businessId
    """, nativeQuery = true)
    BusinessViewDTO findByBusinessId(@Param ("businessId") Integer businessId);
}
