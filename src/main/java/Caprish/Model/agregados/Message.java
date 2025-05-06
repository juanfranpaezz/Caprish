package Caprish.Model.agregados;

public class Message {
    /*CREATE TABLE message (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  chat_id      BIGINT NOT NULL,
  sender_type  VARCHAR(10) NOT NULL,
  sender_id    BIGINT NOT NULL,
  content      TEXT NOT NULL,
  sent_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_msg_chat FOREIGN KEY (chat_id) REFERENCES chat(id)
);
*/
}

/*SEGUN CHATG:
*
* @Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @Enumerated(EnumType.STRING)
    @Column(name = "sender_type", nullable = false, length = 10)
    private SenderType senderType;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "sent_at", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime sentAt = LocalDateTime.now();

    // getters y setters
}

*
*
* */
