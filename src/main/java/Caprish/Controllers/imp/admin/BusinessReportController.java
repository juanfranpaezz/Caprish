package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.interfaces.admin.BusinessReportRepository;
import Caprish.Service.imp.admin.BusinessReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business_report")
public class BusinessReportController extends MyObjectGenericController<BusinessReport, BusinessReportRepository, BusinessReportService> {

    public BusinessReportController(BusinessReportService service) {
        super(service);
    }

}