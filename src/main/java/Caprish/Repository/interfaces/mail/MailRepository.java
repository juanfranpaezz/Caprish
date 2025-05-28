package Caprish.Repository.interfaces.mail;

import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface MailRepository extends MyObjectGenericRepository<Mail> {
    boolean existsByMail(String email);
    boolean findByPassword(String password);
    Optional<Mail> findByMailAndPassword(String mail, String password);
}
