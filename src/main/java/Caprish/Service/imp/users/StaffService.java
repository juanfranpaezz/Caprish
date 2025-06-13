package Caprish.Service.imp.users;

import Caprish.Model.imp.users.Staff;
import Caprish.Repository.interfaces.users.StaffRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StaffService extends MyObjectGenericService<Staff, StaffRepository, StaffService> {


    public StaffService(StaffRepository repo) {super(repo);}

    @Override
    protected void verifySpecificAttributes(Staff entity) {
    }

    public Staff findByCredentialId(Long credentialId) {
        return repository.findAllByCredentialId(credentialId);
    }

    public Long getBusinessIdByCredentialId(Long id){
        return repository.getBusinessIdByCredentialId(id);
    }

}