package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Repository.interfaces.users.StaffRepository;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService extends UserGenericService<Staff, StaffRepository, StaffService> {


    public StaffService(StaffRepository repo) {super(repo);}

    public void promoteStaff(Long id) {
        Staff staff = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Staff con ID " + id + " no encontrado"));

        if ("Supervisor".equalsIgnoreCase(staff.getWork_role().toString())) {
            throw new IllegalStateException("El staff ya es Supervisor");
        }

        ((StaffService) AopContext.currentProxy()).updateField(id, "work_role", "Supervisor");
    }


}