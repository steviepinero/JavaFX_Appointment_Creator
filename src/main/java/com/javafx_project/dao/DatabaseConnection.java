package com.javafx_project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The type Database connection.
 */
public class DatabaseConnection {
    /**
     * The constant connection.
     */
    public static Connection connection;
    /**
     * The constant preparedStatement.
     */
    public static PreparedStatement preparedStatement;

    /**
     * Establish connection connection.
     *
     * @return the connection
     */
    public static Connection establishConnection() {

        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/appointments";
                String username = "admin";
                String password = "admin";
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                // Handle any errors
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public static Connection getConnection() {
//        establishConnection();
        return connection;
    }
}
