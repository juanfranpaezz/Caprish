package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.ProductService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@PermitAll
@RestController
@RequestMapping("/product")
@Validated
public class ProductController extends MyObjectGenericController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }

    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@Valid @RequestBody Product entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
            return delete(id);
        }


    @PermitAll
    @GetMapping("/{id}")
        @Override
        public ResponseEntity<Product> findObjectById(Long id) {
            return findById(id);
        }

    @PutMapping("/updateBusinessId/{id}/{businessId}")
    public ResponseEntity<String> updateBusinessId(@PathVariable @Positive Long id,
                                                   @PathVariable @Positive Long businessId) {
        return update(id, "business_id", businessId);
    }

    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<String> updateName(@PathVariable @Positive Long id,
                                             @PathVariable String name) {
        return update(id, "name", name);
    }

    @PutMapping("/updateBarCode/{id}/{barCode}")
    public ResponseEntity<String> updateBarCode(@PathVariable @Positive Long id,
                                                @PathVariable Double barCode) {
        return update(id, "bar_code", barCode);
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


    @PermitAll
        @GetMapping("/all")
        @Override
        public List<Product> findAllObjects() {
            return findAll();
        }

}