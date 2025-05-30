package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.business.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('SUPERVISOR')")
@RequestMapping("/stock")
public class StockController extends MyObjectGenericController<Stock, StockRepository, StockService> {

    public StockController(StockService service) {
        super(service);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody Stock entity) {
            return create(entity);
        }

        @PreAuthorize("hasRole('SUPERVISOR')")
        @DeleteMapping("/delete/{id}")
        @Override
        public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
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
        public ResponseEntity<Stock> findObjectById(@Valid @PathVariable Long id) {
            return findById(id);
        }

        @PreAuthorize("hasRole('SUPERVISOR')")
        @GetMapping("/all")
        @Override
        public List<Stock> findAllObjects() {
            return findAll();
        }

}