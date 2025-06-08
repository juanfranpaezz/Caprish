package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MyObjectGenericRepository<Product> {

    List<Product> findByNombreAndBusinessId(String nombre, Long businessId);

}
