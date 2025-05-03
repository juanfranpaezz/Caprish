package Caprish.Model.imp.admin;

import Caprish.Model.imp.MyObjects;

public class BusinessReport extends MyObjects {
    String sql =    "CREATE TABLE business (" +
                    "id_business_report BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "id_business INT NOT NULL," +
                    "generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "CONSTRAINT id_business FOREIGN KEY (id_business)" +
                    "REFERENCES business(id_business)" +
                    "about VARCHAR(200), NOT NULL" +
                    "description TEXT" +
                    ");";
}
