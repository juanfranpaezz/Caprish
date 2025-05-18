package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectController;
import Caprish.Model.imp.sales.SaleItem;
import Caprish.Repository.interfaces.sales.SaleItemRepository;
import Caprish.Service.imp.sales.SaleItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sale_item")
public class SaleItemController extends MyObjectController<SaleItem, SaleItemRepository, SaleItemService> {

    public SaleItemController(SaleItemService childService) {
        super(childService);
    }

}