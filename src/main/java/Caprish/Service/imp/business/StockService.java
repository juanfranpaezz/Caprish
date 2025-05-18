package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class StockService extends MyObjectGenericService<Stock, StockRepository> {
    protected StockService(StockRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Stock> getEntityClass() {
        return Stock.class;
    }


    public int changeQuantity(Long id, int quantity) {
        return updateField(id, "quantity", quantity);
    }
}