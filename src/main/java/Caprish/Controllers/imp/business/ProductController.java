package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.ProductService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/product")
@Validated
@Tag(name = "Productos", description = "Gestión de productos disponibles para la venta")
@SecurityRequirement(name = "bearerAuth")
public class ProductController extends MyObjectGenericController<Product, ProductRepository, ProductService> {

    public ProductController(ProductService service) {
        super(service);
    }

    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto con nombre, descripción y precio.")
    @ApiResponse(responseCode = "200", description = "Producto creado exitosamente")
    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Product entity) {
        return create(entity);
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto existente por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(@Positive @PathVariable Long id) {
        return delete(id);
    }

    @Operation(summary = "Buscar producto por ID", description = "Obtiene los detalles de un producto específico.")
    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<Product> findObjectById(@Positive @PathVariable Long id) {
        return findById(id);
    }

    @Operation(summary = "Actualizar nombre", description = "Actualiza el nombre del producto.")
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<String> updateName(
            @Parameter(description = "ID del producto") @PathVariable @Positive Long id,
            @Parameter(description = "Nuevo nombre") @PathVariable String name) {
        return update(id, "name", name);
    }

    @Operation(summary = "Actualizar descripción", description = "Actualiza la descripción del producto.")
    @PutMapping("/updateDescription/{id}/{description}")
    public ResponseEntity<String> updateDescription(
            @Parameter(description = "ID del producto") @PathVariable @Positive Long id,
            @Parameter(description = "Nueva descripción") @PathVariable String description) {
        return update(id, "description", description);
    }

    @Operation(summary = "Actualizar precio", description = "Actualiza el precio del producto.")
    @PutMapping("/updatePrice/{id}/{price}")
    public ResponseEntity<String> updatePrice(
            @Parameter(description = "ID del producto") @PathVariable @Positive Long id,
            @Parameter(description = "Nuevo precio") @PathVariable Double price) {
        return update(id, "price", price);
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @PermitAll
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllObjects() {
        return findAll();
    }
}