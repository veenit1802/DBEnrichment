package org.example.dbConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDBConnector {

    public static Connection dbConnector()
    {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/electronicEcommerce?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
            String username = "veenit";
            String password = "veenit";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return conn;
    }

}
