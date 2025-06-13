package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.EntityNotFoundCustomException;
import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Model.imp.sales.dto.ClientPurchaseDTO;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.business.ProductService;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.sales.ItemService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

    @Autowired
    private StaffService staffService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BusinessService businessService;

    public CartController(CartService service) {
        super(service);

    }

    @PostMapping("/create")
    public ResponseEntity<String> createObject(@RequestBody Map<String,String> payload,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Cart entity = new Cart();
        entity.setStaff(staffService.findByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername())).orElse(null));
        entity.setCart_type(new CartType("SALE"));
        entity.setCart_status(new CartStatus("OPEN"));
        entity.setClient(clientService.getByCredentialId(
                credentialService.getIdByUsername(payload.get("client"))));
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSaleCart(@PathVariable @Positive Long id,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Cart cart = service.findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrado"));
        if (!"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("Solo se pueden eliminar ventas abiertas.");
        }
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        if (!cart.getStaff().getBusiness().getId().equals(businessId)) {
            return ResponseEntity.badRequest().body("No tienes permiso para eliminar esta venta.");
        }
        service.deleteById(id);
        return ResponseEntity.ok("Venta eliminada");
    }


    @GetMapping("/staff/view/my-sales")
    public ResponseEntity<List<CartViewDTO>> getMySales(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.getCartViewsByBusiness(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()))));
    }

    @PutMapping("/staff/confirm-sale/{cartId}")
    public ResponseEntity<String> confirmSale(@PathVariable @Positive Long cartId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        if(!businessService.isActiveById(businessId))
            return ResponseEntity.badRequest().body("No se puede realizar la venta ya que la empresa está dada de baja");
        Cart cart = service.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrado"));
        if (!cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No puedes confirmar esta venta.");
        }
        cart.setCart_status(new CartStatus("CONFIRMED"));
        cart.setSale_date(LocalDate.now());
        service.save(cart);
        cart.getItems().forEach(item ->
                update(item.getProduct().getId(),
                        "stock",
                        (item.getProduct().getStock() - item.getQuantity())));
        return ResponseEntity.ok("Venta confirmada");
    }

    @GetMapping("/view/my-sales/{businessId}")
    public ResponseEntity<List<CartViewDTO>> getMySales(@PathVariable Long businessId) {
        return ResponseEntity.ok(service.getCartViewsByBusiness(businessId));
    }

    @GetMapping("/view/my-purchases")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ClientPurchaseDTO>> getMyPurchases(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername(); // viene del JWT

        // Obtener el ID del Credential
        Long credentialId = credentialService.getIdByUsername(username);

        // Obtener el Client a partir del ID de Credential
        Client client = clientService.findByCredentialId(credentialId);

        if (client == null) {
            return ResponseEntity.notFound().build();
        }

        // Buscar las compras finalizadas del cliente
        return ResponseEntity.ok(service.getFinalizedCartsByClientUsername(client.getId(), userDetails.getUsername()));
    }

    //    @GetMapping("/view/my-sales/{businessId}")
//    public ResponseEntity<Long> getMySales(@PathVariable Long businessId) {
//        return ResponseEntity.ok(businessId);
//    }
    @PutMapping("/client/confirm-purchase")
    public ResponseEntity<String> confirmPurchase(@RequestBody Map<String,String> payload,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Long clientId = clientService.getIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long cartId = Long.valueOf(payload.get("cartId"));
        Cart cart = service.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrado"));
        if (!cart.getClient().getId().equals(clientId)
                || !"PURCHASE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No puedes confirmar esta compra.");
        }
        if(!businessService.isActiveById(cart.getStaff().getBusiness().getId()))
            return ResponseEntity.badRequest().body("No se puede realizar la venta ya que la empresa está dada de baja");

        cart.setCart_status(new CartStatus("CONFIRMED"));
        cart.setSale_date(LocalDate.now());
        service.save(cart);
        cart.getItems().forEach(item ->
                update(item.getProduct().getId(),
                        "stock",
                        (item.getProduct().getStock() - item.getQuantity())));
                Cart newCart = new Cart();
        newCart.setCart_type(new CartType("PURCHASE"));
        newCart.setCart_status(new CartStatus("OPEN"));
        newCart.setClient(clientService.findById(clientId).get());
        return ResponseEntity.ok("Compra confirmada");
    }
}

