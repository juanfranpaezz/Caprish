package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface CartRepository extends MyObjectGenericRepository<Cart> {

    boolean existsByBusinessIdAndClientId(Long businessId, Long clientId);

    @Query(value = "SELECT " +
            "c.id, " +
            "CONCAT(cl.first_name, ' ', cl.last_name) AS client_name, " +
            "ct.value AS cart_type, " +
            "cs.value AS cart_status, " +
            "CONCAT(s.first_name, ' ', s.last_name) AS staff_name, " +
            "b.id AS business_id, " +
            "SUM(p.price * i.quantity)\n AS total " +
            "FROM cart c " +
            "JOIN client cl ON cl.id = c.id_client " +
            "JOIN cart_type ct ON ct.id = c.id_cart_type " +
            "JOIN cart_status cs ON cs.id = c.id_cart_status " +
            "JOIN staff s ON s.id = c.id_staff " +
            "JOIN business b ON b.id = s.id_business " +
            "JOIN item i ON i.id_cart = c.id " +
            "JOIN product p ON p.id = i.id_product " +
            "WHERE cs.value = 'FINALIZADO' AND b.id = :idBusiness " +
            "GROUP BY c.id, cl.first_name, cl.last_name, ct.value, cs.value, s.first_name, s.last_name, b.id",
            nativeQuery = true)
    List<Object[]> getCartViewsByBusinessId(@Param("idBusiness") Long idBusiness);
}
