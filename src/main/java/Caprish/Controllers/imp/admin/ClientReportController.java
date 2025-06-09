package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.admin.ClientReport;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.admin.ClientReportRepository;
import Caprish.Service.imp.admin.ClientReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client_report")
@Validated
public class ClientReportController extends MyObjectGenericController<ClientReport, ClientReportRepository, ClientReportService> {

    public ClientReportController(ClientReportService service) {
        super(service);
    }

        @PostMapping("/create")
            public ResponseEntity<String> createObject(@Valid @RequestBody ClientReport entity) {
                return create(entity);
            }

            @DeleteMapping("/delete/{id}")
            public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
                return delete(id);
            }


            @GetMapping("/{id}")
            public ResponseEntity<ClientReport> findObjectById(@Positive @PathVariable Long id) {
                return findById(id);
            }

}