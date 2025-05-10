package Caprish.Service.interfaces.users;

import Caprish.Model.imp.users.Staff;
import java.util.List;

public interface IStaffService extends IUserBasicGenericService<Staff> {
    void promoteStaff(Long staffId);
}
