package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Sale;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SaleRepository extends MyObjectGenericRepository<Sale, Long> {


}
