package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.EntityNotFoundCustomException;
import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.imp.business.Product;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.business.ProductService;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.sales.ItemService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController extends MyObjectGenericController<Item, ItemRepository, ItemService> {

    @Autowired
    private CartService cartService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private ProductService productService;

    public ItemController(ItemService service) {
        super(service);
    }

    @PostMapping("/staff/add-from-sale")
    public ResponseEntity<?> addItemFromSale(@RequestBody Map<String, String> payload,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService
                .getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long productId = Long.valueOf(payload.get("productId"));
        Product product = productService.findById(productId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Producto no encontrdo"));
        if (!product.getBusiness().getId().equals(businessId)) {
            return ResponseEntity.badRequest().body("El producto no pertenece a tu negocio.");
        }
        Long cartId = Long.valueOf(payload.get("cartId"));
        Cart cart = cartService.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrdo"));
        if (!cart.getCart_type().getId().equals("SALE") || !cart.getCart_status().getId().equals("OPEN")) {
            return ResponseEntity.badRequest().body("El carrito no está abierto o no es de tipo venta.");
        }
        if (cart.getClient() == null) {
            return ResponseEntity.badRequest().body("El carrito no tiene un cliente asignado.");
        }
        Item item = new Item();
        int quantity = payload.get("quantity") == null ? 1 : Integer.parseInt(payload.get("quantity"));
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setCart(cart);
        // Guardar y devolver respuesta
        Item saved = service.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PostMapping("/client/add-from-purchase")
    public ResponseEntity<?> addItemFromPurchase(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload) {
        // 1) Identificar cliente
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId = clientService.getIdByCredentialId(credentialId);

        // 2) Cargar producto
        Long productId = Long.valueOf(payload.get("productId"));
        Product product = productService.findById(productId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Producto no encontrado"));

        // 3) Obtener o crear carrito de compra abierto
        String cartIdStr = payload.get("cartId");
        Cart cart;
        if (cartIdStr != null && cartService.existsById(Long.valueOf(cartIdStr))) {
            cart = cartService.findById(Long.valueOf(cartIdStr))
                    .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrado"));
        } else {
            cart = new Cart();
            cart.setCart_type(new CartType("PURCHASE"));
            cart.setCart_status(new CartStatus("OPEN"));
            cart.setClient(clientService.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundCustomException("Cliente no encontrado")));
            cart = cartService.save(cart);
        }

        // 4) Verificar estado y tipo
        if (!"PURCHASE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("El carrito no está abierto o no es de tipo compra.");
        }

        // 5) Verificar empresa consistente
        if (!cart.getItems().isEmpty()) {
            Long existingBiz = cart.getItems().get(0)
                    .getProduct().getBusiness().getId();
            Long newBiz = product.getBusiness().getId();
            if (!existingBiz.equals(newBiz)) {
                return ResponseEntity.badRequest()
                        .body("El carrito ya contiene ítems de otra empresa. Vacíalo antes de agregar este producto.");
            }
        }

        // 6) Crear y guardar ítem
        int quantity = payload.get("quantity") == null ? 1 : Integer.parseInt(payload.get("quantity"));
        Item item = new Item();
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setCart(cart);
        Item saved = service.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 2) Staff update quantity
    @PutMapping("/staff/update-quantity/{itemId}/{quantity}")
    public ResponseEntity<?> updateQuantityStaff(@PathVariable @Positive Long itemId,
                                                 @PathVariable @Positive int quantity,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        // Verificar negocio a través del staff del carrito
        if (!cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No tienes permiso para modificar este ítem.");
        }
        item.setQuantity(quantity);
        service.save(item);
        return ResponseEntity.ok("Cantidad actualizada");
    }

    // 3) Client update quantity
    @PutMapping("/client/update-quantity/{itemId}/{quantity}")
    public ResponseEntity<?> updateQuantityClient(@PathVariable @Positive Long itemId,
                                                  @PathVariable @Positive int quantity,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId = clientService.getIdByCredentialId(credentialId);
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        if (!cart.getClient().getId().equals(clientId)
                || !"PURCHASE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No puedes modificar este ítem.");
        }
        item.setQuantity(quantity);
        service.save(item);
        return ResponseEntity.ok("Cantidad actualizada");
    }

    // 4) Staff delete item
    @DeleteMapping("/staff/delete/{itemId}")
    public ResponseEntity<?> deleteItemStaff(@PathVariable @Positive Long itemId,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        // Verificar negocio a través del staff del carrito
        if (!cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No tienes permiso para eliminar este ítem.");
        }
        service.deleteById(itemId);
        return ResponseEntity.ok("Ítem eliminado");
    }

    // 5) Client delete item
    @DeleteMapping("/client/delete/{itemId}")
    public ResponseEntity<?> deleteItemClient(@PathVariable @Positive Long itemId,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId = clientService.getIdByCredentialId(credentialId);

        staffService.findByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));

        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        if (!cart.getClient().getId().equals(clientId)
                || !"PURCHASE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No puedes eliminar este ítem.");
        }
        service.deleteById(itemId);
        return ResponseEntity.ok("Ítem eliminado");
    }
}
