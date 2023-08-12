package com.javafx_project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportController {

    /**  the total number of customer appointments by type and month

     •  a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

     •  an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C
     */

    @FXML
    private Button backButton;

    public void customerAppointmentTypeReport() {
        // TODO implement this method
    }

    public void customerAppointmentMonthReport() {
        // TODO implement this method
    }

    public void contactScheduleReport() {
        // TODO implement this method
        // a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

    }

    public void userUpdateReport() {
        // report that tracks the number of updates made by each user
        // TODO implement this method
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
}
