package Caprish.Repository.interfaces.sales;

import Caprish.Model.imp.sales.Cart;
import Caprish.Model.imp.users.Client;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface CartRepository extends MyObjectGenericRepository<Cart> {

    @Query(value =
            "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END " +
                    "FROM cart c " +
                    "JOIN client cl ON cl.id = c.id_client " +
                    "JOIN staff s  ON s.id  = c.id_staff " +
                    "JOIN business b ON b.id = s.business_id " +
                    "WHERE b.id   = :idBusiness " +
                    "  AND cl.id  = :idClient",
            nativeQuery = true
    )
    Long existsByBusinessIdAndClientId(
            @Param("idBusiness") Long businessId,
            @Param("idClient")   Long clientId
    );


    @Query(value = """
    SELECT DISTINCT cl.*
    FROM cart ca
    JOIN client cl ON cl.id = ca.id_client
    JOIN staff st ON st.id = ca.id_staff
    JOIN business b ON b.id = st.business_id
    WHERE b.id = :businessId
    """, nativeQuery = true)
    List<Client> findClientsByBusinessId(@Param("businessId") Long businessId);



    @Query(value = "SELECT " +
            "c.id, " +
            "CONCAT(cl_cred.first_name, ' ', cl_cred.last_name) AS client_name, " +
            "ct.id AS cart_type, " +
            "cs.id AS cart_status, " +
            "CONCAT(st_cred.first_name, ' ', st_cred.last_name) AS staff_name, " +
            "b.id AS business_id, " +
            "SUM(p.price * i.quantity)\n AS total " +
            "FROM cart c " +
            "JOIN client cl ON cl.id = c.id_client " +
            "JOIN credential cl_cred ON cl.id_credential = cl_cred.id " +
            "JOIN cart_type ct ON ct.id = c.id_cart_type " +
            "JOIN cart_status cs ON cs.id = c.id_cart_status " +
            "JOIN staff s ON s.id = c.id_staff " +
            "LEFT JOIN credential st_cred ON st.id_credential = st_cred.id " +
            "JOIN business b ON b.id = s.business_id " +
            "JOIN item i ON i.id_cart = c.id " +
            "JOIN product p ON p.id = i.id_product " +
            "WHERE cs.value = 'FINALIZADO' AND b.id = :idBusiness " +
            "GROUP BY c.id, cl_cred.first_name, cl_cred.last_name, ct.id, cs.id, st_cred.first_name, st_cred.last_name, b.id",
            nativeQuery = true)
    List<Object[]> getCartViewsByBusinessId(@Param("businessId") Long idBusiness);

    @Query(value = "SELECT " +
            "c.id, " +
            "CONCAT(cl_cred.first_name, ' ', cl_cred.last_name) AS client_name, " +
            "ct.id AS cart_type, " +
            "cs.id AS cart_status, " +
            "COALESCE(CONCAT(st_cred.first_name, ' ', st_cred.last_name), 'Compra web') AS staff_name, " +
            "b.business_name AS business_name, " +
            "SUM(p.price * i.quantity) AS total " +
            "FROM cart c " +
            "JOIN client cl ON cl.id = c.id_client " +
            "JOIN credential cl_cred ON cl.id_credential = cl_cred.id " +
            "JOIN cart_type ct ON ct.id = c.id_cart_type " +
            "JOIN cart_status cs ON cs.id = c.id_cart_status " +
            "LEFT JOIN staff st ON st.id = c.id_staff " +
            "LEFT JOIN credential st_cred ON st.id_credential = st_cred.id " +
            "JOIN item i ON i.id_cart = c.id " +
            "JOIN product p ON p.id = i.id_product " +
            "JOIN business b ON b.id = p.business_id " +
            "WHERE cs.id = 'FINALIZADO' AND cl_cred.username = :username " +
            "GROUP BY c.id, cl_cred.first_name, cl_cred.last_name, ct.id, cs.id, st_cred.first_name, st_cred.last_name, b.id",
            nativeQuery = true)
    List<Object[]> findFinalizedCartsByClient(@Param("clientId") Long clientId, @Param("username") String user);
    @Query("SELECT c FROM Cart c " +
            "WHERE c.client.id = :clientId " +
            "AND c.cart_type.id = :typeId " +
            "AND c.cart_status.id = :statusId")
    Optional<Cart> findByClientIdAndTypeAndStatus(
            @Param("clientId") Long clientId,
            @Param("typeId") String typeId,
            @Param("statusId") String statusId
    );
}

