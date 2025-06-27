package Caprish.Service.imp.sales;


import Caprish.Model.enums.CartStatus;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartItemViewDTO;
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
                .map(obj -> {
                    Long idCart = ((Number) obj[0]).longValue();

                    List<CartItemViewDTO> items = repository.findItemsByCartId(idCart)
                            .stream()
                            .map(item -> new CartItemViewDTO(
                                    (String) item[0],
                                    ((Number) item[1]).intValue()))
                            .collect(Collectors.toList());

                    return new CartViewDTO(
                            idCart,
                            (String) obj[1],
                            (String) obj[2],
                            (String) obj[3],
                            (String) obj[4],
                            ((Number) obj[5]).longValue(),
                            items
                    );
                })
                .collect(Collectors.toList());
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException("Error getting cart views", e);
    }
}

    public List<CartViewDTO> getCartViewsByBusiness(Long businessId, String cartStatus) {
        try {
            return repository.getCartViewsByBusinessId(businessId, cartStatus)
                    .stream()
                    .map(obj -> {
                        Long idCart = ((Number) obj[0]).longValue();

                        List<CartItemViewDTO> items = repository.findItemsByCartId(idCart)
                                .stream()
                                .map(item -> new CartItemViewDTO(
                                        (String) item[0],
                                        ((Number) item[1]).intValue()
                                ))
                                .collect(Collectors.toList());

                        return new CartViewDTO(
                                idCart,
                                (String) obj[1],
                                (String) obj[2],
                                (String) obj[3],
                                (String) obj[4],
                                ((Number) obj[5]).longValue(),
                                items
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting cart views", e);
        }
    }


    public Optional<Cart> findByClientIdAndTypeAndStatus(Long clientId, String typeId, String statusId) {
        return repository.findByClientIdAndTypeAndStatus(clientId, typeId, statusId);
    }

    public List<ClientPurchaseDTO> getFinalizedCartsByClientUsername(Long clientId, String username, String cartStatus) {
        return repository.findFinalizedCartsByClient(clientId, username, cartStatus)
                .stream()
                .map(row -> {
                    Long cartId = ((Number) row[0]).longValue();

                    List<CartItemViewDTO> items = repository.findItemsByCartId(cartId)
                            .stream()
                            .map(item -> new CartItemViewDTO(
                                    (String) item[0],
                                    ((Number) item[1]).intValue()
                            ))
                            .collect(Collectors.toList());

                    return new ClientPurchaseDTO(
                            cartId,
                            (String) row[1],
                            (String) row[2],
                            (String) row[3],
                            (String) row[4],
                            (String) row[5],
                            ((Number) row[6]).doubleValue(),
                            items
                    );
                })
                .collect(Collectors.toList());
    }


    public void confirmPurchase(Long cartId){
        repository.confirmPurchase(cartId, new CartStatus("CONFIRMED"));
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
