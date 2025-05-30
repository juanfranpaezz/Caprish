package Caprish.Service.imp.messaging;

import Caprish.Model.BeanUtils;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService extends MyObjectGenericService<Message, MessageRepository, MessageService> {

    protected MessageService(MessageRepository repository) {
        super(repository);
    }

    @Override
    protected void verifySpecificAttributes(Message entity) {
        // ninguna validación extra
    }

    /** Lista los mensajes de un chat ordenados cronológicamente */
    public List<Message> findByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    public Message saveA(Message entity){
        BeanUtils.verifyValues(entity);
        verifySpecificAttributes(entity);
        return repository.save(entity);
    }
}
