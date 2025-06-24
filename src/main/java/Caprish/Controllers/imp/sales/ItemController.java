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

    @PostMapping("/staff/add-from-sale")
    public ResponseEntity<?> addItemFromSale(@RequestBody Map<String, String> payload,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService
                .getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername()));
        Long productId = productService.findIdByName(payload.get("productName"));
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
        int quantity = payload.get("quantity") == null ? 1 : Integer.parseInt(payload.get("quantity"));
        if (productService.findById(productId).get().getStock() < quantity) return ResponseEntity.badRequest().body("No hay suficiente stock este producto.");
        Item item = new Item();
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setCart(cart);
        Item saved = service.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "Agregar productos",
            description = "Agrega un producto, crea un carrito si no tiene ninguno asociado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })

    @PostMapping("/client/add-from-purchase")
    public ResponseEntity<?> addItemFromPurchase(
            @AuthenticationPrincipal UserDetails userDetails,
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

        Cart cart = cartService.findByClientIdAndTypeAndStatus(
                clientId, "PURCHASE", "OPEN").orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setCart_type(new CartType("PURCHASE"));
            cart.setCart_status(new CartStatus("OPEN"));
            cart.setClient(clientService.findById(clientId)
                    .orElseThrow(() -> new EntityNotFoundCustomException("Cliente no encontrado")));
            cart.setSale_date(LocalDate.now());
            cart.setStaff(null);
            cart = cartService.save(cart);
        }

        if (!"PURCHASE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("El carrito no está abierto o no es de tipo compra.");
        }

        if (!cart.getItems().isEmpty()) {
            Long existingBiz = cart.getItems().get(0).getProduct().getBusiness().getId();
            Long newBiz = product.getBusiness().getId();
            if (!existingBiz.equals(newBiz)) {
                return ResponseEntity.badRequest()
                        .body("El carrito ya contiene ítems de otra empresa. Vacíalo antes de agregar este producto.");
            }
        }

        int quantity = payload.get("quantity") == null ? 1 : Integer.parseInt(payload.get("quantity"));
        if (product.getStock() < quantity) {
            return ResponseEntity.badRequest().body("No hay suficiente stock de este producto.");
        }

        Item item = new Item();
        item.setQuantity(quantity);
        item.setProduct(product);
        item.setCart(cart);

        Item saved = service.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(
            summary = "Actualizar cantidad de un ítem",
            description = "Modifica el valor del campo 'quantity' para el ítem con el ID dado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @PutMapping("/staff/update-quantity/{itemId}/{quantity}")
    public ResponseEntity<?> updateQuantityStaff(@PathVariable @Positive Long itemId,
                                                 @PathVariable @Positive int quantity,
                                                 @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        if (item.getProduct().getStock() < item.getQuantity()) return ResponseEntity.badRequest().body("No hay suficiente stock este producto.");
        Cart cart = item.getCart();

        if (!cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No tienes permiso para modificar este ítem.");
        }
        item.setQuantity(quantity);
        service.save(item);
        return ResponseEntity.ok("Cantidad actualizada");
    }

    @Operation(
            summary = "Actualizar cantidad de un ítem",
            description = "Modifica el valor del campo 'quantity' para el ítem con el ID dado"
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


    @Operation(
            summary = "Eliminar ítem",
            description = "Elimina un ítem existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/staff/delete/{itemId}")
    public ResponseEntity<?> deleteItemStaff(@PathVariable @Positive Long itemId,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        Item item = service.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundCustomException("Item no encontrado"));
        Cart cart = item.getCart();
        if (!cart.getStaff().getBusiness().getId().equals(businessId)
                || !"SALE".equals(cart.getCart_type().getId())
                || !"OPEN".equals(cart.getCart_status().getId())) {
            return ResponseEntity.badRequest().body("No tienes permiso para eliminar este ítem.");
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
