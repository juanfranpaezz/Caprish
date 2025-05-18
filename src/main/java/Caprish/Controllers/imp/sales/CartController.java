package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.sales.CartService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

    public CartController(CartService service) {
        super(service);
    }

}

