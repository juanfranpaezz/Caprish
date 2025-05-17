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
    @JoinColumn(name = "id",nullable = false)
    private CartType cartType;

    @OneToOne(optional = false)
    @JoinColumn(name = "id",nullable = false)
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "cart_voucher",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "voucher_id")
    )
    private List<Voucher> vouchers;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id",nullable = false)
    private CartStatus cartStatus;

    public Cart(CartType CartType, Client client, List<Voucher> vouchers, CartStatus cartStatus) {
        this.cartType = CartType;
        this.client = client;
        this.vouchers = vouchers;
        this.cartStatus = cartStatus;
    }

}



