package Caprish.Controllers.imp.business;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.business.Voucher;
import Caprish.Repository.interfaces.business.VoucherRepository;
import Caprish.Service.imp.business.VoucherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/voucher")
public class VoucherController extends MyObjectGenericController<Voucher, VoucherRepository, VoucherService> {

    public VoucherController(VoucherService service) {
        super(service);
    }

}