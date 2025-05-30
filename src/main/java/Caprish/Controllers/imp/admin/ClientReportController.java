package Caprish.Controllers.imp.admin;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.admin.ClientReport;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.admin.ClientReportRepository;
import Caprish.Service.imp.admin.ClientReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client_report")
public class ClientReportController extends MyObjectGenericController<ClientReport, ClientReportRepository, ClientReportService> {

    public ClientReportController(ClientReportService service) {
        super(service);
    }

        @PostMapping("/create")
            @Override
            public ResponseEntity<String> createObject(@RequestBody ClientReport entity) {
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
            public ResponseEntity<ClientReport> findObjectById(Long id) {
                return findById(id);
            }

            @GetMapping("/all")
            @Override
            public List<ClientReport> findAllObjects() {
                return findAll();
            }

}