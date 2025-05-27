package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.sales.CartService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/cart")
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

    public CartController(CartService service) {
        super(service);
    }

    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody Cart entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(Long id) {
            return delete(id);
        }

        @PutMapping("/update/{id}")
        @Override
        public ResponseEntity<String> updateObject(Long id) {
            return update(id);
        }

        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Cart> findObjectById(Long id) {
            return findById(id);
        }

        @GetMapping("/all")
        @Override
        public List<Cart> findAllObjects() {
            return findAll();
        }


}

