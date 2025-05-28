package Caprish.Model.imp.users;

import Caprish.Model.imp.messaging.Chat;
import Caprish.Model.imp.sales.Cart;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client extends User {

    @Column(unique = true, nullable = false)
    private Integer phone;

    @Column(unique = true, nullable = false)
    private String tax;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference("chat-client")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference("cart-client")
    private List<Cart> carts = new ArrayList<>();


    public Client(String email, String password_hash) {
        super(email, password_hash);
    }
}
