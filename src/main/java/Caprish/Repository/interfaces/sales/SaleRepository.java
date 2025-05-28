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

    @Query("""
    SELECT new Caprish.Model.imp.sales.dto.SalesReportDto(
        s.id, s.sale_date, s.total_amount, st.first_name, st.last_name
    )
    FROM Sale s
    JOIN s.staff st
    WHERE st.business.id = (
        SELECT st2.business.id
        FROM Staff st2
        WHERE st2.id = :staffId
    )
    ORDER BY s.sale_date DESC
""")
    List<SalesReportDto> findVentasReporteByStaffId(@Param("staffId") Long staffId);
}
