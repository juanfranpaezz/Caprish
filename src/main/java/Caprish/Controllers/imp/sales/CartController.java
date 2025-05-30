package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.sales.CartService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/cart")
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

    public CartController(CartService service) {
        super(service);
    }
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody Cart entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('EMPLOYEE')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
            return delete(id);
        }

        /*@PreAuthorize("hasRole('EMPLOYEE')")
        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
            return update(id);
        }*/

        @PreAuthorize("hasRole('EMPLOYEE')")
        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Cart> findObjectById(@Valid @PathVariable Long id) {
            return findById(id);
        }

        @PreAuthorize("hasRole('EMPLOYEE')")
        @GetMapping("/all")
        @Override
        public List<Cart> findAllObjects() {
            return findAll();
        }


}

