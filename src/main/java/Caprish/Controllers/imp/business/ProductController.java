package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Product;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.business.ProductService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@PermitAll
@RestController
@RequestMapping("/product")
@Validated
@Tag(name = "Productos", description = "Gestión de productos disponibles para la venta")
public class ProductController extends MyObjectGenericController<Product, ProductRepository, ProductService> {

    @Autowired
    BusinessService businessService;
    @Autowired
    StaffService staffService;
    @Autowired
    CredentialService credentialService;

    public ProductController(ProductService service) {
        super(service);
    }


    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto con nombre, descripción y precio.")
    @ApiResponse(responseCode = "200", description = "Producto creado exitosamente")
    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Product entity,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        if (businessId == null || businessService.findById(businessId).isEmpty()) {
            return ResponseEntity.badRequest().body("Negocio no encontrado.");
        }
        var business = businessService.findById(businessId).get();
        if (business.getProducts().stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(entity.getName()))) {
            return ResponseEntity.badRequest().body("El producto ya existe.");
        }
        entity.setBusiness(business);
        return create(entity);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteByName(@PathVariable @NotBlank String name,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());
        Long businessId   = staffService.getBusinessIdByCredentialId(credentialId);
        var optBusiness = businessService.findById(businessId);
        if (businessId == null || optBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body("Negocio no encontrado.");
        }
        Product toDelete = optBusiness.get().getProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
        if (toDelete == null) {
            return ResponseEntity.badRequest().body("El producto '" + name + "' no existe.");
        }
        service.deleteProductById(toDelete.getId());
        return ResponseEntity.ok("Producto '" + name + "' eliminado correctamente.");
    }

    @GetMapping("/client/name/{name}")
    public ResponseEntity<List<Product>> findByNameFromClient(@PathVariable @NotBlank String name) {
        List<Product> all = service.findAll();
        if (all.isEmpty()) return ResponseEntity.noContent().build();
        List<Product> matching = all.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .toList();
        return ResponseEntity.ok(matching);
    }

    @GetMapping("/staff/name/{name}")
    public ResponseEntity<?> findByNameFromBusiness(
            @PathVariable @NotBlank String name,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long credentialId = credentialService.getIdByUsername(userDetails.getUsername());

        Long businessId = staffService.getBusinessIdByCredentialId(credentialId);

        var optBusiness = businessService.findById(businessId);
        if (businessId == null || optBusiness.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se encontró un negocio asociado al usuario");
            return ResponseEntity.badRequest().body(error);
        }

        List<Product> matching = optBusiness.get().getProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .toList();

        if (matching.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se encontraron productos con el nombre especificado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(matching);
    }

    @GetMapping("/all-by-business/{businessName}")
    public ResponseEntity<?> findAllByBusinessName(@PathVariable @NotBlank String businessName) {
        var optBusiness = businessService.findByBusinessName(businessName);
        if (optBusiness == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se encontró un negocio con ese nombre");
            return ResponseEntity.badRequest().body(error);
        }
        List<Product> products = optBusiness.getProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all-by-my-business")
    public ResponseEntity<?> findAllByBusinessName(@AuthenticationPrincipal UserDetails userDetails) {
        var optBusiness = businessService.findById(staffService.getBusinessIdByCredentialId(credentialService.getIdByUsername(userDetails.getUsername())));
        if (optBusiness.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se encontró un negocio con ese nombre");
            return ResponseEntity.badRequest().body(error);
        }
        List<Product> products = optBusiness.get().getProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }


    @Operation(summary = "Actualizar nombre", description = "Actualiza el nombre del producto.")
    @PutMapping("/updateName/{oldName}/{newName}")
    public ResponseEntity<String> updateName(@PathVariable @NotBlank String oldName,
                                             @PathVariable @NotBlank String newName,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        var optBusiness = businessService.findById(businessId);
        if (businessId == null || optBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body("Negocio no encontrado.");
        }
        var products = optBusiness.get().getProducts();
        var optProd = products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(oldName))
                .findFirst();
        if (optProd.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No se encontró el producto '" + oldName + "'.");
        }
        if (products.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(newName))) {
            return ResponseEntity.badRequest()
                    .body("Ya existe un producto con el nombre '" + newName + "'.");
        }
        Long id = optProd.get().getId();
        return update(id, "name", newName);
    }

    @Operation(summary = "Actualizar descripción", description = "Actualiza la descripción del producto.")
    @PutMapping("/updateDescription")
    public ResponseEntity<String> updateDescription(
                                                    @RequestBody Map <String, String> request,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String name = request.get("name");
        String description = request.get("description");
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        var optBusiness = businessService.findById(businessId);
        if (businessId == null || optBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body("Negocio no encontrado.");
        }
        var optProd = optBusiness.get().getProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
        if (optProd.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No se encontró el producto '" + name + "'.");
        }
        return update(optProd.get().getId(), "description", description);
    }

    @Operation(summary = "Actualizar precio", description = "Actualiza el precio del producto.")
    @PutMapping("/updatePrice/{name}/{price}")
    public ResponseEntity<String> updatePrice(@PathVariable @NotBlank String name,
                                              @PathVariable @Positive Double price,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        var optBusiness = businessService.findById(businessId);
        if (businessId == null || optBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body("Negocio no encontrado.");
        }
        var optProd = optBusiness.get().getProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
        if (optProd.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No se encontró el producto '" + name + "'.");
        }
        return update(optProd.get().getId(), "price", price);
    }

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos disponibles.")
    @PermitAll
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllObjects() {
        return findAll();
    }

}