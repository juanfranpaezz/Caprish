package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class StockService extends MyObjectGenericService<Stock> {
    protected StockService(StockRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Stock> getEntityClass() {
        return Stock.class;
    }


    public int changeQuantity(Long id, int email) {
        return updateField(id, "quantity", email);
    }
}