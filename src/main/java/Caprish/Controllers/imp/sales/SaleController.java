package Caprish.Controllers.imp.sales;

import Caprish.Controllers.MyObjectGenericController;
import Caprish.Model.imp.sales.Sale;
import Caprish.Model.imp.sales.dto.SalesReportDto;
import Caprish.Repository.interfaces.sales.SaleRepository;
import Caprish.Service.imp.sales.SaleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController extends MyObjectGenericController<Sale, SaleRepository, SaleService> {

    public SaleController(SaleService service) {
        super(service);
    }

    @GetMapping("/my-sales/{staffId}")
    public List<SalesReportDto>getMySales(@PathVariable Long staffId){
        return service.getSalesStaff(staffId);
    }

}