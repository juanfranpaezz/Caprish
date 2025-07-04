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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/item")
@Tag(name = "Ítems", description = "Gestión de ítems dentro del sistema (productos en un carrito, pedido, etc.)")
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
    @Operation(summary = "Agregar un item a un carrito", description = "Agrega un item a un carrito en especifico")
    @PostMapping("/staff/add-from-sale")
    public ResponseEntity<?> addItemFromSale(@RequestBody Map<String, String> payload,
                                             @AuthenticationPrincipal UserDetails userDetails) {

        Long businessId = staffService
                .getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long productId = productService.findIdByName(payload.get("productName"));
        Product product = productService.findById(productId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Producto no encontrado"));
        if (!product.getBusiness().getId().equals(businessId)) {
            return ResponseEntity.badRequest().body("El producto no pertenece a tu negocio.");
        }
        Long cartId = Long.valueOf(payload.get("cartId"));
        Cart cart = cartService.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Carrito no encontrado"));
        if (cart.getStaff() == null
                || !cart.getStaff().getBusiness().getId().equals(businessId)) {
            return ResponseEntity.badRequest().body("No tienes permiso para agregar ítems a ese carrito.");
        }
        if (!"SALE".equals(cart.getCart_type().getId()) || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("El carrito no está abierto o no es de tipo venta.");
        }
        if (cart.getClient() == null) {
            return ResponseEntity.badRequest().body("El carrito no tiene un cliente asignado.");
        }
        int quantityToAdd = payload.get("quantity") == null ? 1 : Integer.parseInt(payload.get("quantity"));
        if (product.getStock() < quantityToAdd) {
            return ResponseEntity.badRequest().body("No hay suficiente stock de este producto.");
        }
        Optional<Item> existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();
        Item item;
        if (existing.isPresent()) {
            item = existing.get();
            item.setQuantity(item.getQuantity() + quantityToAdd);
        } else {
            item = new Item();
            item.setProduct(product);
            item.setCart(cart);
            item.setQuantity(quantityToAdd);
        }
        Item saved = service.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @Operation(summary = "Agregar a compra", description = "Agrega un producto buscado por el cliente a un carrito abierto, de no ser asi, crea el carrito y agrega el producto")
    @PostMapping("/client/add-from-purchase")
    public ResponseEntity<?> addItemFromPurchase(@AuthenticationPrincipal UserDetails userDetails,
                                                 @RequestBody Map<String, String> payload) {

        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId = clientService.getIdByCredentialId(credentialId);

        String productName = payload.get("productName");
        if (productName == null || productName.isBlank()) {
            return ResponseEntity.badRequest().body("Falta el parámetro productName");
        }
        Product product;
        try {
            product = productService.findByName(productName);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
        }
        Cart cart = cartService.findByClientIdAndTypeAndStatus(clientId, "PURCHASE", "OPEN")
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCart_type(new CartType("PURCHASE"));
                    newCart.setCart_status(new CartStatus("OPEN"));
                    newCart.setClient(clientService.findById(clientId)
                            .orElseThrow(() -> new EntityNotFoundCustomException("Cliente no encontrado")));
                    newCart.setSale_date(LocalDate.now());
                    newCart.setStaff(null);
                    return cartService.save(newCart);
                });
        if (!"PURCHASE".equals(cart.getCart_type().getId()) || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("El carrito no está abierto o no es de tipo compra.");
        }
        if (!cart.getItems().isEmpty()) {
            Long existingBiz = cart.getItems().get(0).getProduct().getBusiness().getId();
            if (!existingBiz.equals(product.getBusiness().getId())) {
                return ResponseEntity.badRequest()
                        .body("El carrito ya contiene ítems de otra empresa. Vacíalo antes de agregar este producto.");
            }
        }
        int quantityToAdd = payload.get("quantity") == null ? 1 : Integer.parseInt(payload.get("quantity"));
        if (product.getStock() < quantityToAdd) {
            return ResponseEntity.badRequest().body("No hay suficiente stock de este producto.");
        }
        Optional<Item> existing = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(product.getId()))
                .findFirst();
        Item item;
        if (existing.isPresent()) {
            item = existing.get();
            item.setQuantity(item.getQuantity() + quantityToAdd);
        } else {
            item = new Item();
            item.setProduct(product);
            item.setCart(cart);
            item.setQuantity(quantityToAdd);
        }
        Item saved = service.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "Actualizar cantidad de un ítem",
            description = "Modifica el valor del campo 'quantity' para el ítem con el nombre dado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })

    @PutMapping("/client/update-quantity")
    public ResponseEntity<?> updateQuantityClient(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        String productName = payload.get("productName");
        String quantityStr = payload.get("quantity");

        if (productName == null || productName.isBlank()) {
            return ResponseEntity.badRequest().body("Falta el parámetro productName");
        }

        if (quantityStr == null || quantityStr.isBlank()) {
            return ResponseEntity.badRequest().body("Falta el parámetro quantity");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                return ResponseEntity.badRequest().body("La cantidad debe ser mayor a 0");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("El parámetro quantity debe ser numérico");
        }

        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId = clientService.getIdByCredentialId(credentialId);

        Cart cart = cartService.findByClientIdAndTypeAndStatus(clientId, "PURCHASE", "OPEN")
                .orElseThrow(() -> new EntityNotFoundCustomException("No tienes un carrito abierto de compra"));

        Optional<Item> itemOpt = cart.getItems().stream()
                .filter(i -> i.getProduct().getName().equalsIgnoreCase(productName))
                .findFirst();

        if (itemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un ítem con ese producto en tu carrito");
        }

        Item item = itemOpt.get();

        if (item.getProduct().getStock() < quantity) {
            return ResponseEntity.badRequest().body("No hay suficiente stock para este producto");
        }

        item.setQuantity(quantity);
        service.save(item);

        return ResponseEntity.ok("Cantidad actualizada");
    }
    @Operation(summary = "Actualziar cantidad de un item", description = "Actualiza la cantidad de un item de un carrito")
    @PutMapping("/staff/update-quantity/{itemId}/{quantity}")
    public ResponseEntity<?> updateQuantityStaff(
            @PathVariable @Positive Long itemId,
            @PathVariable @Positive int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService
                .getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        if (cart.getStaff() == null
                || !cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest()
                    .body("No tienes permiso para modificar este ítem.");
        }
        if (item.getProduct().getStock() < quantity) {
            return ResponseEntity.badRequest()
                    .body("No hay suficiente stock para este producto.");
        }
        item.setQuantity(quantity);
        service.save(item);
        return ResponseEntity.ok("Cantidad actualizada");
    }
    @Operation(summary = "Eliminar un item", description = "Elimina un item de un carrito")
    @DeleteMapping("/staff/delete/{itemId}")
    public ResponseEntity<?> deleteItemStaff(
            @PathVariable @Positive Long itemId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService
                .getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        if (cart.getStaff() == null
                || !cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest()
                    .body("No tienes permiso para eliminar este ítem.");
        }
        service.deleteById(itemId);
        return ResponseEntity.ok("Ítem eliminado");
    }


    @Operation(
            summary = "Eliminar ítem",
            description = "Elimina un ítem existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })

    @DeleteMapping("/client/delete")
    public ResponseEntity<?> deleteItemClient(
            @RequestBody Map<String, String> payload,
            @AuthenticationPrincipal UserDetails userDetails) {

        String productName = payload.get("productName");
        if (productName == null || productName.isBlank()) {
            return ResponseEntity.badRequest().body("Falta el parámetro productName");
        }
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long clientId = clientService.getIdByCredentialId(credentialId);

        Cart cart = cartService.findByClientIdAndTypeAndStatus(clientId, "PURCHASE", "OPEN")
                .orElseThrow(() -> new EntityNotFoundCustomException("No tienes un carrito abierto de compra"));

        Optional<Item> itemOpt = cart.getItems().stream()
                .filter(i -> i.getProduct().getName().equalsIgnoreCase(productName))
                .findFirst();

        if (itemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró un ítem con ese producto en tu carrito");
        }

        Item item = itemOpt.get();
        if (service.deleteMyItem(item.getId())) {
            return ResponseEntity.ok("Item eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

}
