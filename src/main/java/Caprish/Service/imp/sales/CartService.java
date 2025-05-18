package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class CartService extends MyObjectGenericService<Cart, CartRepository> {
    protected CartService(CartRepository childRepository) {
        super(childRepository);
    }
}
