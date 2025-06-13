package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Product;
import Caprish.Model.imp.business.dto.ProductViewDTO;
import Caprish.Repository.interfaces.business.ProductRepository;
import Caprish.Service.imp.business.BusinessService;
import Caprish.Service.imp.business.ProductService;
import Caprish.Service.imp.users.CredentialService;
import Caprish.Service.imp.users.StaffService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;



@PermitAll
@RestController
@RequestMapping("/product")
@Validated
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
        service.deleteById(toDelete.getId());
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
    public ResponseEntity<List<Product>> findByNameFromBusiness(@PathVariable @NotBlank String name,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        Long businessId = staffService.getBusinessIdByCredentialId(
                credentialService.getIdByUsername(userDetails.getUsername()));
        var optBusiness = businessService.findById(businessId);
        if (businessId == null || optBusiness.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
        List<Product> matching = optBusiness.get().getProducts().stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .toList();
        return ResponseEntity.ok(matching);
    }


    @PostMapping("/show-product")
    public ResponseEntity<Product> findObjectByName(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        try {
            return ResponseEntity.ok(service.findByName(name));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

            @GetMapping("/all-by-business/{businessName}")
    public ResponseEntity<List<Product>> findAllByBusinessName(@PathVariable @NotBlank String businessName) {
        var optBusiness = businessService.findByBusinessName(businessName);
        if (optBusiness == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.emptyList());
        }

        List<Product> products = optBusiness.getProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }


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

    @PutMapping("/updateDescription/{name}/{description}")
    public ResponseEntity<String> updateDescription(@PathVariable @NotBlank String name,
                                                    @PathVariable @NotBlank String description,
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
        return update(optProd.get().getId(), "description", description);
    }

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

    @PermitAll
    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllObjects() {
        return findAll();
    }

}