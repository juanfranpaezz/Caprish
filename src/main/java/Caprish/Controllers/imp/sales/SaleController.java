package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Sale;
import Caprish.Repository.interfaces.sales.SaleRepository;
import Caprish.Service.imp.sales.SaleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController extends MyObjectGenericController<Sale, SaleRepository, SaleService> {

    public SaleController(SaleService service) {
        super(service);
    }


}