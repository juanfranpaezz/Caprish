package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectController;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController extends MyObjectController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }

}