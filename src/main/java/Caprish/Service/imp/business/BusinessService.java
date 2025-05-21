package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository, BusinessService> {
    protected BusinessService(BusinessRepository childRepository) {
        super(childRepository);
    }


}
