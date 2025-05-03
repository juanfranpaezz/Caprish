package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;

public class Stock extends MyObjects {
    String sql =    "CREATE TABLE stock_history (\n" +
                    "  id_stock BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  id_product BIGINT NOT NULL,\n" +
                    "  id_branch BIGINT NOT NULL,\n" +
                    "  quantity INT NOT NULL,\n" +
                    "    FOREIGN KEY (id_product)\n" +
                    "    REFERENCES stock(id_product)\n" +
                    "    FOREIGN KEY (id_branch)\n" +
                    "    REFERENCES branch(id_branch)\n" +
                    "    ON DELETE CASCADE\n" +
                    ");\n";


}
