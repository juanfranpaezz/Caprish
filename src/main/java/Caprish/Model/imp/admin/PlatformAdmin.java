package Caprish.Model.imp.admin;

public class PlatformAdmin {

    String sql =    "CREATE TABLE platform_admin (\n" +
                    "  id_platform_admin          BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "  email                  VARCHAR(150) NOT NULL UNIQUE,\n" +
                    "  password_hash          VARCHAR(255) NOT NULL,\n" +
                    "  role                   ENUM('SUPERADMIN','SUPPORT') NOT NULL,\n" +
                    "  created_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    ");\n";


}
