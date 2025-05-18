package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Voucher;
import Caprish.Repository.interfaces.business.VoucherRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class VoucherService extends MyObjectGenericService<Voucher, VoucherRepository> {
    protected VoucherService(VoucherRepository childRepository) {
        super(childRepository);
    }

    public void changeValid_from(Long id, LocalDate valid_from) {
        ((VoucherService) AopContext.currentProxy()).updateField(id, "valid_from", valid_from);
    }


    public void changeValid_to(Long id, LocalDate valid_to) {
        ((VoucherService) AopContext.currentProxy()).updateField(id, "valid_to", valid_to);
    }


    public void changeDiscount_percent(Long id, BigDecimal discount_percent) {
        ((VoucherService) AopContext.currentProxy()).updateField(id, "discount_percent", discount_percent);
    }


}