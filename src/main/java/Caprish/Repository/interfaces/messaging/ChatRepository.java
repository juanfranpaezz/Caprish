package Caprish.Repository.interfaces.messaging;

import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRepository extends MyObjectGenericRepository<Chat> {
    @Query("""
        SELECT c
          FROM Chat c
         WHERE c.business.id = :businessId
           AND c.client.id   = :clientId
        """)
    Chat findByBusinessIdAndClientId(
            @Param("businessId") Long businessId,
            @Param("clientId")   Long clientId
    );
    @Query("""
        SELECT c.id
          FROM Chat c
         WHERE c.business.id = :businessId
           AND c.client.id   = :clientId
        """)
    Long findIdByBusinessIdAndClientId(
            @Param("businessId") Long businessId,
            @Param("clientId")   Long clientId
    );
}
