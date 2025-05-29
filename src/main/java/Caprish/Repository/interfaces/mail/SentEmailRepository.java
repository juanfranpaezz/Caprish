package Caprish.Repository.interfaces.mail;

import Caprish.Model.imp.mail.SentEmail;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.List;

public interface SentEmailRepository extends MyObjectGenericRepository<SentEmail> {
    List<SentEmail> findAllByReceiverIgnoreCase(String receiver);
    List<SentEmail> findAllBySender(String sender);
}
