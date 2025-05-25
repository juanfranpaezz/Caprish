package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.Sale;
import Caprish.Model.imp.sales.dto.SalesReportDto;
import Caprish.Repository.interfaces.sales.SaleRepository;
import Caprish.Service.imp.MyObjectGenericService;
import jakarta.persistence.EntityNotFoundException;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaleService extends MyObjectGenericService<Sale, SaleRepository, SaleService> {

    protected SaleService(SaleRepository repository) {
        super(repository);
    }

    public List<SalesReportDto> getSalesStaff(Long staffId) {
        List<SalesReportDto> sales= repository.findVentasReporteByStaffId(staffId);
        if(sales.isEmpty()){
            throw new EntityNotFoundException("Sales report not found");
        }
        return sales;
    }

}