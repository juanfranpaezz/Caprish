package Caprish.Model.imp.users;

public class Staff extends User {

    String sql = "CREATE TABLE staff (\n" +
            "    id_staff INT PRIMARY KEY AUTO_INCREMENT,\n" +
            "    first_name VARCHAR(50) NOT NULL,\n" +
            "    last_name VARCHAR(50) NOT NULL,\n" +
            "    email VARCHAR(100) NOT NULL UNIQUE,\n" +
            "    pass VARCHAR(255) NOT NULL,\n" +
            "    work_role ENUM('supervisor', 'employee'),\n" +
            "    id_business INT,    \n" +
            "    FOREIGN KEY (id_business) REFERENCES empresas(id_business)\n" +
            ");";


    /*tiene  Enum workrole*/
}
