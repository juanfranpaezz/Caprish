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
    public Optional<Cart> findByClientIdAndTypeAndStatus(Long clientId, String typeId, String statusId) {
        return repository.findByClientIdAndTypeAndStatus(clientId, typeId, statusId);
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
    protected void verifySpecificAttributes(Cart entity) {}

    public boolean existsByBusinessIdAndClientId(Long businessId, Long clientId){
        Long founded=repository.existsByBusinessIdAndClientId(businessId, clientId);
        return founded==1;
    }

    public List<Client> findClientsByBusinessId(Long businessId){
        return repository.findClientsByBusinessId(businessId);
    }
}
