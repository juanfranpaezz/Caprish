package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MyObjectGenericRepository<Product> {

    List<Product> findByNameAndBusiness_Id(String name, Long businessId);


}
