package Caprish.Model.imp.sales;




import Caprish.Model.imp.business.Voucher;
import Caprish.Model.imp.sales.enums.cart_status;
import Caprish.Model.imp.sales.enums.cart_type;
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
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_cart;

    @Enumerated(EnumType.STRING)
    private cart_type cart_type;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_client",nullable = false)
    private Client client;

    @ManyToMany
    @JoinColumn(name="id_voucher")
    private List<Voucher> vouchers;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private cart_status cart_status;

    public Cart(Long id_cart, Caprish.Model.imp.sales.enums.cart_type cart_type, Client client, List<Voucher> vouchers, Caprish.Model.imp.sales.enums.cart_status cart_status) {
        this.id_cart = id_cart;
        this.cart_type = cart_type;
        this.client = client;
        this.vouchers = vouchers;
        this.cart_status = cart_status;
    }

    //    String sql =    "CREATE TABLE cart (\n" +
//                    "  id_cart      BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
//                    "  id_client    BIGINT,    -- sólo para PURCHASE\n" +
//                    "  id_voucher   BIGINT,\n" +
//                    "  status       ENUM('OPEN','CONFIRMED') NOT NULL DEFAULT 'OPEN',\n" +
//                    "  FOREIGN KEY (id_client)   REFERENCES clients(id_client),\n" +
//                    "  FOREIGN KEY (id_voucher)  REFERENCES vouchers(id_voucher)\n" +
//                    ");";

}



