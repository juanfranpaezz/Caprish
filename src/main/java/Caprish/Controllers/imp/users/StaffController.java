package Caprish.Controllers.imp.users;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.users.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class StaffController extends MyObjectGenericController<Staff, StaffRepository, StaffService> {

    public StaffController(StaffService service) {
        super(service);
    }

        @PostMapping("/create")
            @Override
            public ResponseEntity<String> createObject(@RequestBody Staff entity) {
                return create(entity);
            }

            @DeleteMapping("/delete/{id}")
            @Override
            public ResponseEntity<String> deleteObject(Long id) {
                return delete(id);
            }


            @GetMapping("/{id}")
            @Override
            public ResponseEntity<Staff> findObjectById(Long id) {
                return findById(id);
            }

            @GetMapping("/all")
            @Override
            public List<Staff> findAllObjects() {
                return findAll();
            }

}
