package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.UserGenericRepository;
import Caprish.Service.imp.users.StaffService;
import Caprish.Service.imp.users.UserGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff")
public class StaffController extends UserGenericController<Staff, StaffService> {

    public StaffController(Caprish.Service.imp.users.StaffService service) {
        super(service);
    }

}
