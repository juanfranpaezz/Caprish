package Caprish.Model.imp.users;

import Caprish.Model.imp.MyObjects;

public abstract class User extends MyObjects {

    String sql =    "  CREATE TABLE users (" +
                    "   id_users BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "   email VARCHAR(150) UNIQUE NOT NULL," +
                    "   password_hash VARCHAR(255) NOT NULL," +
                    "   " +
                    ");";

    /*padre de: admin, boss, client, employee, */
}
