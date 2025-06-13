package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.business.BusinessRepository;
import Caprish.Service.imp.business.BusinessService;
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

@RestController
@RequestMapping("/business")
@Validated
@Tag(name = "Negocios", description = "Operaciones relacionadas con entidades de tipo Business")
public class BusinessController extends MyObjectGenericController<Business, BusinessRepository, BusinessService> {

    public BusinessController(BusinessService service) {
        super(service);
    }

    @Operation(summary = "Eliminar un negocio", description = "Elimina un negocio a partir de su ID")
    @ApiResponse(responseCode = "200", description = "Negocio eliminado correctamente")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(
            @PathVariable @Parameter(description = "ID del negocio") @Positive Long id) {
        return delete(id);
    }

    @Operation(summary = "Buscar negocio por ID", description = "Obtiene un negocio usando su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Negocio encontrado"),
            @ApiResponse(responseCode = "404", description = "Negocio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Business> findObjectById(
            @PathVariable @Parameter(description = "ID del negocio") @Positive Long id) {
        return findById(id);
    }

    @Operation(summary = "Actualizar nombre del negocio")
    @PutMapping("/updateBusinessName/{id}/{name}")
    public ResponseEntity<String> updateBusinessName(
            @PathVariable @Parameter(description = "ID del negocio") @Positive Long id,
            @PathVariable @Parameter(description = "Nuevo nombre del negocio") String name) {
        return update(id, "bussiness_name", name);
    }

    @Operation(summary = "Actualizar descripción del negocio")
    @PutMapping("/updateDescription/{id}/{description}")
    public ResponseEntity<String> updateDescription(
            @PathVariable @Parameter(description = "ID del negocio") @Positive Long id,
            @PathVariable @Parameter(description = "Nueva descripción") String description) {
        return update(id, "description", description);
    }

    @Operation(summary = "Actualizar eslogan del negocio")
    @PutMapping("/updateSlogan/{id}/{slogan}")
    public ResponseEntity<String> updateSlogan(
            @PathVariable @Parameter(description = "ID del negocio") @Positive Long id,
            @PathVariable @Parameter(description = "Nuevo eslogan") String slogan) {
        return update(id, "slogan", slogan);
    }

    @Operation(summary = "Actualizar valor del impuesto del negocio")
    @PutMapping("/updateTax/{id}/{tax}")
    public ResponseEntity<String> updateTax(
            @PathVariable @Parameter(description = "ID del negocio") @Positive Long id,
            @PathVariable @Parameter(description = "Nuevo valor de impuesto") int tax) {
        return update(id, "tax", tax);
    }

    @Operation(summary = "Obtener todos los negocios")
    @ApiResponse(responseCode = "200", description = "Lista de negocios obtenida con éxito")
    @GetMapping("/all")
    public ResponseEntity<List<Business>> findAllObjects() {
        return findAll();
    }
}
