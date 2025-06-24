package Caprish.Model.imp.sales;
import Caprish.Model.imp.MyObject;
import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Staff;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cart_type", nullable = false)
    private CartType cart_type;

    @ManyToOne(optional = false)
    @JsonBackReference("cart-client")
    @JoinColumn(name = "id_client",nullable = false)
    private Client client;



    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cart_status",nullable = false)
    private CartStatus cart_status;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("item-cart")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name="id_staff", nullable = true)
    @JsonBackReference("cart-staff")
    private Staff staff;

    @Column(nullable = false)
    private LocalDate sale_date;

}
