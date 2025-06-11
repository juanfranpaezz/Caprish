package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StockRepository extends MyObjectGenericRepository<Stock> {

    boolean existsByProductIdAndBranchId(Long productId, Long branchId);
        @Query(value = """
        SELECT 
            s.quantity AS cantidad_en_stock
        FROM stock s
        JOIN product p ON s.id_product = p.id
        JOIN branch br ON s.id_branch = br.id
        JOIN business b ON br.id_business = b.id
        WHERE p.name = :productName
          AND b.business_name = :businessName
        """, nativeQuery = true)
     int findStockByProductAndBusiness(
            @Param("productName") String productName,
            @Param("businessName") String businessName
    );
}
