package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Sale;
import Caprish.Model.imp.sales.dto.SalesReportDto;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends MyObjectGenericRepository<Sale> {
    Optional<Sale> findByCartId(Long id);

    @Query("SELECT new Caprish.Model.imp.sales.dto.SalesReportDto(s.id, s.sale_date, s.total_amount, CONCAT(st.first_name, ' ', st.last_name)) " +
            "FROM Sale s LEFT JOIN s.staff st " +
            "WHERE st.id = :staffId")
    List<SalesReportDto> findVentasReporteByStaffId(@Param("staffId") Long staffId);
}
