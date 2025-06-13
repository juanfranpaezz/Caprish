package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Stock;
import Caprish.Repository.interfaces.business.StockRepository;
import Caprish.Service.imp.business.StockService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/stock")
@Validated
@Tag(name = "Stock", description = "Gestión de unidades en stock de productos")
@SecurityRequirement(name = "bearerAuth")
public class StockController extends MyObjectGenericController<Stock, StockRepository, StockService> {

    public StockController(StockService service) {
        super(service);
    }

    @Operation(
            summary = "Obtener listado de stock",
            description = "Devuelve todos los registros del stock disponibles en el sistema."
    )
    @ApiResponse(responseCode = "200", description = "Listado retornado correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<Stock>> findAllObjects() {
        return findAll();
    }

    @Operation(
            summary = "Actualizar cantidad en stock",
            description = "Permite modificar la cantidad disponible de un producto en stock mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada con éxito"),
            @ApiResponse(responseCode = "400", description = "ID o cantidad inválida"),
            @ApiResponse(responseCode = "404", description = "Stock no encontrado")
    })
    @PutMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<String> updateQuantity(
            @Parameter(description = "ID del stock") @PathVariable @Positive Long id,
            @Parameter(description = "Nueva cantidad a establecer") @PathVariable @Positive Integer quantity) {
        return update(id, "quantity", quantity);
    }
}