package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends MyObjectGenericController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }


    @GetMapping("/all")
    public List<Product> findAll() {
        return service.findAll();
    }



}