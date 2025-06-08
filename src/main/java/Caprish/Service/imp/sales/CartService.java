package Caprish.Service.imp.sales;


import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@Service
public class CartService extends MyObjectGenericService<Cart, CartRepository, CartService> {
    protected CartService(CartRepository childRepository) {
        super(childRepository);
    }

    public List<CartViewDTO> getCartViewsByBusiness(Long idBusiness) {
        return repository.getCartViewsByBusinessId(idBusiness)
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
    }

    @Override
    protected void verifySpecificAttributes(Cart cart) {

        if (cart.getCart_type() == null) {
            throw new IllegalArgumentException("El tipo de carrito no puede ser nulo.");
        }

        if (cart.getClient() == null) {
            throw new IllegalArgumentException("El cliente asociado al carrito no puede ser nulo.");
        }

        if (cart.getCart_status() == null) {
            throw new IllegalArgumentException("El estado del carrito no puede ser nulo.");
        }

        if (cart.getSale_date() == null) {
            throw new IllegalArgumentException("La fecha de venta no puede ser nula.");
        }
        if (cart.getSale_date().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de venta no puede ser futura.");
        }

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("El carrito debe contener al menos un item.");
        }
        String CartType= cart.getCart_type().getId();
        String statusCode= cart.getCart_status().getId();

        if ("SALE".equalsIgnoreCase(CartType) && "CONFIRMED".equalsIgnoreCase(statusCode)) {
            if (cart.getStaff() == null) {
                throw new IllegalArgumentException("Para carritos de venta confirmados, el vendedor debe estar asignado");
            }
        }
    }
}
