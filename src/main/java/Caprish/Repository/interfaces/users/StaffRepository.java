package Caprish.Repository.interfaces.users;

import Caprish.Model.imp.users.Staff;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends UserGenericRepository<Staff, Long> {
}
