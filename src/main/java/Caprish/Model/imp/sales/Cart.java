package Caprish.Model.imp.sales;

public class Cart {


    String sql =    "CREATE TABLE cart (\n" +
                    "  id_cart      BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  cart_type    ENUM('PURCHASE','SALE') NOT NULL,\n" +
                    "  id_client    BIGINT,    -- sólo para PURCHASE\n" +
                    "  id_employee  BIGINT,    -- sólo para SALE\n" +
                    "  id_voucher   BIGINT,\n" +
                    "  status       ENUM('OPEN','CONFIRMED') NOT NULL DEFAULT 'OPEN',\n" +
                    "  last_edition   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                    "  FOREIGN KEY (id_client)   REFERENCES clients(id_client),\n" +
                    "  FOREIGN KEY (id_employee) REFERENCES employees(id_employee),\n" +
                    "  FOREIGN KEY (id_voucher)  REFERENCES vouchers(id_voucher)\n" +
                    ");";

}
