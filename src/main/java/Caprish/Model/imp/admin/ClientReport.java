package Caprish.Model.imp.admin;

public class ClientReport {
    String sql =    "CREATE TABLE client_report (\n" +
                    "  id_client_report BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  id_sale BIGINT,\n" +
                    "  generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  report_data TEXT,\n" +
                    "  FOREIGN KEY (id_sale)\n" +
                    "    REFERENCES sale(id_sale)\n" +
                    "    ON DELETE SET NULL\n" +
                    ");";
}
