package Caprish.Controllers.imp.sales;


import Caprish.Controllers.MyObjectGenericController;
import Caprish.Exception.InvalidEntityException;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Model.imp.sales.Item;
import Caprish.Repository.interfaces.sales.ItemRepository;
import Caprish.Service.imp.sales.ItemService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart_item")
public class ItemController extends MyObjectGenericController<Item, ItemRepository, ItemService> {

    public ItemController(ItemService service) {
        super(service);
    }

    @PostMapping("/create")
    @Override
    public ResponseEntity<String> createObject(@RequestBody Item entity) {
        return create(entity);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public ResponseEntity<String> deleteObject(Long id) {
        return delete(id);
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> updateObject(Long id) {
        return update(id);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Item> findObjectById(Long id) {
        return findById(id);
    }

    @GetMapping("/all")
    @Override
    public List<Item> findAllObjects() {
        return findAll();
    }



}