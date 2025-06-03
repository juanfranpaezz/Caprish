package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch")
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