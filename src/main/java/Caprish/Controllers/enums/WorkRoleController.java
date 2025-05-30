package Caprish.Controllers.enums;

import Caprish.Model.enums.WorkRole;
import Caprish.Model.imp.users.Staff;
import Caprish.Repository.enums.WorkRoleRepository;
import Caprish.Service.enums.WorkRoleService;
import jakarta.persistence.OneToMany;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/work_role")
public class WorkRoleController extends MyEnumGenericController<WorkRole, WorkRoleRepository, WorkRoleService> {
    public WorkRoleController(WorkRoleService service) {
        super(service);
    }

    @OneToMany(mappedBy = "workRole")
    private List<Staff> staff = new ArrayList<>();
}
