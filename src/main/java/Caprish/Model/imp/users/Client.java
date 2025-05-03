package Caprish.Model.imp.users;

public class Client extends User {

    String sql =    "CREATE TABLE clients (" +
                    "    id_client INT PRIMARY KEY AUTO_INCREMENT," +
                    "    first_name VARCHAR(50) NOT NULL," +
                    "    pass VARCHAR(255) NOT NULL," +
                    "    last_name VARCHAR(50) NOT NULL," +
                    "    email VARCHAR(100) NOT NULL UNIQUE," +
                    "    phone int NOT NULL UNIQUE" +
                    ");";



}
