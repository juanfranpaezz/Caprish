package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.List;

public interface ItemRepository extends MyObjectGenericRepository<Item> {
    List<Item> findByCartId(Long id);
    Long findIdByProductId(Long productId);
}
