package com.javafx_project.controllers;

import com.javafx_project.dao.UserDAO;
import com.javafx_project.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    public void initialize() {
        //TODO move button action to fxml file
        loginButton.setOnAction(e -> login());
    }

    @FXML
    private void login(ActionEvent event) throws SQLException {
        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(enteredUsername);

        if (user != null && user.getPassword().equals(enteredPassword)) {
            // login successful
            // navigate to the next scene, etc.
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("appointmentView.fxml"));
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
            // show an error message, etc.
        }
    }

}
