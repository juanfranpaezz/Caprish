package Caprish.Repository.interfaces.messaging;

import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.MyObjectGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MessageRepository extends MyObjectGenericRepository<Message> {
}
