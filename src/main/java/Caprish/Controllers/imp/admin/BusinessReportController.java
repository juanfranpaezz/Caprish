package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.interfaces.admin.BusinessReportRepository;
import Caprish.Service.imp.admin.BusinessReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business_report")
@Validated
public class BusinessReportController extends MyObjectGenericController<BusinessReport, BusinessReportRepository, BusinessReportService> {

    public BusinessReportController(BusinessReportService service) {
        super(service);
    }

    @PostMapping("/create")
        public ResponseEntity<String> createObject(@Valid @RequestBody BusinessReport entity) {
            return create(entity);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
            return delete(id);
        }


        @GetMapping("/{id}")
        public ResponseEntity<BusinessReport> findObjectById(@Positive @PathVariable Long id) {
            return findById(id);
        }

}

