package Caprish.Controllers.enums;

import Caprish.Model.enums.WorkRole;
import Caprish.Repository.enums.WorkRoleRepository;
import Caprish.Service.enums.WorkRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/work_role")
public class WorkRoleController extends MyEnumGenericController<WorkRole, WorkRoleRepository, WorkRoleService> {
    public WorkRoleController(WorkRoleService service) {
        super(service);
    }
}
