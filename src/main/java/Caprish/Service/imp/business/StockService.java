package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;


@Service
public class StockService extends MyObjectGenericService<Stock, StockRepository> {
    protected StockService(StockRepository childRepository) {
        super(childRepository);
    }


    public void changeQuantity(Long id, int quantity) {
        ((StockService) AopContext.currentProxy()).updateField(id, "quantity", quantity);
    }
}