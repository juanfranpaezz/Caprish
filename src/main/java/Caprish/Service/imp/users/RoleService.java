package Caprish.Service.imp.users;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.users.Role;
import Caprish.Repository.interfaces.users.RoleRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService extends MyObjectGenericService<Role, RoleRepository, RoleService> {
    protected RoleService(RoleRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Role entity) {
    }

}
