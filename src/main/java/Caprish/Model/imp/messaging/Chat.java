package Caprish.Model.imp.messaging;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Client;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "chat",
       uniqueConstraints = @UniqueConstraint(columnNames = {"company_id","client_id"}))
public class Chat extends MyObject {
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_business", nullable = false)
    private Business business;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_client",  nullable = false)
    private Client client;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

}

