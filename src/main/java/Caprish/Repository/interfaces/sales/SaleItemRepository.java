package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.SaleItem;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SaleItemRepository extends MyObjectGenericRepository<SaleItem, Long> {
}
