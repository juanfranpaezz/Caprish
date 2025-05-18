package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService
        extends UserBasicGenericService<Staff> {

    @Autowired
    StaffRepository staffRepository;
    public StaffService(StaffRepository repository) {
            super(repository);
    }


    public void promoteStaff(Long staffId) {
        Staff s = repository.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff no encontrado: " + staffId));
        s.setWork_role(new WorkRole("Senior"));
        repository.save(s);
    }

}