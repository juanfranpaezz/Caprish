package Caprish.Controllers.imp.users;

import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.UserBasicGenericRepository;
import Caprish.Service.imp.users.UserBasicGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController extends UserBasicGenericService<Client> {

    @Autowired
    private UserBasicGenericRepository<Client, Long> staffRepository;
    protected ClientController(UserBasicGenericRepository<Client, Long> repository) {
        super(repository);
    }
}
