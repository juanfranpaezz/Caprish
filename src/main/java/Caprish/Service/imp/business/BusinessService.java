package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

@Service
public class BusinessService extends MyObjectGenericService<Business, BusinessRepository> {

    protected BusinessService(BusinessRepository childRepository) {
        super(childRepository);
    }


    public void changeBussiness_name(Long id, String bussiness_name) {
        ((BusinessService) AopContext.currentProxy()).updateField(id, "bussiness_name", bussiness_name);
    }
    public void changeDescription(Long id, String description) {
        ((BusinessService) AopContext.currentProxy()).updateField(id, "description", description    );
    }
    public void changeSlogan(Long id, String slogan) {
        ((BusinessService) AopContext.currentProxy()).updateField(id, "slogan", slogan);
    }
    public void changeTax(Long id, String tax) {
        ((BusinessService) AopContext.currentProxy()).updateField(id, "tax", tax);
    }

}