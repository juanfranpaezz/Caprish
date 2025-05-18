package Caprish.Service.imp.users;

import Caprish.Exception.UserException;
import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import org.springframework.stereotype.Service;

@Service
public class StaffService
        extends UserBasicGenericService<Staff> {
//    private final StaffRepository staffRepository; descomentar si se hace algun metodo especifico que requiera de este repositorio

    public StaffService(StaffRepository repository) {
        super(repository);
//        this.staffRepository = repository; lo mismo q lo de arriba
    }
    public void promoteStaff(Long staffId) throws UserException {
        Staff s = repository.findById(staffId)
                .orElseThrow(() -> new UserException("Staff no encontrado: " + staffId));
        s.setWork_role(new WorkRole("Senior"));
        repository.save(s);
    }
    public void demoteStaff(Long staffId) {
    }
}