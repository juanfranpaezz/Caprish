package Caprish.Model.imp.sales;

import Caprish.Model.imp.MyObjects;

public class Sale  extends MyObjects {

    String sql =    "CREATE TABLE sale (\n" +
                    "  id_sale        BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  id_cart  BIGINT NOT NULL,\n" +
                    "  sale_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  total_amount   DECIMAL(12,2) NOT NULL,\n" +
                    "  FOREIGN KEY (id_cart) REFERENCES cart(id_cart) ON DELETE RESTRICT\n" +
                    ");";
}




