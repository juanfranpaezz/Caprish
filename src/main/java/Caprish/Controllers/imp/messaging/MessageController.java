package Caprish.Controllers.imp.messaging;

import Caprish.Controllers.MyObjectController;
import Caprish.Model.imp.messaging.Message;
import Caprish.Repository.interfaces.messaging.MessageRepository;
import Caprish.Service.imp.messaging.MessageService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message")
public class MessageController extends MyObjectController<Message, MessageRepository, MessageService> {

    public MessageController(MessageService service) {
        super(service);
    }

}