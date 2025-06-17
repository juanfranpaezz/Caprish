package Caprish.Service.imp.messaging;

import Caprish.Model.BeanUtils;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService extends MyObjectGenericService<Message, MessageRepository, MessageService> {

    protected MessageService(MessageRepository repository) {
        super(repository);
    }

    @Override
    protected void verifySpecificAttributes(Message entity) {
    }

    public List<Message> findByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    public Message saveA(Message entity){
        BeanUtils.verifyValues(entity);
        verifySpecificAttributes(entity);
        return repository.save(entity);
    }
}
