package Caprish.Service.imp.messaging;

import Caprish.Model.imp.messaging.Message;
import Caprish.Model.imp.sales.Cart;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Repository.interfaces.sales.CartRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class MessageService extends MyObjectGenericService<Message, MessageRepository, MessageService> {
    protected MessageService(MessageRepository childRepository) {
        super(childRepository);
    }

}