package Caprish.Repository.interfaces.messaging;

import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends MyObjectGenericRepository<Chat> {
}
