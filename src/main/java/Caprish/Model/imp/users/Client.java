package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.messaging.Chat;
import Caprish.Model.imp.sales.Cart;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Client extends MyObject {

    @OneToOne(optional = false)
    @JoinColumn(name = "id_credential", nullable = false, unique = true)
    private Credential credential;

    @Column(unique = true, nullable = false)
    private Integer phone;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String tax;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference("chat-client")
    private List<Chat> chats = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    @JsonManagedReference("cart-client")
    private List<Cart> carts = new ArrayList<>();
}
