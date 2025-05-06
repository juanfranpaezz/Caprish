//package Caprish.Model.agregados;
//
//public class Chat {
//
//    /*CREATE TABLE chat (
//  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
//  company_id   BIGINT NOT NULL,
//  client_id    BIGINT NOT NULL,
//  created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//  CONSTRAINT fk_chat_company FOREIGN KEY (company_id) REFERENCES company(id),
//  CONSTRAINT fk_chat_client  FOREIGN KEY (client_id)  REFERENCES client(id),
//  UNIQUE (company_id, client_id)
//);
//*/
//}
//
///*SEGUN CHATG:
//@Entity
//@Table(name = "chat",
//       uniqueConstraints = @UniqueConstraint(columnNames = {"company_id","client_id"}))
//public class Chat {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "company_id", nullable = false)
//    private Company company;
//
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "client_id",  nullable = false)
//    private Client client;
//
//    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Message> messages = new ArrayList<>();
//
//    // getters y setters
//}
//
// */