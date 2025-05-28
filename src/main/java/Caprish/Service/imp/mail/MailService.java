package Caprish.Service.imp.mail;

import Caprish.Repository.interfaces.mail.MailRepository;
import Caprish.Service.imp.MyObjectGenericService;
import org.springframework.stereotype.Service;

@Service
public class MailService extends MyObjectGenericService<Mail,MailRepository,MailService> {

    private final MailRepository mailRepository;

    public MailService(MailRepository mailRepository) {
        super(mailRepository);
        this.mailRepository = mailRepository;
    }

    @Override
    protected void verifySpecificAttributes(Mail entity) {
    }

    public void addMail(String texto,String password) {
        Mail mail = new Mail(texto,password);
        mailRepository.save(mail);
    }
    public boolean existsByMail(String email) {
        return mailRepository.existsByMail(email);
    }

}
