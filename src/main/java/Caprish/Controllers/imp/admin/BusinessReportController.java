package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.interfaces.admin.BusinessReportRepository;
import Caprish.Service.imp.admin.BusinessReportService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
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
        public ResponseEntity<String> deleteObject(@Valid @PathVariable  Long id) {
            return delete(id);
        }




        @GetMapping("/{id}")
        @Override
        public ResponseEntity<BusinessReport> findObjectById(@Valid @PathVariable Long id) {
            return findById(id);
        }

        @GetMapping("/all")
        @Override
        public List<BusinessReport> findAllObjects() {
            return findAll();
        }

}

