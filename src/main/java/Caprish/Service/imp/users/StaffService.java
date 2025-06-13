package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Model.imp.users.dto.StaffViewDTO;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

@Slf4j
@Service
public class StaffService extends MyObjectGenericService<Staff, StaffRepository, StaffService> {


    public StaffService(StaffRepository repo) {super(repo);}

    @Override
    protected void verifySpecificAttributes(Staff entity) {
    }

    public Optional<Staff> findByCredentialId(Long credentialId) {
        return Optional.ofNullable(repository.findByCredentialId(credentialId));
    }

    public Long getBusinessIdByCredentialId(Long id){
        return repository.getBusinessIdByCredentialId(id);
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