package Caprish.Service.imp.messaging;

import Caprish.Model.imp.messaging.Chat;
import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.messaging.ChatRepository;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class ChatService extends MyObjectGenericService<Chat, ChatRepository> {
    protected ChatService(ChatRepository childRepository) {
        super(childRepository);
    }

    @Override
    protected Class<Chat> getEntityClass() {
        return Chat.class;
    }
}