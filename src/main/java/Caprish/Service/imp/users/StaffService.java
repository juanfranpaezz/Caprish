package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService extends UserGenericService<Staff, StaffRepository, StaffService> {


    public StaffService(StaffRepository repo) {
        super(repo);
    }

}