package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.business.BusinessService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('BOSS')")
@RequestMapping("/business")
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    public BusinessController(BusinessService service) {
        super(service);
    }

    @PreAuthorize("hasRole('BOSS')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody Business entity) {
        return create(entity);
    }

    @PreAuthorize("hasRole('BOSS')")
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
        return delete(id);
    }

   /* @PreAuthorize("hasRole('BOSS')")
    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
        return update(id);
    }*/

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Business> findObjectById(@Valid @PathVariable Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Override
    public List<Business> findAllObjects() {
        return findAll();
    }

}

