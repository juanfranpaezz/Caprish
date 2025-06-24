package Caprish.Scheduler;

import Caprish.Repository.interfaces.mail.EmailTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.time.LocalDateTime;

@Component
@EnableScheduling
public class TokenCleanUpTask {

    private final EmailTokenRepository repo;

//    private boolean dbCreated = false;
//
//    private final String urlWithDB = "jdbc:mysql://localhost:3306/caprishdb";
//    private final String urlNoDB = "jdbc:mysql://localhost:3306/";
//    private final String user = "root";
//    private final String pass = "root";

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
