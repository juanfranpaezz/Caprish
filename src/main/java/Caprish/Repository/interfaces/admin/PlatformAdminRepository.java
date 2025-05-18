package Caprish.Repository.interfaces.admin;

import Caprish.Model.imp.admin.PlatformAdmin;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PlatformAdminRepository extends MyObjectGenericRepository<PlatformAdmin, Long> {
}
