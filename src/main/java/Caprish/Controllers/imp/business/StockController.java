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
@PreAuthorize("hasRole('SUPERVISOR')")
@RequestMapping("/stock")
@Validated
public class StockController extends MyObjectGenericController<Stock, StockRepository, StockService> {

    public StockController(StockService service) {
        super(service);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@Valid @RequestBody Stock entity) {
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

        @PreAuthorize("hasRole('SUPERVISOR')")
        @GetMapping("/{id}")
        @Override
        public ResponseEntity<Stock> findObjectById(@Positive @PathVariable Long id) {
            return findById(id);
        }

        @PreAuthorize("hasRole('SUPERVISOR')")
        @GetMapping("/all")
        @Override
        public List<Stock> findAllObjects() {
            return findAll();
        }

}