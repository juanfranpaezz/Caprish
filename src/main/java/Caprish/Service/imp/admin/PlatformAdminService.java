package Caprish.Service.imp.admin;

import Caprish.Model.imp.admin.PlatformAdmin;
import Caprish.Repository.interfaces.admin.PlatformAdminRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class PlatformAdminService extends MyObjectGenericService<PlatformAdmin, PlatformAdminRepository, PlatformAdminService> {

    protected PlatformAdminService(PlatformAdminRepository childRepository) {
        super(childRepository);
    }

        @Override
            protected void verifySpecificAttributes(PlatformAdmin entity) {

            }
}