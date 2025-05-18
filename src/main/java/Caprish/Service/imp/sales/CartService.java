package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class CartService extends MyObjectGenericService<Cart> {
    protected CartService(CartRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Cart> getEntityClass() {
        return Cart.class;
    }
}
