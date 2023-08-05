package com.javafx_project.dao;

import com.javafx_project.models.User;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.javafx_project.dao.DatabaseConnection.connection;

public class UserDAO {
    public static User getUserByUsername;
    public static User getUserByUserId;
    private int userId;
    private String userName;

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = javafx.collections.FXCollections.observableArrayList();

        try {
            PreparedStatement loadUsers = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Users ORDER BY User_ID");
            ResultSet rs = loadUsers.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getString("Create_Date"));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getString("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                userList.add(user);
            }
            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public User getUserByUserId(int userId) {
        String query = "SELECT * FROM Users WHERE User_ID = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getString("Create_Date"));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getString("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE User_ID = ?";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        userName = String.valueOf(getUserByUsername);
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
