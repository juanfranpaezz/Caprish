package Caprish.Service.imp.mail;


import Caprish.Model.imp.mail.SentEmail;
import Caprish.Repository.interfaces.mail.SentEmailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SentEmailService {
    private final SentEmailRepository sentEmailRepository;

    public SentEmailService(SentEmailRepository sentEmailRepository) {
        this.sentEmailRepository = sentEmailRepository;
    }
    /** se explican solo los metodos no rompan los kinotos**/
    public List<SentEmail> findAllByReceiver(String receiver) {
        return sentEmailRepository.findAllByReceiverIgnoreCase(receiver);
    }
    public List<SentEmail> findAllBySender(String sender) {
        return sentEmailRepository.findAllBySender(sender);
    }
}
