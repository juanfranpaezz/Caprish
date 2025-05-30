package Caprish.Controllers.enums;

import Caprish.Model.enums.CartType;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.enums.CartTypeRepository;
import Caprish.Service.enums.CartTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart_type")
public class CartTypeController extends MyEnumGenericController<CartType, CartTypeRepository, CartTypeService> {
    public CartTypeController(CartTypeService service) {
        super(service);
    }


}
