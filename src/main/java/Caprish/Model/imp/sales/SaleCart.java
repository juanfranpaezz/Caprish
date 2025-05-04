package Caprish.Model.imp.sales;


import Caprish.Model.imp.sales.enums.cart_type;
import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Staff;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cart")
@NoArgsConstructor
@Getter
@Setter
public class SaleCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_cart;

    @Enumerated(EnumType.STRING)
    private cart_type cart_type;

    @OneToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @OneToOne
    @JoinColumn(name = "id_employee")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "id_employee")
    private Staff staff;


    //SIN ENTIDAD

    String sql =    "CREATE TABLE cart (\n" +
            "  id_cart      BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
            "  cart_type    ENUM('PURCHASE','SALE') NOT NULL,\n" +
            "  id_client    BIGINT,    -- sólo para PURCHASE\n" +
            "  id_employee  BIGINT,    -- sólo para SALE\n" +
            "  id_voucher   BIGINT,\n" +
            "  status       ENUM('OPEN','CONFIRMED') NOT NULL DEFAULT 'OPEN',\n" +
            "  FOREIGN KEY (id_client)   REFERENCES clients(id_client),\n" +
            "  FOREIGN KEY (id_employee) REFERENCES employees(id_employee),\n" +
            "  FOREIGN KEY (id_voucher)  REFERENCES vouchers(id_voucher)\n" +
            ");";

}