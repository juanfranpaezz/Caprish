package Caprish.Service.imp.messaging;

import Caprish.Model.imp.messaging.Chat;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Service.imp.MyObjectGenericService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatService extends MyObjectGenericService<Chat, ChatRepository, ChatService> {
    protected ChatService(ChatRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected void verifySpecificAttributes(Chat entity) {}


    public Chat findByBusinessIdAndClientId(Long businessId, Long clientId){
        return repository.findByBusinessIdAndClientId(businessId, clientId);
    }

}