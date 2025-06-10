package Caprish.Service.imp.users;

import Caprish.Model.BeanUtils;
import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StaffService extends MyObjectGenericService<Staff, StaffRepository, StaffService> {


    public StaffService(StaffRepository repo) {super(repo);}

    @Override
    protected void verifySpecificAttributes(Staff entity) {

    }

}