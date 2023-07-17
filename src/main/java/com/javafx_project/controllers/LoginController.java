package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.dao.UserDAO;
import com.javafx_project.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends Pane {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public LoginController(TextField usernameField, PasswordField passwordField, Button loginButton) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginButton = loginButton;
    }

    public LoginController() {
        // No-argument constructor
    }

    @FXML
    public void initialize() {
        //TODO move button action to fxml file
/*
        loginButton.setOnAction(e -> login());
*/
    }

    @FXML
    private void login(ActionEvent event) throws SQLException {
        // Establish connection
        DatabaseConnection.establishConnection();

        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(enteredUsername);
        System.out.print("~~~~~~~~~~~~~login method~~~~~~~~~~~~");



        if (user != null && user.getPassword().equals(enteredPassword)) {
            // login successful
            // navigate to the next scene.
            try {
                FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/javafx_project/homeView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) loginButton.getScene().getWindow();

                // Create new scene and set it on the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // login failed
            // show an error message.
            showLoginError();
        }
    }

    public void showLoginError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText("Incorrect username or password.");

        alert.showAndWait();
    }

}
