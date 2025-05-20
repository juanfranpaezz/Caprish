package Caprish.Repository.interfaces.business;

import Caprish.Model.imp.business.Business;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BusinessRepository extends MyObjectGenericRepository<Business> {
}
