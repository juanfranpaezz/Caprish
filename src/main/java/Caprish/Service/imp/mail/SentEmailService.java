package Caprish.Service.imp.mail;


import Caprish.Model.imp.mail.SentEmail;
import Caprish.Repository.interfaces.mail.SentEmailRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentEmailService extends MyObjectGenericService<SentEmail,SentEmailRepository,SentEmailService> {
    private final SentEmailRepository sentEmailRepository;

    public SentEmailService(SentEmailRepository sentEmailRepository) {
        super(sentEmailRepository);
        this.sentEmailRepository = sentEmailRepository;
    }

    @Override
    protected void verifySpecificAttributes(SentEmail entity) {
    }

    public List<SentEmail> findAllByReceiver(String receiver) {
        return sentEmailRepository.findAllByReceiverIgnoreCase(receiver);
    }
    public List<SentEmail> findAllBySender(String sender) {
        return sentEmailRepository.findAllBySender(sender);
    }
}
