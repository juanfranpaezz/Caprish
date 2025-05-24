package Caprish.Service.imp.business;

import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;


@Service
public class StockService extends MyObjectGenericService<Stock, StockRepository, StockService> {
    protected StockService(StockRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Stock entity) {

    }
}