package Caprish.Service.enums;

import Caprish.Model.enums.WorkRole;
import Caprish.Repository.enums.WorkRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class WorkRoleService extends MyEnumGenericService<WorkRole, WorkRoleRepository, WorkRoleService> {
    protected WorkRoleService(WorkRoleRepository childRepository) {
        super(childRepository);
    }
}
