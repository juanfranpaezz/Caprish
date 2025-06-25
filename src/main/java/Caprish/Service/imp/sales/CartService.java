package Caprish.Service.imp.sales;


import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Model.imp.sales.dto.ClientPurchaseDTO;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService extends MyObjectGenericService<Cart, CartRepository, CartService> {
    protected CartService(CartRepository childRepository) {
        super(childRepository);
    }

    public List<CartViewDTO> getCartsByBusinessAndStatus(Long businessId, String cartStatus) {
        try {
            return repository.getCartViewsByBusinessId(businessId, cartStatus)
                    .stream()
                    .map(obj -> new CartViewDTO(
                            ((Number) obj[0]).longValue(),             // idCart
                            (String) obj[1],                           // clientName (first + last)
                            (String) obj[2],                           // cartType
                            (String) obj[3],                           // cartStatus
                            (String) obj[4],                           // staffName (first + last)
                            ((Number) obj[5]).longValue()
                            // totalAmount
                    ))
                    .collect(Collectors.toList());
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new RuntimeException("Error getting cart views", e);
        }
    }


    public Optional<Cart> findByClientIdAndTypeAndStatus(Long clientId, String typeId, String statusId) {
        return repository.findByClientIdAndTypeAndStatus(clientId, typeId, statusId);
    }

    public List<ClientPurchaseDTO> getFinalizedCartsByClientUsername(Long clientId, String username, String cartStatus) {
        return  repository.findFinalizedCartsByClient(clientId, username, cartStatus)
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
    protected void verifySpecificAttributes(Cart entity) {}

    public boolean existsByBusinessIdAndClientIdService(Long businessId, Long clientId){
        Integer found = repository.existsByBusinessIdAndClientId(businessId, clientId);
        return found != null && found == 1;
    }

    public List<Client> findClientsByBusinessId(Long businessId){
        return repository.findClientsByBusinessId(businessId);
    }
}
