package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface BusinessRepository extends MyObjectGenericRepository<Business> {

    boolean existsByBusinessName(String businessName);
    boolean existsByTax(int tax);
    Long findIdByBusinessName(String businessName);
    Optional<Business>findByBusinessName (String businessName);
}
