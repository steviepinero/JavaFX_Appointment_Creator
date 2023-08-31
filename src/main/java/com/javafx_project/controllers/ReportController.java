package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportController {

    /**  the total number of customer appointments by type and month

     •  a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

     •  an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C
     */

    @FXML
    private TableView<Map.Entry<String, Map<String, Integer>>> reportTable1;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> monthColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> typeColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> countColumn;

    @FXML
    private TableView<Appointment> reportTable2;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private Button backButton;

    //report that tracks the total number of customer appointments by type and month
    public Map<String, Map<String, Integer>> getAppointmentsByTypeAndMonth() {
        String query = "SELECT MONTHNAME(Start) as Month, Type, COUNT(*) as Count FROM appointments GROUP BY MONTH(Start), Type";
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int count = rs.getInt("Count");

                resultMap.computeIfAbsent(month, k -> new HashMap<>()).put(type, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    //report/schedule that tracks the total number of appointments by contact
    public Map<Contact, List<Appointment>> getScheduleForContacts() {
        String query = "SELECT * FROM appointments ORDER BY Contact_ID";
        Map<Contact, List<Appointment>> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Contact contact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"));
                Appointment appointment = new Appointment(
                        rs.getInt("appointment_ID"),
                        rs.getString("title"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getTimestamp("start").toLocalDateTime(),
                        rs.getTimestamp("end").toLocalDateTime(),
                        rs.getInt("customer_ID")
                );

                resultMap.computeIfAbsent(contact, k -> new ArrayList<>()).add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }


    // report that tracks the total number of updates made by user
    public void userUpdateReport() {
        // report that tracks the number of updates made by each user
        // TODO implement this method
    }

    //displays the report that tracks the total number of customer appointments by type and month
    public void customerAppointmentReport() {
        Map<String, Map<String, Integer>> data = getAppointmentsByTypeAndMonth();

        monthColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        typeColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().keySet().toString()));
        countColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().values().stream().mapToInt(Integer::intValue).sum()).asObject());

        ObservableList<Map.Entry<String, Map<String, Integer>>> items = FXCollections.observableArrayList(data.entrySet());
        reportTable1.setItems(items);
    }

    //displays the report that tracks the total number of appointments by contact
    public void contactAppointmentReport(Contact contact) {
        Map<Contact, List<Appointment>> data = getScheduleForContacts();

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        ObservableList<Appointment> items = FXCollections.observableArrayList(data.get(contact));
        reportTable2.setItems(items);
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
