package Caprish.Service.imp.sales;


import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService extends MyObjectGenericService<Cart, CartRepository, CartService> {
    protected CartService(CartRepository childRepository) {
        super(childRepository);
    }

    public List<CartViewDTO> getCartViewsByBusiness(Long idBusiness) {
        return repository.getCartViewsByBusinessId(idBusiness)
                .stream()
                .map(obj -> new CartViewDTO(
                        ((Number) obj[0]).longValue(),       // idCart
                        (String) obj[1],                     // clientName
                        (String) obj[2],                     // cartType
                        (String) obj[3],                     // cartStatus
                        ((java.sql.Date) obj[4]).toLocalDate(), // saleDate
                        (String) obj[5],                     // staffName
                        ((Number) obj[6]).longValue(),       // idBusiness
                        ((Number) obj[7]).doubleValue()      // totalAmount
                ))
                .collect(Collectors.toList());
    }


    @Override
    protected void verifySpecificAttributes(Cart entity) {

    }
}
