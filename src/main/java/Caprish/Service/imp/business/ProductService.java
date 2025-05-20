package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService extends MyObjectGenericService<Product, ProductRepository> {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    protected ProductService(ProductRepository repository) {
        super(repository);
    }

    public int changePrice(Long id, BigDecimal price) {
        return ((ProductService) AopContext.currentProxy()).updateField(id, "price", price);
    }

    public Product findByIdWithImages(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setImagenes(imageService.findByEntidadAndReferenciaId("Producto", product.getId()));
        }
        return product;
    }
}
