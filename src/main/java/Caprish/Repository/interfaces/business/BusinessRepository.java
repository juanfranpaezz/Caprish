package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

public interface BusinessRepository extends MyObjectGenericRepository<Business> {

    boolean existsByBusinessName(String businessName);
    boolean existsByTax(int tax);
    Long findIdByBusinessName(String businessName);
}
