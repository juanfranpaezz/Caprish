package Caprish.Service.enums;

import Caprish.Model.enums.CartType;
import Caprish.Model.enums.Role;
import Caprish.Repository.enums.CartTypeRepository;
//import Caprish.Repository.enums.RoleRepository;
import Caprish.Repository.enums.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleService extends MyEnumGenericService<Role, RoleRepository, RoleService> {
    public RoleService(RoleRepository childRepository) {
        super(childRepository);
    }

}