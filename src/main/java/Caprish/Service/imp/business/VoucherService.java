package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Voucher;
import Caprish.Repository.interfaces.business.VoucherRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class VoucherService extends MyObjectGenericService<Voucher, VoucherRepository, VoucherService> {
    protected VoucherService(VoucherRepository childRepository) {
        super(childRepository);
    }


}