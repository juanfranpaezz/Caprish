package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.business.Branch;
import Caprish.Repository.interfaces.business.BranchRepository;
import Caprish.Service.imp.business.BranchService;
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
@RequestMapping("/branch")
@Validated
@Tag(name = "Sucursales", description = "Gestión de sucursales del negocio")
public class BranchController extends MyObjectGenericController<Branch, BranchRepository, BranchService> {

    public BranchController(BranchService service) {
        super(service);
    }

    @Operation(summary = "Crear nueva sucursal", description = "Registra una nueva sucursal en el sistema.")
    @ApiResponse(responseCode = "200", description = "Sucursal creada correctamente")
    @PostMapping("/create")
    public ResponseEntity<String> createObject(@Valid @RequestBody Branch entity) {
        return create(entity);
    }

    @Operation(summary = "Eliminar una sucursal", description = "Elimina una sucursal según su ID.")
    @ApiResponse(responseCode = "200", description = "Sucursal eliminada correctamente")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteObject(
            @PathVariable @Parameter(description = "ID de la sucursal a eliminar") @Positive Long id) {
        return delete(id);
    }

    @Operation(summary = "Actualizar dirección de la sucursal", description = "Modifica la dirección de una sucursal existente.")
    @PutMapping("/updateAddress/{id}/{address}")
    public ResponseEntity<String> updateAddress(
            @PathVariable @Parameter(description = "ID de la sucursal") @Positive Long id,
            @PathVariable @Parameter(description = "Nueva dirección") String address) {
        return update(id, "address", address);
    }

    @Operation(summary = "Actualizar tipo de sucursal", description = "Modifica el tipo de la sucursal (por ejemplo, depósito, atención al cliente, etc.).")
    @PutMapping("/updateBranchType/{id}/{type}")
    public ResponseEntity<String> updateBranchType(
            @PathVariable @Parameter(description = "ID de la sucursal") @Positive Long id,
            @PathVariable @Parameter(description = "Nuevo tipo de sucursal") String type) {
        return update(id, "branch_type", type);
    }

    @Operation(summary = "Buscar sucursal por ID", description = "Obtiene los datos de una sucursal usando su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
            @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Branch> findObjectById(
            @PathVariable @Parameter(description = "ID de la sucursal") @Positive Long id) {
        return findById(id);
    }

    @Operation(summary = "Listar todas las sucursales", description = "Devuelve el listado completo de sucursales registradas.")
    @ApiResponse(responseCode = "200", description = "Lista devuelta correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<Branch>> findAllObjects() {
        return findAll();
    }
}