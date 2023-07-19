package com.javafx_project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button appointmentButton;

    @FXML
    private Button customerButton;

    @FXML
    private Button userButton;

    @FXML
    private Button reportButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void appointmentButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/com/javafx_project/appointmentView.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) appointmentButton.getScene().getWindow();

        // Create new scene and set it on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    protected void customerButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/javafx_project/customerView.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) customerButton.getScene().getWindow();

        // Create new scene and set it on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    protected void userButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/javafx_project/userView.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) userButton.getScene().getWindow();

        // Create new scene and set it on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    protected void reportButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/javafx_project/reportView.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) reportButton.getScene().getWindow();

        // Create new scene and set it on the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
}