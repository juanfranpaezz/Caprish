package Caprish.Controllers.imp.sales;
import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.sales.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart_item")
@Validated
public class ItemController extends MyObjectGenericController<Item, ItemRepository, ItemService> {

    public ItemController(ItemService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Item entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Item> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/updateCartId/{id}/{cartId}")
    public ResponseEntity<String> updateCartId(@PathVariable @Positive Long id,
                                               @PathVariable @Positive Long cartId) {
        return update(id, "cart_id", cartId);
    }

    @PutMapping("/updateProductId/{id}/{productId}")
    public ResponseEntity<String> updateProductId(@PathVariable @Positive Long id,
                                                  @PathVariable @Positive Long productId) {
        return update(id, "product_id", productId);
    }

    @PutMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable @Positive Long id,
                                                 @PathVariable int quantity) {
        return update(id, "quantity", quantity);
    }

    @GetMapping("/all")
    @Override
    public List<Item> findAllObjects() {
        return findAll();
    }

}