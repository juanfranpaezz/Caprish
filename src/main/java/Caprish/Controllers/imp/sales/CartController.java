package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.sales.dto.CartViewDTO;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.sales.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@Validated
@Tag(name = "Carritos", description = "Gestión de carritos de compra y ventas asociadas")
public class CartController extends MyObjectGenericController<Cart, CartRepository, CartService> {

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
    public ResponseEntity<String> createObject(
            @Valid @RequestBody Cart entity) {
        return create(entity);
    }

    @Operation(
            summary = "Eliminar carrito",
            description = "Elimina un carrito según su ID"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(
            @Parameter(description = "ID del carrito a eliminar") @Positive @PathVariable Long id) {
        return delete(id);
    }

    @Operation(
            summary = "Buscar carrito por ID",
            description = "Obtiene un carrito en función de su identificador"
    )
    @GetMapping("/{id}")
    public ResponseEntity<Cart> findObjectById(
            @Parameter(description = "ID del carrito") @Positive @PathVariable Long id) {
        return findById(id);
    }

    @Operation(
            summary = "Actualizar ID del cliente",
            description = "Actualiza el ID del cliente asociado a un carrito"
    )
    @PutMapping("/updateClientId/{id}/{clientId}")
    public ResponseEntity<String> updateClientId(
            @Parameter(description = "ID del carrito") @PathVariable @Positive Long id,
            @Parameter(description = "Nuevo ID de cliente") @PathVariable @Positive Long clientId) {
        return update(id, "client_id", clientId);
    }

    @Operation(
            summary = "Actualizar estado del carrito",
            description = "Actualiza el estado de un carrito (ej: PENDING, COMPLETED)"
    )
    @PutMapping("/updateCartStatus/{id}/{status}")
    public ResponseEntity<String> updateCartStatus(
            @Parameter(description = "ID del carrito") @PathVariable @Positive Long id,
            @Parameter(description = "Nuevo estado") @PathVariable String status) {
        return update(id, "cart_status", status);
    }

    @Operation(
            summary = "Actualizar ID del empleado",
            description = "Asigna o actualiza el ID del empleado que gestiona el carrito"
    )
    @PutMapping("/updateStaffId/{id}/{staffId}")
    public ResponseEntity<String> updateStaffId(
            @Parameter(description = "ID del carrito") @PathVariable @Positive Long id,
            @Parameter(description = "Nuevo ID de staff") @PathVariable @Positive Long staffId) {
        return update(id, "staff_id", staffId);
    }

    @Operation(
            summary = "Listar todos los carritos",
            description = "Devuelve todos los carritos registrados en el sistema"
    )
    @GetMapping("/all")
    public ResponseEntity<List<Cart>> findAllObjects() {
        return findAll();
    }

    @Operation(
            summary = "Ver mis ventas",
            description = "Obtiene los carritos (ventas) relacionados con una empresa específica, útil para vista del supervisor"
    )
    @ApiResponse(responseCode = "200", description = "Ventas devueltas correctamente")
    @GetMapping("/view/my-sales")
    public ResponseEntity<List<CartViewDTO>> getMySales(
            @Parameter(description = "ID de la empresa") @RequestParam Long idBusiness) {
        return ResponseEntity.ok(service.getCartViewsByBusiness(idBusiness));
    }
}
