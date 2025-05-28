package Caprish.Service.enums;

import Caprish.Model.enums.WorkRole;
import Caprish.Repository.enums.WorkRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WorkRoleService extends MyEnumGenericService<WorkRole, WorkRoleRepository, WorkRoleService> {
    protected WorkRoleService(WorkRoleRepository childRepository) {
        super(childRepository);
    }
}
