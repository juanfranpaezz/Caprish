package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
@Validated
public class BranchController extends MyObjectGenericController<Branch, BranchRepository, BranchService> {

    public BranchController(BranchService service) {
        super(service);
    }

     @PostMapping("/create")
         @Override
         public ResponseEntity<String> createObject(@RequestBody Branch entity) {
             return create(entity);
         }

         @DeleteMapping("/delete/{id}")
         @Override
         public ResponseEntity<String> deleteObject(Long id) {
             return delete(id);
         }

    @PutMapping("/updateBusinessId/{id}/{businessId}")
    public ResponseEntity<String> updateBusinessId(@PathVariable @Positive Long id,
                                                   @PathVariable @Positive Long businessId) {
        return update(id, "business_id", businessId);
    }

    @PutMapping("/updateAddress/{id}/{address}")
    public ResponseEntity<String> updateAddress(@PathVariable @Positive Long id,
                                                @PathVariable String address) {
        return update(id, "address", address);
    }

    @PutMapping("/updateBranchType/{id}/{type}")
    public ResponseEntity<String> updateBranchType(@PathVariable @Positive Long id,
                                                   @PathVariable String type) {
        return update(id, "branch_type", type);
    }


         @GetMapping("/{id}")
         @Override
         public ResponseEntity<Branch> findObjectById(Long id) {
             return findById(id);
         }

         @GetMapping("/all")
         @Override
         public List<Branch> findAllObjects() {
             return findAll();
         }


}