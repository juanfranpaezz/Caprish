package Caprish.Service.imp.business;

import Caprish.Model.imp.business.Voucher;
import Caprish.Repository.interfaces.business.VoucherRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class VoucherService extends MyObjectGenericService<Voucher, VoucherRepository> {
    protected VoucherService(VoucherRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Voucher> getEntityClass() {
        return Voucher.class;
    }
    public int changeValid_from(Long id, LocalDate value) {
        return updateField(id, "valid_from", value);
    }


    public int changeValid_to(Long id, LocalDate value) {
        return updateField(id, "valid_to", value);
    }


    public int changeDiscount_percent(Long id, BigDecimal value) {
        return updateField(id, "discount_percent", value);
    }


}