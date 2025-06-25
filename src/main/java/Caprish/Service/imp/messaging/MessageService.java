package Caprish.Service.imp.messaging;

import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService extends MyObjectGenericService<Message, MessageRepository, MessageService> {

    protected MessageService(MessageRepository repository) {
        super(repository);
    }

    @Override
    protected void verifySpecificAttributes(Message entity) {
    }
}
