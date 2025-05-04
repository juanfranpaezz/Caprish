package Caprish.Model.imp.sales;

public class CartItem {

    String sql =    "CREATE TABLE cart_product (\n" +
                    "  id_cart_product  BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  id_cart          BIGINT NOT NULL,\n" +
                    "  id_product       BIGINT NOT NULL,\n" +
                    "  quantity         INT NOT NULL CHECK(quantity > 0),\n" +
                    "  unit_price       DECIMAL(10,2) NOT NULL CHECK(unit_price >= 0),\n" +
                    "  FOREIGN KEY (id_cart)    REFERENCES cart(id_cart) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (id_product) REFERENCES products(id_product)\n" +
                    ");\n";
}





