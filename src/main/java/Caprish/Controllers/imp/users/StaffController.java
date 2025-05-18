package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.UserBasicGenericRepository;
import Caprish.Service.imp.users.UserBasicGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffController extends UserBasicGenericService<Staff> {

    @Autowired
    private UserBasicGenericRepository<Staff, Long> staffRepository;
    protected StaffController(UserBasicGenericRepository<Staff, Long> repository) {
        super(repository);
    }
}

