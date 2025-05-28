package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends MyObjectGenericRepository<Cart> {

    @Query(value = "SELECT " +
            "c.id, cl.name, ct.name, cs.name, c.sale_date, s.name, b.id, SUM(i.price * i.quantity) " +
            "FROM cart c " +
            "JOIN client cl ON cl.id = c.id_client " +
            "JOIN cart_type ct ON ct.id = c.id_cart_type " +
            "JOIN cart_status cs ON cs.id = c.id_cart_status " +
            "JOIN staff s ON s.id = c.id_staff " +
            "JOIN business b ON b.id = s.id_business " +
            "JOIN item i ON i.id_cart = c.id " +
            "WHERE cs.name = 'FINALIZADO' AND b.id = :idBusiness " +
            "GROUP BY c.id, cl.name, ct.name, cs.name, c.sale_date, s.name, b.id",
            nativeQuery = true)
    List<Object[]> getCartViewsByBusinessId(@Param("idBusiness") Long idBusiness);

}

