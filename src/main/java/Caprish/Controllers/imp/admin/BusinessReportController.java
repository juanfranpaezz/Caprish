package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.interfaces.admin.BusinessReportRepository;
import Caprish.Service.imp.admin.BusinessReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business_report")
public class BusinessReportController extends MyObjectGenericController<BusinessReport, BusinessReportRepository, BusinessReportService> {

    public BusinessReportController(BusinessReportService service) {
        super(service);
    }

    @PostMapping("/create")
        @Override
        public ResponseEntity<String> createObject(@RequestBody BusinessReport entity) {
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
        public ResponseEntity<BusinessReport> findObjectById(Long id) {
            return findById(id);
        }

        @GetMapping("/all")
        @Override
        public List<BusinessReport> findAllObjects() {
            return findAll();
        }

}

