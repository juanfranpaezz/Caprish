package Caprish.Repository.interfaces.admin;

import Caprish.Model.imp.admin.ClientReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientReportRepository extends JpaRepository<ClientReport, Long> {
}
