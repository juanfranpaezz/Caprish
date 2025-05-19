package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Sale;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface SaleRepository extends MyObjectGenericRepository<Sale, Long> {
    Optional<Sale> findByCartId(Long id);
}
