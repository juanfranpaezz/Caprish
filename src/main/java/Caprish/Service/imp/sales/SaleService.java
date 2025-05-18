package Caprish.Service.imp.sales;

import Caprish.Model.imp.sales.Sale;
import Caprish.Repository.interfaces.sales.SaleRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;


@Service
public class SaleService extends MyObjectGenericService<Sale, SaleRepository> {

    protected SaleService(SaleRepository repository) {
        super(repository);
    }

}