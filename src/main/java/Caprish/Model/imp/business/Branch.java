package Caprish.Model.imp.business;

import Caprish.Model.imp.MyObjects;

public class Branch extends MyObjects {

    String sql =    "CREATE TABLE branch (\n" +
                    "  id_branch BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  id_business BIGINT NOT NULL,\n" +
                    "  address VARCHAR(200) NOT NULL,\n" +
                    "  branch_type ENUM(SALES_POINT, WAREHOUSE) NOT NULL," +
                    "  FOREIGN KEY (id_business)\n" +
                    "  REFERENCES business(id_business)\n" +
                    "  ON DELETE CASCADE\n" +
                    ");";
}