package Caprish.Controllers.enums;


import Caprish.Model.enums.CartType;
import Caprish.Model.enums.Role;
import Caprish.Repository.enums.CartTypeRepository;
import Caprish.Repository.enums.RoleRepository;
import Caprish.Service.enums.CartTypeService;
import Caprish.Service.enums.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart_type")
public class RoleController extends MyEnumGenericController<Role, RoleRepository, RoleService> {
    public RoleController(RoleService service) {
        super(service);
    }


}
