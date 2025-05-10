package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Model.imp.users.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.interfaces.users.IStaffService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService
        extends UserBasicGenericService<Staff>
        implements IStaffService {

    private final StaffRepository staffRepo;

    public StaffService(StaffRepository repo) {
        super(repo);
        this.staffRepo = repo;
    }


    @Override
    public void promoteStaff(Long staffId) {
        Staff s = staffRepo.findById(staffId)
                .orElseThrow(() -> new IllegalArgumentException("Staff no encontrado: " + staffId));
        s.setWork_role(WorkRole.valueOf("SENIOR"));
        staffRepo.save(s);
    }
}
