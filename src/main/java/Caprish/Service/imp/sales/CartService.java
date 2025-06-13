package Caprish.Service.imp.sales;


import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Model.imp.sales.dto.ClientPurchaseDTO;
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

    public List<CartViewDTO> getCartViewsByBusiness(Long businessId) {
        try {
            return repository.getCartViewsByBusinessId(businessId)
                    .stream()
                    .map(obj -> new CartViewDTO(
                            ((Number) obj[0]).longValue(),             // idCart
                            (String) obj[1],                           // clientName (first + last)
                            (String) obj[2],                           // cartType
                            (String) obj[3],                           // cartStatus
                            (String) obj[4],                           // staffName (first + last)
                            ((Number) obj[5]).longValue(),             // idBusiness
                            ((Number) obj[6]).doubleValue()            // totalAmount
                    ))
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error getting cart views", e);
        }
    }

    public List<ClientPurchaseDTO> getFinalizedCartsByClientUsername(Long clientId, String username) {
        return  repository.findFinalizedCartsByClient(clientId, username)
                .stream()
                .map(row -> new ClientPurchaseDTO(
                ((Number) row[0]).longValue(),
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (String) row[4],
                (String)row[5],
                ((Number) row[6]).doubleValue()
        )).collect(Collectors.toList());
    }

    @Override
    protected void verifySpecificAttributes(Cart entity) {

    }
}
