package Caprish.Service.imp.business;

import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository, BusinessService> {
    protected BusinessService(BusinessRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Business entity) {

    }
}
