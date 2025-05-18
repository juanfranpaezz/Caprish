package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService extends UserGenericService<Staff> {

    @Autowired
    StaffRepository staffRepository;
    public StaffService(StaffRepository repo) {super(repo);}

    public void promoteStaff(Long staffId) {
        Staff s = userRepository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff no encontrado: " + staffId));
        s.setWork_role(new WorkRole("Supervisor"));
        userRepository.save(s);
    }

    @Override
    protected Class<Staff> getEntityClass() {
        return Staff.class;
    }

}