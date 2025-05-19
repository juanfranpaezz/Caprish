package Caprish.Controllers.imp.sales;


import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.sales.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart_item")
public class ItemController extends MyObjectGenericController<Item, ItemRepository, ItemService> {

    public ItemController(ItemService service) {
        super(service);
    }

}