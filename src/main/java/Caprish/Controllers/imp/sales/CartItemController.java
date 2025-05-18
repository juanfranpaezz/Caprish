package Caprish.Controllers.imp.sales;


import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.CartItem;
import Caprish.Repository.interfaces.sales.CartItemRepository;
import Caprish.Service.imp.sales.CartItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart_item")
public class CartItemController extends MyObjectGenericController<CartItem, CartItemRepository, CartItemService> {

    public CartItemController(CartItemService service) {
        super(service);
    }

}