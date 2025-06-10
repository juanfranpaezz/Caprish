package Caprish.Repository.interfaces.users;


import Caprish.Model.imp.users.Staff;
import Caprish.Model.imp.users.Credential;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StaffRepository extends MyObjectGenericRepository<Staff> {
    Optional<Staff> findByCredential(Credential credential);
}
