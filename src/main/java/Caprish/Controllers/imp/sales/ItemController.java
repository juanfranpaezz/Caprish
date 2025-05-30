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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/cart_item")
public class ItemController extends MyObjectGenericController<Item, ItemRepository, ItemService> {

    public ItemController(ItemService service) {
        super(service);
    }
    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody Item entity) {
        return create(entity);
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(@Valid @PathVariable Long id) {
        return delete(id);
    }

    /*@PreAuthorize("hasRole('SUPERVISOR')")
    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(@Valid @PathVariable Long id) {
        return update(id);
    }*/

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Item> findObjectById(@Valid @PathVariable Long id) {
        return findById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    @Override
    public List<Item> findAllObjects() {
        return findAll();
    }



}