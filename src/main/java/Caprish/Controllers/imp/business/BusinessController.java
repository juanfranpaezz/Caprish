package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.business.BusinessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    public BusinessController(BusinessService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody Business entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(Long id) {
        return delete(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(Long id) {
        return update(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Business> findObjectById(Long id) {
        return findById(id);
    }

    @GetMapping("/all")
    @Override
    public List<Business> findAllObjects() {
        return findAll();
    }

}

