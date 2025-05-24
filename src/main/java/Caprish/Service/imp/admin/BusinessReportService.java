package Caprish.Service.imp.admin;

import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.admin.BusinessReportRepository;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class BusinessReportService extends MyObjectGenericService<BusinessReport, BusinessReportRepository, BusinessReportService> {
    protected BusinessReportService(BusinessReportRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(BusinessReport entity) {

    }


}