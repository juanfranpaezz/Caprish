package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface StockRepository extends MyObjectGenericRepository<Stock> {

    boolean existsByProductIdAndBranchId(Long productId, Long branchId);

}
