package Caprish.Model.imp.messaging;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Business;
import Caprish.Model.imp.users.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat",
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_business","id_client"}))
public class Chat extends MyObject {
    @ManyToOne(optional = false)
    @JsonBackReference("chat-business")
    @JoinColumn(name="id_business")
    private Business business;

    @ManyToOne(optional = false)
    @JsonBackReference("chat-client")
    @JoinColumn(name="id_client")
    private Client client;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("message-chat")
    private List<Message> messages = new ArrayList<>();
}


