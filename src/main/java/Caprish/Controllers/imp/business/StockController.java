package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.business.StockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
@Validated
public class StockController extends MyObjectGenericController<Stock, StockRepository, StockService> {

    public StockController(StockService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@Valid @RequestBody Stock entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Stock> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/updateProductId/{id}/{productId}")
    public ResponseEntity<String> updateProductId(@PathVariable @Positive Long id,
                                                  @PathVariable @Positive Long productId) {
        return update(id, "product_id", productId);
    }

    @PutMapping("/updateBranchId/{id}/{branchId}")
    public ResponseEntity<String> updateBranchId(@PathVariable @Positive Long id,
                                                 @PathVariable @Positive Long branchId) {
        return update(id, "branch_id", branchId);
    }

    @PutMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable @Positive Long id,
                                                 @PathVariable int quantity) {
        return update(id, "quantity", quantity);
    }

    @GetMapping("/all")
    @Override
    public List<Stock> findAllObjects() {
        return findAll();
    }

}