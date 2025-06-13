package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Model.imp.sales.dto.ClientPurchaseDTO;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.sales.CartService;
import Caprish.Service.imp.users.ClientService;
import Caprish.Service.imp.users.CredentialService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/cart")
@Validated
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {
    private final CredentialService credentialService;
    private final ClientService clientService;

    public CartController(CartService service, CredentialService credentialService, ClientService clientService) {
        super(service);
        this.credentialService = credentialService;
        this.clientService = clientService;
    }


    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Cart entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @PutMapping("/updateClientId/{id}/{clientId}")
    public ResponseEntity<String> updateClientId(@PathVariable @Positive Long id,
                                                 @PathVariable @Positive Long clientId) {
        return update(id, "client_id", clientId);
    }

    @PutMapping("/updateCartStatus/{id}/{status}")
    public ResponseEntity<String> updateCartStatus(@PathVariable @Positive Long id,
                                                   @PathVariable String status) {
        return update(id, "cart_status", status);
    }

    @PutMapping("/updateStaffId/{id}/{staffId}")
    public ResponseEntity<String> updateStaffId(@PathVariable @Positive Long id,
                                                @PathVariable @Positive Long staffId) {
        return update(id, "staff_id", staffId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> findAllObjects() {
        return findAll();
    }

    @GetMapping("/view/my-sales")
    public ResponseEntity<List<CartViewDTO>> getMySales(@RequestParam Long idBusiness) {
        return ResponseEntity.ok(service.getCartViewsByBusiness(idBusiness));
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

}

