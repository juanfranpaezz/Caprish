package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.EntityNotFoundCustomException;
import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.Item;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Model.imp.sales.dto.ClientPurchaseDTO;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.business.ProductService;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
@Tag(name = "Carritos", description = "Gestión de carritos de compra y ventas asociadas")
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

    @Autowired
    private StaffService staffService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BusinessService businessService;


    public CartController(CartService service) {
        super(service);

    }

    @Operation(
            summary = "Crear un nuevo carrito",
            description = "Crea un nuevo carrito con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/create")
    public ResponseEntity<String> createObject(@RequestBody Map<String,String> payload,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Cart entity = new Cart();
        Cart cart = new Cart();
        entity.setStaff(staffService.findByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername())).orElse(null));
        entity.setCart_type(new CartType("SALE"));
        entity.setCart_status(new CartStatus("OPEN"));
        entity.setClient(clientService.getByCredentialId(
                credentialService.getIdByUsername(payload.get("client"))));
        entity.setItems(null);
        entity.setSale_date(LocalDate.now());

        return create(entity);
    }

    @Operation(
            summary = "Eliminar carrito",
            description = "Elimina un carrito según su ID"
    )
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
        return ResponseEntity.ok("Carrito eliminada");
    }
    @Operation(
            summary = "Ver mis ventas",
            description = "Obtiene las ventas relacionadas con una empresa"
    )
    @ApiResponse(responseCode = "200", description = "Ventas devueltas correctamente")
    @GetMapping("/staff/view/my-sales")
    public ResponseEntity<?> getMySales(@AuthenticationPrincipal UserDetails userDetails) {
        List<CartViewDTO>carts= service.getCartsByBusinessAndStatus(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "CONFIRMED");
        if(carts.isEmpty()) return ResponseEntity.badRequest().body("No se han encontrado carritos");
        return ResponseEntity.ok(carts);
    }
    @Operation(
            summary = "Ver mis ventas",
            description = "Obtiene las ventas relacionadas con una empresa"
    )
    @ApiResponse(responseCode = "200", description = "Ventas devueltas correctamente")
    @GetMapping("/staff/view/my-carts")
    public ResponseEntity<?> getMyCarts(@AuthenticationPrincipal UserDetails userDetails) {
        List<CartViewDTO> carts= service.getCartsByBusinessAndStatus(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())), "OPEN");
            if(carts.isEmpty()){
                return ResponseEntity.badRequest().body("No hay carritos");
            }
        return ResponseEntity.ok(carts);
    }
    @Operation(summary = "Confirmar una venta", description = "Confirma una venta y cambia el estado del carrito")
    @PutMapping("/staff/confirm-sale/{cartId}")
    public ResponseEntity<String> confirmSale(@PathVariable @Positive Long cartId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        if (!businessService.isActiveById(businessId))
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
        cart.getItems().forEach(item -> {
            Long prodId = item.getProduct().getId();
            int newStock = item.getProduct().getStock() - item.getQuantity();
            productService.changeField(prodId, "stock", newStock);
        });
        return ResponseEntity.ok("Venta confirmada");
    }
    @Operation(summary = "Ver mis compras", description = "Muestra las ventas realizadas del cliente")
    @GetMapping("/client/view/my-purchases")
    public ResponseEntity<?> getMyPurchases(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        Long credentialId = credentialService.getIdByUsername(username);
        Client client = clientService.findByCredentialId(credentialId);

        if (client == null) {
            return ResponseEntity.badRequest().body("No hay ventas");
        }
        List<ClientPurchaseDTO> carts = service.getFinalizedCartsByClientUsername(client.getId(), userDetails.getUsername(), "CONFIRMED");
        if(carts.isEmpty()) return ResponseEntity.badRequest().body("No hay carritos");
        return ResponseEntity.ok(carts);
    }
    @Operation(summary = "Ver mi carrito", description = "Muestra los carritos abiertos relacionados al cliente")
    @GetMapping("/client/view/my-cart")
    public ResponseEntity<?> viewMyCart(@AuthenticationPrincipal UserDetails userDetails) {
        Client client = clientService.findByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        if(client == null) return ResponseEntity.notFound().build();
        List<ClientPurchaseDTO> carts = service.getFinalizedCartsByClientUsername(client.getId(), userDetails.getUsername(), "OPEN");
        if (carts.isEmpty()) {
            return ResponseEntity.badRequest().body("No hay un carrito abierto");
        }
        return ResponseEntity.ok(carts);
    }
    @Operation(summary = "Confirmar venta", description = "Confirma una venta por parte del cliente")
    @PutMapping("/client/confirm-purchase")
    public ResponseEntity<String> confirmPurchase(@RequestBody Map<String,String> payload,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId     = clientService.getIdByCredentialId(credentialId);
        Long cartId       = Long.valueOf(payload.get("cartId"));
        Cart cart = service.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrado"));
        if (!cart.getClient().getId().equals(clientId)
                || !"PURCHASE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No puedes confirmar esta compra.");
        }
        for (Item item : cart.getItems()) {
            Long bizId = item.getProduct().getBusiness().getId();
            if (!businessService.isActiveById(bizId)) {
                return ResponseEntity
                        .badRequest()
                        .body("No se puede realizar la compra: la empresa \""
                                + item.getProduct().getBusiness().getBusinessName()
                                + "\" está dada de baja");
            }
        }
        for (Item item : cart.getItems()) {
            Long   prodId   = item.getProduct().getId();
            int    newStock = item.getProduct().getStock() - item.getQuantity();
            productService.changeField(prodId, "stock", newStock);
        }
        service.confirmPurchase(cartId);
        return ResponseEntity.ok("Compra confirmada");
    }
}
