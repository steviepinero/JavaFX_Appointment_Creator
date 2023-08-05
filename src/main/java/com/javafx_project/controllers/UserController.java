package com.javafx_project.controllers;

import com.javafx_project.dao.AppointmentDAO;
import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.User;
import com.javafx_project.dao.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.dao.DatabaseConnection.connection;

public class UserController implements Initializable {
    static ObservableList<User> userList = UserDAO.getAllUsers();
    static ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments();
    private static User selectedUser;

    /** User table */
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> userIdColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, String> createDateColumn;
    @FXML
    private TableColumn<User, String> createdByColumn;
    @FXML
    private TableColumn<User, String> lastUpdateColumn;
    @FXML
    private TableColumn<User, String> lastUpdatedByColumn;


    @FXML
    private Button backButton;
    @FXML
    private Button createUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button updateUserButton;


    @FXML
    private TextField passwordField;
    @FXML
    private TextField userNameField;




    //constructor
    public UserController() {
    }

    @FXML
    public void addUser(ActionEvent actionEvent) {
        String userName, password, createdBy, lastUpdatedBy;
        userName = userNameField.getText();
        password = passwordField.getText();
        createdBy = "test";
        lastUpdatedBy = "test";

        try {
            DatabaseConnection.establishConnection();
           PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (User_Name, password, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (?, ?, NOW(), ?, NOW(), ?)");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, createdBy);
            preparedStatement.setString(4, lastUpdatedBy);
            preparedStatement.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Added");
            alert.setHeaderText("User Added");
            alert.setContentText("User Added Successfully");
            alert.showAndWait();
            
            setTable();
            
            userNameField.setText("");
            passwordField.setText("");

            userTable.refresh();
            
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
     }
    }
    
    public void setTable() {
        // Set the user table
        userTable.setItems(userList);
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("Password"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("Create_Date"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));

    }

    @FXML
    public void updateUser(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        String userName, password, lastUpdatedBy;
        int userId;
        userName = userNameField.getText();
        password = passwordField.getText();
        lastUpdatedBy = "test";
        userId = userTable.getSelectionModel().getSelectedItem().getUser_Id();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET User_Name = ?, Password = ?, Last_Update = NOW(), Last_Updated_By = ? WHERE User_ID = ?");
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, lastUpdatedBy);
            preparedStatement.setInt(4, userId);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Updated");
            alert.setHeaderText("User Updated");
            alert.setContentText("User Updated Successfully");
            alert.showAndWait();

            setTable();
            userTable.refresh();

            userNameField.setText("");
            passwordField.setText("");

        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void deleteUser(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        selectedUser = userTable.getSelectionModel().getSelectedItem();

        // Check if the user is the default admi
        if (selectedUser.getUser_Id() == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Cannot delete the default admin user");
            alert.showAndWait();
            return;
        }
        // Chdeck if a user is selected
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a user to delete");
            alert.showAndWait();
            return;
        } else {
            /** Check if user has any upcoming appointments */
            for (Appointment appointment : appointmentList) {
                if (appointment.getUserId() == selectedUser.getUser_Id()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("Cannot delete a user with upcoming appointments");
                    alert.showAndWait();
                    return;
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete User");
            alert.setHeaderText("Delete User");
            alert.setContentText("Are you sure you want to delete this user?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                //user deleted from database
                UserDAO.deleteUser(selectedUser.getUser_Id());

                //console message verifying delete
                System.out.println("User" + selectedUser.getUser_Name() + "deleted");
                //reload table and remove deleted user from fxml table
                userTable.setItems(UserDAO.getAllUsers());
                userTable.refresh();
            }
        }


    }

    public void backButtonAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/javafx_project/homeView.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Button backButton;
        backButton = (Button) actionEvent.getSource();
        Stage stage = (Stage) backButton.getScene().getWindow();

        // Create new scene and set it on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setTable();
    }
}
