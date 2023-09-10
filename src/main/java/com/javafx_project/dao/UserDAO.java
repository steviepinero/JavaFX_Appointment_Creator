package com.javafx_project.dao;

import com.javafx_project.models.User;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.javafx_project.dao.DatabaseConnection.connection;

/**
 * The type User dao.
 */
public class UserDAO {
    /**
     * The constant getUserByUsername.
     */
    public static User getUserByUsername;
    /**
     * The constant getUserByUserId.
     */
    public static User getUserByUserId;
    private int userId;
    private String userName;

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = javafx.collections.FXCollections.observableArrayList();

        try {
            PreparedStatement loadUsers = DatabaseConnection.getConnection().prepareStatement("SELECT * FROM Users ORDER BY User_ID");
            ResultSet rs = loadUsers.executeQuery();

            while (rs.next()) {
                Integer userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lasUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                User user = new User(userId, userName, password, createDate, createdBy, lasUpdate, lastUpdatedBy);
                userList.add(user);
            }
            return userList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     * @throws SQLException the sql exception
     */
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Users WHERE User_Name = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUser_Name(rs.getString("User_Name"));
            user.setPassword(rs.getString("Password"));
            user.setUser_ID(rs.getInt("User_ID"));
            // set other fields if it becomes necessary
            return user;
        } else {
            return null;
        }
    }

    /**
     * Gets user by user id.
     *
     * @param userId the user id
     * @return the user by user id
     * @throws SQLException the sql exception
     */
    public User getUserByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM Users WHERE User_ID = ?";
        PreparedStatement ps = connection.prepareStatement(query);
         ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUser_ID(rs.getInt("User_ID"));
//                user.setUser_Name(rs.getString("User_Name"));
//                user.setPassword(rs.getString("Password"));
//                user.setCreate_Date(rs.getString("Create_Date"));
//                user.setCreated_By(rs.getString("Created_By"));
//                user.setLast_Update(rs.getString("Last_Update"));
//                user.setLast_Updated_By(rs.getString("Last_Updated_By"));
               return user;
            } else {
                return null;
            }
        }


    /**
     * Delete user.
     *
     * @param userId the user id
     */
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


    /**
     * Gets user id by username.
     *
     * @param enteredUsername the entered username
     * @return the user id by username
     * @throws SQLException the sql exception
     */
    public UserDAO getUserIDByUsername(String enteredUsername) throws SQLException {
        return getUserByUserId(userId);
    }
}
