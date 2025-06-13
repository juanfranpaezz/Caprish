package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.business.BusinessService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
@Validated
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    public BusinessController(BusinessService service) {
        super(service);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Business> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }
    @PutMapping("/updateBusinessName/{id}/{name}")
    public ResponseEntity<String> updateBusinessName(@PathVariable @Positive Long id,
                                                     @PathVariable String name) {
        return update(id, "bussiness_name", name);
    }

    @PutMapping("/updateDescription/{id}/{description}")
    public ResponseEntity<String> updateDescription(@PathVariable @Positive Long id,
                                                    @PathVariable String description) {
        return update(id, "description", description);
    }

    @PutMapping("/updateSlogan/{id}/{slogan}")
    public ResponseEntity<String> updateSlogan(@PathVariable @Positive Long id,
                                               @PathVariable String slogan) {
        return update(id, "slogan", slogan);
    }

    @PutMapping("/updateTax/{id}/{tax}")
    public ResponseEntity<String> updateTax(@PathVariable @Positive Long id,
                                            @PathVariable int tax) {
        return update(id, "tax", tax);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Business>> findAllObjects() {
        return findAll();
    }

}

