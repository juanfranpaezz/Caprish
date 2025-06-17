package Caprish.Scheduler;

import Caprish.Repository.interfaces.mail.EmailTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class TokenCleanUpTask {

    private final EmailTokenRepository repo;

    public TokenCleanUpTask(EmailTokenRepository repo) {
        this.repo = repo;
    }
    @Transactional
    @Scheduled(fixedRate = 300_000)
    public void cleanupExpiredTokens() {
        int deletedCount = repo.deleteByExpirationBefore(LocalDateTime.now());
        System.out.println("Tokens expirados eliminados: " + deletedCount);
    }
}
