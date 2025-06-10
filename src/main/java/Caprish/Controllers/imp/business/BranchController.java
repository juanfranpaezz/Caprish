package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
import jakarta.validation.Valid;
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
         public ResponseEntity<String> createObject(@Valid @RequestBody Branch entity) {
             return create(entity);
         }

         @DeleteMapping("/delete/{id}")
         public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
             return delete(id);
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
         public ResponseEntity<Branch> findObjectById(@Positive @PathVariable Long id) {
             return findById(id);
         }

         @GetMapping("/all")
         public ResponseEntity<List<Branch>> findAllObjects() {
             return findAll();
         }


}