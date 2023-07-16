package com.javafx_project.dao;

import com.javafx_project.models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.javafx_project.dao.DatabaseConnection.connection;

public class UserDAO {
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE User_Name = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUserName(rs.getString("User_Name"));
            user.setPassword(rs.getString("Password"));
            // set other fields if it becomes necessary
            return user;
        } else {
            return null;
        }
    }

}
