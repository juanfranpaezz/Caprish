package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.CartItem;
import Caprish.Repository.interfaces.sales.CartItemRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class CartItemService extends MyObjectGenericService<CartItem> {
    protected CartItemService(CartItemRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<CartItem> getEntityClass() {
        return CartItem.class;
    }

    


}
