package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/product")
@Validated
public class ProductController extends MyObjectGenericController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@Valid @RequestBody Product entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('SUPERVISOR')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
            return delete(id);
        }

        /*@PreAuthorize("hasRole('SUPERVISOR')")
        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
            return update(id);
        }*/

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Product> findObjectById(@Positive @PathVariable Long id) {
            return findById(id);
        }

        @PreAuthorize("hasRole('USER')")
        @GetMapping("/all")
        @Override
        public List<Product> findAllObjects() {
            return findAll();
        }



}