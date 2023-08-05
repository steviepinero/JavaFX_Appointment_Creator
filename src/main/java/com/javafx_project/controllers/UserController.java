package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.dao.DatabaseConnection.connection;

public class UserController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<User, String> createDateColumn;

    @FXML
    private Button createUserButton;

    @FXML
    private TableColumn<User, String> createdByColumn;

    @FXML
    private Button deleteUserButton;

    @FXML
    private TableColumn<User, String> lastUpdateColumn;

    @FXML
    private TableColumn<User, String> lastUpdatedByColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TextField passwordField;

    @FXML
    private Button updateUserButton;

    @FXML
    private TableColumn<User, String> userIdColumn;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TextField userNameField;

    @FXML
    private TableView<User> userTable;


    //constructor
    public UserController() {
    }

    @FXML
    public void addCustomer(ActionEvent actionEvent) {
        String userName, password, createdBy, lastUpdatedBy;
        userName = userNameField.getText();
        password = passwordField.getText();
        createdBy = "test";
        lastUpdatedBy = "test";

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
            
        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
     }
    }
    
    public void setTable() {
        Connection conn = DatabaseConnection.connection;
        ObservableList<User> users = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("User_ID"));
                user.setUserName(rs.getString("User_Name"));
                user.setPassword(rs.getString("Password"));
                user.setCreateDate(rs.getString("Create_Date"));
                user.setCreatedBy(rs.getString("Created_By"));
                user.setLastUpdate(rs.getString("Last_Update"));
                user.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                userTable.getItems().add(user);
            }


            userTable.setItems(users);

        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }

        userTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    User rowData = row.getItem();
                    userNameField.setText(rowData.getUserName());
                    passwordField.setText(rowData.getPassword());
                }
            });
            return row;
        });
    }

    @FXML
    public void updateCustomer(ActionEvent actionEvent) {
        String userName, password, lastUpdatedBy;
        int userId;
        userName = userNameField.getText();
        password = passwordField.getText();
        lastUpdatedBy = "test";
        userId = userTable.getSelectionModel().getSelectedItem().getUserId();

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

            userNameField.setText("");
            passwordField.setText("");

        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void deleteCustomer(ActionEvent actionEvent) {
        int userId;
        userId = userTable.getSelectionModel().getSelectedItem().getUserId();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE User_ID = ?");
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Deleted");
            alert.setHeaderText("User Deleted");
            alert.setContentText("User Deleted Successfully");
            alert.showAndWait();

            setTable();

            userNameField.setText("");
            passwordField.setText("");

        } catch (SQLException e) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
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
