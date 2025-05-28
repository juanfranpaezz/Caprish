package Caprish.Service.imp.business;


import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ProductService extends MyObjectGenericService<Product, ProductRepository, ProductService> {

    @Autowired
    private ImageService imageService;

    @Autowired
    ProductRepository productRepository;

    protected ProductService(ProductRepository repository) {
        super(repository);
    }

    @Override
    protected void verifySpecificAttributes(Product entity) {

    }


    public Product findByIdWithImages(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setImagenes(imageService.findByEntidadAndReferenciaId("Producto", product.getId()));
        }
        return product;
    }

}



