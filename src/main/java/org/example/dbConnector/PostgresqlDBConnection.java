package org.example.dbConnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlDBConnection {
    public static Connection dbPgConnector() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost:5433/product_data?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
            String username = "postgres";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return conn;
    }
}
