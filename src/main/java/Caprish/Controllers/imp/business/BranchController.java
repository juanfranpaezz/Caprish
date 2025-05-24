package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch")
public class BranchController extends MyObjectGenericController<Branch, BranchRepository, BranchService> {



    public BranchController(BranchService service) {
        super(service);
    }

    @Override
    @PostMapping
    public Branch create(@RequestBody Branch branch) {
        return service.save(branch);
    }

    @Override
    @PutMapping("/{BranchId}")
    public ResponseEntity<Branch> update(@PathVariable Long id, @RequestBody Branch branch) {
        if(!service.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        branch.setId(id);
        return ResponseEntity.ok(service.save(branch));
    }



}
