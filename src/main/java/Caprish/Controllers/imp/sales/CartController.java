package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.sales.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Validated
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

    public CartController(CartService service) {
        super(service);
    }


    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Cart entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Cart> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/updateCartType/{id}/{type}")
    public ResponseEntity<String> updateCartType(@PathVariable @Positive Long id,
                                                 @PathVariable String type) {
        return update(id, "cart_type", type);
    }

    @PutMapping("/updateClientId/{id}/{clientId}")
    public ResponseEntity<String> updateClientId(@PathVariable @Positive Long id,
                                                 @PathVariable @Positive Long clientId) {
        return update(id, "client_id", clientId);
    }

    @PutMapping("/updateCartStatus/{id}/{status}")
    public ResponseEntity<String> updateCartStatus(@PathVariable @Positive Long id,
                                                   @PathVariable String status) {
        return update(id, "cart_status", status);
    }

    @PutMapping("/updateStaffId/{id}/{staffId}")
    public ResponseEntity<String> updateStaffId(@PathVariable @Positive Long id,
                                                @PathVariable @Positive Long staffId) {
        return update(id, "staff_id", staffId);
    }

    @PutMapping("/updateSaleDate/{id}/{date}")
    public ResponseEntity<String> updateSaleDate(@PathVariable @Positive Long id,
                                                 @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return update(id, "sale_date", date);
    }

    @GetMapping("/all")
    @Override
    public List<Cart> findAllObjects() {
        return findAll();
    }

    @GetMapping("/view/my-sales")
    public ResponseEntity<List<CartViewDTO>> getMySales(@RequestParam Long idBusiness) {
        return ResponseEntity.ok(service.getCartViewsByBusiness(idBusiness));
    }

}

