package Caprish.Repository.interfaces.messaging;

import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageRepository extends MyObjectGenericRepository<Message> {

    /**
     * Recupera los mensajes de un chat ordenados por sent_at.
     */
    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId ORDER BY m.sent_at ASC")
    List<Message> findByChatId(@Param("chatId") Long chatId);

}
