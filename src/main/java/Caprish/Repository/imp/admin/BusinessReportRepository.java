package Caprish.Repository.imp.admin;

import Caprish.Model.imp.admin.BusinessReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessReportRepository extends JpaRepository<BusinessReport, Long> {
}
