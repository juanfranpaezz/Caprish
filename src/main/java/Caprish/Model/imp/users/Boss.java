package Caprish.Model.imp.users;

public class Boss extends User{

    String sql =    "CREATE TABLE bosses(" +
                    "    id_boss int primary key auto_increment," +
                    "    first_name varchar(50) NOT NULL," +
                    "    last_name varchar(50) NOT NULL," +
                    "    pass varchar(255) NOT NULL," +
                    "    email varchar(50) NOT NULL UNIQUE," +
                    "    phone int NOT NULL UNIQUE" +
                    ");";



}
