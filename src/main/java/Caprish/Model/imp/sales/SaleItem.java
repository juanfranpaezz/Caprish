package Caprish.Model.imp.sales;

public class SaleItem {


    String sql =    "CREATE TABLE sale_item (\n" +
                    "  id_sale_item  BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  id_sale       BIGINT NOT NULL,\n" +
                    "  id_product    BIGINT NOT NULL,\n" +
                    "  quantity      INT NOT NULL CHECK (quantity > 0),\n" +
                    "  unit_price    DECIMAL(10,2) NOT NULL CHECK (unit_price >= 0),\n" +
                    "  FOREIGN KEY (id_sale)    REFERENCES sale(id_sale) ON DELETE CASCADE,\n" +
                    "  FOREIGN KEY (id_product) REFERENCES products(id_product)\n" +
                    ");";


}
