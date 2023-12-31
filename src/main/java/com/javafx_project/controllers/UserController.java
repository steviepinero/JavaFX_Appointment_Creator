package com.javafx_project.controllers;

import com.javafx_project.dao.AppointmentDAO;
import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Country;
import com.javafx_project.models.FirstLevelDivision;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.dao.DatabaseConnection.connection;

/**
 * The type User controller.
 */
public class UserController implements Initializable {
    /**
     * The User list.
     */
    static ObservableList<User> userList = UserDAO.getAllUsers();
    /**
     * The Appointment list.
     */
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
    private TableColumn<User, Timestamp> createDateColumn;
    @FXML
    private TableColumn<User, String> createdByColumn;
    @FXML
    private TableColumn<User, Timestamp> lastUpdateColumn;
    @FXML
    private TableColumn<User, String> lastUpdatedByColumn;


    @FXML
    private TextField passwordField;
    @FXML
    private TextField userNameField;


    /**
     * Instantiates a new User controller.
     */
//constructor
    public UserController() {
    }

    /**
     * Add user.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void addUser(ActionEvent actionEvent) {
        String userName, password, createdBy, lastUpdatedBy;
        userName = userNameField.getText();
        password = passwordField.getText();
        createdBy = String.valueOf(LoginController.loggedInUser.getUser_Name());;
        lastUpdatedBy = createdBy;
        DatabaseConnection.establishConnection();

        try {
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

            userTable.setItems(UserDAO.getAllUsers());
            userTable.refresh();


        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
     }
    }

    /**
     * Sets table.
     */
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

    /**
     * Update user.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void updateUser(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        String userName, password, lastUpdatedBy;
        int userId;
        userName = userNameField.getText();
        password = passwordField.getText();
        //get the username of the logged in user
        lastUpdatedBy = String.valueOf(LoginController.loggedInUser.getUser_Name());
        userId = userTable.getSelectionModel().getSelectedItem().getUser_ID();

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
            userTable.setItems(UserDAO.getAllUsers());
            userTable.refresh();

            userNameField.setText("");
            passwordField.setText("");

        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Delete user.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void deleteUser(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        selectedUser = userTable.getSelectionModel().getSelectedItem();

        // Check if the user is the default admi
        if (selectedUser.getUser_ID() == 1) {
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
        } else {
            /** Check if user has any upcoming appointments */
            for (Appointment appointment : appointmentList) {
                if (appointment.getUser_ID() == selectedUser.getUser_ID()) {
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
                UserDAO.deleteUser(selectedUser.getUser_ID());

                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Success");
                alertSuccess.setHeaderText("User deleted");
                alertSuccess.setContentText("User" + selectedUser.getUser_Name() + "deleted successfully");
                alertSuccess.showAndWait();

                //console message verifying delete
                System.out.println("User" + selectedUser.getUser_Name() + "deleted");
                //reload table and remove deleted user from fxml table
                userTable.setItems(UserDAO.getAllUsers());
                userTable.refresh();
            }
        }


    }

    /**
     * Back button action.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    @FXML
    private void handleTableRowClick() {
        // Get the selected appointment from the table
        User selectedUser = userTable.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            // Populate the fields with the selected Uszer's data
            userNameField.setText(selectedUser.getUser_Name());
            passwordField.setText(selectedUser.getPassword());


        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setTable();
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleTableRowClick();
            }
        });
    }
}
