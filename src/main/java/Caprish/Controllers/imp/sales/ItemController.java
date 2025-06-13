package Caprish.Controllers.imp.sales;


import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.sales.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/item")
@Validated
@Tag(name = "Ítems", description = "Gestión de ítems dentro del sistema (productos en un carrito, pedido, etc.)")
public class ItemController extends MyObjectGenericController<Item, ItemRepository, ItemService> {

    public ItemController(ItemService service) {
        super(service);
    }

    @Operation(
            summary = "Agregar un ítem",
            description = "Crea un nuevo ítem en la base de datos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping("/add")
    public ResponseEntity<String> createObject(
            @Valid @RequestBody Item entity) {
        return create(entity);
    }

    @Operation(
            summary = "Eliminar ítem",
            description = "Elimina un ítem existente por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem eliminado correctamente"),
            @ApiResponse(responseCode = "400", description = "ID inválido")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(
            @Parameter(description = "ID del ítem a eliminar", required = true)
            @Positive @PathVariable Long id) {
        return delete(id);
    }

    @Operation(
            summary = "Actualizar cantidad de un ítem",
            description = "Modifica el valor del campo 'quantity' para el ítem con el ID dado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cantidad actualizada correctamente"),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos")
    })
    @PutMapping("/updateQuantity/{id}/{quantity}")
    public ResponseEntity<String> updateQuantity(
            @Parameter(description = "ID del ítem", required = true)
            @PathVariable @Positive Long id,

            @Parameter(description = "Nueva cantidad", required = true)
            @PathVariable int quantity) {

        return update(id, "quantity", quantity);
    }

    @Operation(
            summary = "Listar todos los ítems",
            description = "Obtiene una lista de todos los ítems registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<Item>> findAllObjects() {
        return findAll();
    }
}