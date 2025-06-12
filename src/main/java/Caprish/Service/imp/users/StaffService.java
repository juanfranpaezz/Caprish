package Caprish.Service.imp.users;

import Caprish.Model.BeanUtils;
import Caprish.Model.imp.users.Staff;
import Caprish.Model.enums.WorkRole;
import Caprish.Model.imp.users.dto.StaffViewDTO;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StaffService extends MyObjectGenericService<Staff, StaffRepository, StaffService> {


    public StaffService(StaffRepository repo) {super(repo);}

    @Override
    protected void verifySpecificAttributes(Staff entity) {

    }

    public List<StaffViewDTO> getStaffByBusiness(Long businessId) {
        return repository.findStaffByBusiness(businessId)
                .stream()
                .map(obj -> new StaffViewDTO(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        (String) obj[2],
                        (String) obj[3],
                        ((Number) obj[4]).longValue(),
                        (String) obj[5]
                ))
                .collect(Collectors.toList());
    }

}