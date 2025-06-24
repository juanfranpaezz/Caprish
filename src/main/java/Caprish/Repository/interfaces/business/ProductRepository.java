package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MyObjectGenericRepository<Product> {

        @Query(value = "SELECT " +
                "p.id AS ID_Product, " +
                "p.name AS Product_Name, " +
                "p.price AS Product_Price, " +
                "b.business_name AS Business_Name, " +
                "s.quantity AS Stock " +
                "FROM product p " +
                "JOIN stock s ON p.id = s.id_product " +
                "JOIN business b ON p.id_business = b.id " +
                "WHERE b.business_name = :businessName",
                nativeQuery = true)
    List<Object[]> getProductByBusiness(@Param("businessName") String businessName);

    Optional<Product> findProductByName(String name);

    List<Product> findByNameAndBusiness_Id(String name, Long businessId);
}
