package Caprish.Repository.interfaces.mail;

import Caprish.Model.imp.mail.EmailToken;
import Caprish.Repository.interfaces.MyObjectGenericRepository;

import java.util.Optional;

public interface EmailTokenRepository extends MyObjectGenericRepository<Mail> {
    Optional<EmailToken> findByEmail(String email);
    Optional<EmailToken> findByEmailAndToken(String email, String token);
}
