package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Service.imp.users.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios/staff")
public class StaffController extends UserBasicGenericController<Staff> {

    private final StaffService staffService;

    public StaffController(StaffService service) {
        super(service);// para tener los metodos de servicio generico
        this.staffService = service; // para los metodos especificos de la clase
    }
    @PostMapping("/promote/{id}")
    public ResponseEntity<String> promoteStaff(@PathVariable("id") Long staffId) {
        try {
            staffService.promoteStaff(staffId);
            return ResponseEntity.ok("El staff fue promovido a Senior exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

