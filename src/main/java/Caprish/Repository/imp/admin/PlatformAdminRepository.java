package Caprish.Repository.imp.admin;

import Caprish.Model.imp.admin.PlatformAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformAdminRepository extends JpaRepository<PlatformAdmin, Long> {
}
