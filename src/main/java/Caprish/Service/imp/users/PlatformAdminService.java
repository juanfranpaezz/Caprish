package Caprish.Service.imp.users;

import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlatformAdminService extends MyObjectGenericService<PlatformAdmin, PlatformAdminRepository, PlatformAdminService> {

    public PlatformAdminService(PlatformAdminRepository repo) {super(repo);}

    @Override
    protected void verifySpecificAttributes(PlatformAdmin entity) {

    }

}
