package Caprish.Service.imp.users;

import Caprish.Model.imp.users.PlatformAdmin;
import Caprish.Repository.interfaces.users.PlatformAdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlatformAdminService extends UserGenericService<PlatformAdmin, PlatformAdminRepository, PlatformAdminService> {

    public PlatformAdminService(PlatformAdminRepository repo) {super(repo);}

}
