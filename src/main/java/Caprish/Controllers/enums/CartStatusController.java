package Caprish.Controllers.enums;

import Caprish.Model.enums.CartStatus;
import Caprish.Model.imp.admin.BusinessReport;
import Caprish.Repository.enums.CartStatusRepository;
import Caprish.Service.enums.CartStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart_status")
public class CartStatusController extends MyEnumGenericController<CartStatus, CartStatusRepository, CartStatusService> {
    public CartStatusController(CartStatusService service) {
        super(service);
    }

}
