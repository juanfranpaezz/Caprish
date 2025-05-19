package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.List;

public interface ItemRepository extends MyObjectGenericRepository<Item, Long> {
    List<Item> findByCartId(Long id);
}
