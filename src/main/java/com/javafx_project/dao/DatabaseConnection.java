package com.javafx_project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connection;

    public static Connection establishConnection() {

            try {
                String url = "jdbc:mysql://localhost:3306/appointments";
                String username = "admin";
                String password = "admin";
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                // Handle any errors
                e.printStackTrace();
            }
        return connection;
    }

    public static Connection getConnection() {
        establishConnection();
        return connection;
    }
}
