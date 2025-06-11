package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Product;
import Caprish.Model.imp.business.dto.ProductViewDTO;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/product")
@Validated
public class ProductController extends MyObjectGenericController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }

    @PostMapping("/create")
        public ResponseEntity<String> createObject(@Valid @RequestBody Product entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
            return delete(id);
        }



    @PostMapping("/show-product")
    public ResponseEntity<Product> findObjectByName(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        try{
            return ResponseEntity.ok(service.findByName(name));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<String> updateName(@PathVariable @Positive Long id,
                                             @PathVariable String name) {
        return update(id, "name", name);
    }


    @PutMapping("/updateDescription/{id}/{description}")
    public ResponseEntity<String> updateDescription(@PathVariable @Positive Long id,
                                                    @PathVariable String description) {
        return update(id, "description", description);
    }

    @PutMapping("/updatePrice/{id}/{price}")
    public ResponseEntity<String> updatePrice(@PathVariable @Positive Long id,
                                              @PathVariable Double price) {
        return update(id, "price", price);
    }


        @GetMapping("/all")
        public ResponseEntity<List<Product>> findAllObjects() {
            return findAll();
        }


    @GetMapping("/all/byBusiness")
    public ResponseEntity<List<ProductViewDTO>> getProductByBusiness(@RequestBody Map<String,String> request) {
        String businessName = request.get("businessName");
        try{
            return ResponseEntity.ok(service.getProductByBusinessName(businessName));
        }catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}