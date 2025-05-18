package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectController;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.business.StockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController extends MyObjectController<Stock, StockRepository, StockService> {

    public StockController(StockService service) {
        super(service);
    }

}