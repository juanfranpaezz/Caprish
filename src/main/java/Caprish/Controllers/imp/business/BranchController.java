package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('SUPERVISOR')")
@RequestMapping("/branch")
@Validated
public class BranchController extends MyObjectGenericController<Branch, BranchRepository, BranchService> {

    public BranchController(BranchService service) {
        super(service);
    }

    @PreAuthorize("hasRole('BOSS')")
     @PostMapping("/create")
         @Override
         public ResponseEntity<String> createObject(@Valid @RequestBody Branch entity) {
             return create(entity);
         }

         @PreAuthorize("hasRole('BOSS')")
         @DeleteMapping("/delete/{id}")
         @Override
         public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
             return delete(id);
         }

         @PreAuthorize("hasRole('BOSS')")
         @PutMapping("/updateAddress/{id}/{address}")
                  public ResponseEntity<String> updateAddress(@PathVariable @Positive Long id, @PathVariable String address) {
                      return update(id, "address", address);
                  }


         @PreAuthorize("hasRole('SUPERVISOR')")
         @GetMapping("/{id}")
         @Override
         public ResponseEntity<Branch> findObjectById(@Positive @PathVariable Long id) {
             return findById(id);
         }

         @PreAuthorize("hasRole('SUPERVISOR')")
         @GetMapping("/all")
         @Override
         public List<Branch> findAllObjects() {
             return findAll();
         }


}