package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import com.javafx_project.models.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ReportController implements Initializable {

    /**  the total number of customer appointments by type and month

     •  a schedule for each contact in your organization that includes appointment ID, title, type and description, start date and time, end date and time, and customer ID

     •  an additional report of your choice that is different from the two other required reports in this prompt and from the user log-in date and time stamp that will be tracked in part C
     */

    @FXML
    private TableView<Map.Entry<String, Map<String, Integer>>> customerReportTable;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> appointmentMonthColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, String> typeColumn;
    @FXML
    private TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> appointmentCountColumn;

    @FXML
    private TableView<Appointment> contactReportTable;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TableView<User> userReportTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, Integer> updateCountColumn;

    @FXML
    private Button backButton;

    @FXML
    private TabPane reportTabPane;

    @FXML
    private Tab customerReportTab;

    @FXML
    private Tab contactReportTab;

    @FXML
    private Tab userReportTab;
    private TableColumn<Object, Object> customerNameColumn;

    //report that tracks the total number of customer appointments by type and month
    public Map<String, Map<String, Integer>> getAppointmentsByTypeAndMonth() {
        DatabaseConnection.establishConnection();

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
        DatabaseConnection.establishConnection();

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
        DatabaseConnection.establishConnection();

        // report that tracks the number of updates made by each user

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        updateCountColumn.setCellValueFactory(new PropertyValueFactory<>("Update_Count"));



        // TODO implement this method
    }

    //displays the report that tracks the total number of customer appointments by type and month
    public void customerAppointmentReport() {
        DatabaseConnection.establishConnection();

        Map<String, Map<String, Integer>> data = getAppointmentsByTypeAndMonth();

        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        appointmentMonthColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
        typeColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue().keySet().toString()));
        appointmentCountColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getValue().values().stream().mapToInt(Integer::intValue).sum()).asObject());

        ObservableList<Map.Entry<String, Map<String, Integer>>> items = FXCollections.observableArrayList(data.entrySet());
        customerReportTable.setItems(items);
    }

    //displays the report that tracks the total number of appointments by contact
    public void contactAppointmentReport() {
        DatabaseConnection.establishConnection();

        Map<Contact, List<Appointment>> data = getScheduleForContacts();

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        Contact contact = new Contact();
        ObservableList<Appointment> items = FXCollections.observableArrayList(data.get(contact));
        contactReportTable.setItems(items);
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

        // Set up tab selection listeners
        customerReportTab.setOnSelectionChanged(event -> {
            if (customerReportTab.isSelected()) {
                customerAppointmentReport();
            }
        });

        contactReportTab.setOnSelectionChanged(event -> {
            if (contactReportTab.isSelected()) {
                contactAppointmentReport();
            }
        });

        userReportTab.setOnSelectionChanged(event -> {
            if (userReportTab.isSelected()) {
                userUpdateReport();
            }
        });
    }
}
