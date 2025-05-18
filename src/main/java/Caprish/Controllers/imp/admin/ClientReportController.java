package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.ClientReport;
import Caprish.Repository.interfaces.admin.ClientReportRepository;
import Caprish.Service.imp.admin.ClientReportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client_report")
public class ClientReportController extends MyObjectGenericController<ClientReport, ClientReportRepository, ClientReportService> {

    public ClientReportController(ClientReportService service) {
        super(service);
    }

}