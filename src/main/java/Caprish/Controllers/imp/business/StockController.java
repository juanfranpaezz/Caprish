package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.business.StockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
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

        @GetMapping("/all")
        public ResponseEntity<List<Stock>> findAllObjects() {
            return findAll();
        }


    @PutMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable @Positive Long id,
                                                @PathVariable @Positive Integer quantity) {
        return update(id, "quantity", quantity);
    }

}