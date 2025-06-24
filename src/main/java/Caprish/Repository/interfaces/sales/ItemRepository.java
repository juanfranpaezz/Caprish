package Caprish.Repository.interfaces.sales;


import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends MyObjectGenericRepository<Item> {
//    List<Item> findByCartId(Long id);

    @Query(value = "SELECT ci.id FROM item ci WHERE ci.product.id = :productId", nativeQuery = true)
    Long findIdByProductId(@Param("productId") Long productId);

}
