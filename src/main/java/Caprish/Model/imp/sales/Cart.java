package Caprish.Model.imp.sales;
import Caprish.Model.imp.MyObject;
import Caprish.Model.imp.business.Voucher;
import Caprish.Model.enums.CartStatus;
import Caprish.Model.enums.CartType;
import Caprish.Model.imp.users.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="cart")
@NoArgsConstructor
@Getter
@Setter
public class Cart extends MyObject {

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cart_type",nullable = false)
    private CartType cart_type;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_client",nullable = false)
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "cart_voucher",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    private List<Voucher> vouchers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cart_status",nullable = false)
    private CartStatus cart_status;

    public Cart(CartType cart_type, Client client, List<Voucher> vouchers, CartStatus cart_status) {
        this.cart_type = cart_type;
        this.client = client;
        this.vouchers = vouchers;
        this.cart_status = cart_status;
    }

}



