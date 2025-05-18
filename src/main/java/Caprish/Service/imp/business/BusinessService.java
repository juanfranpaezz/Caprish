package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository> {

    protected BusinessService(BusinessRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Business> getEntityClass() {
        return Business.class;
    }


    public int changeBussiness_name(Long id, String bussiness_name) {
        return updateField(id, "bussiness_name", bussiness_name);
    }
    public int changeDescription(Long id, String description) {
        return updateField(id, "description", description);
    }
    public int changeSlogan(Long id, String slogan) {
        return updateField(id, "slogan", slogan);
    }
    public int changeTax(Long id, String tax) {
        return updateField(id, "tax", tax);
    }

}