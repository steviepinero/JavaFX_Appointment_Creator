package com.javafx_project.controllers;

import com.javafx_project.dao.ContactDAO;
import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.dao.UserDAO;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import com.javafx_project.models.Customer;
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
    public TableColumn customerAppointmentTypeColumn;
    @FXML
    public TableColumn typeColumn;
    @FXML
    public TableColumn typeCountColumn;

    @FXML
    public TableView<AppointmentReportEntry> customerReportTable;
    @FXML
    public TableColumn<Map.Entry<String, Map<String, Integer>>, String> appointmentMonthColumn;
    @FXML
    public TableColumn<Map.Entry<String, Map<String, Integer>>, String> appointmentTypeColumn;
    @FXML
    public TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> appointmentCountColumn;



    @FXML
    public TableView<Appointment> contactReportTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIdColumn;

    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> monthColumn;
    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> startColumn;
    @FXML
    public TableColumn<Appointment, String> endColumn;
    @FXML
    public TableColumn<Appointment, Integer> customerIDColumn;



    @FXML
    public TableView<User> userReportTable;
    @FXML
    public TableColumn<User, String> usernameColumn;
    @FXML
    public TableColumn<User, Integer> updateCountColumn;

    @FXML
    public Button backButton;

    @FXML
    public TabPane reportTabPane;

    @FXML
    public Tab customerReportTab;

    @FXML
    public Tab contactReportTab;

    @FXML
    public Tab userReportTab;
    @FXML
    public TableColumn<Customer, String> customerNameColumn;

    @FXML
    public TableColumn<AppointmentReportEntry, String> reportMonthColumn;


    public static ObservableList<User> userList = UserDAO.getAllUsers();
    public static com.javafx_project.dao.AppointmentDAO AppointmentDAO;
    static ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments();
    static ObservableList<Contact> contactList = ContactDAO.getAllContacts();

    public ReportController() {
    }

    //report that displays the total number of customer appointments by month
    public Map<String, Integer> getAppointmentsByMonth() {
        DatabaseConnection.establishConnection();

        String query = "SELECT MONTHNAME(Start) as Month, COUNT(*) as Count FROM appointments GROUP BY MONTHNAME(Start)";

        Map<String, Integer> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String month = rs.getString("Month");
                int count = rs.getInt("Count");
                resultMap.put(month, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public Map<String, Integer> getAppointmentsByType() {
        DatabaseConnection.establishConnection();

        String query = "SELECT Type, COUNT(*) as Count FROM appointments GROUP BY Type";
        Map<String, Integer> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String type = rs.getString("Type");
                int count = rs.getInt("Count");
                resultMap.put(type, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }



    //report that tracks the total number of customer appointments by type and month
    public Map<String, Map<String, Integer>> getAppointmentsByTypeAndMonth() {
        Map<String, Integer> appointmentsByMonth = getAppointmentsByMonth();
        Map<String, Integer> appointmentsByType = getAppointmentsByType();

        // Initialize the combined map with months and set all appointment types to zero
        Map<String, Map<String, Integer>> combinedMap = new HashMap<>();
        for (String month : appointmentsByMonth.keySet()) {
            Map<String, Integer> typeMap = new HashMap<>();
            for (String type : appointmentsByType.keySet()) {
                typeMap.put(type, 0);
            }
            combinedMap.put(month, typeMap);
        }
        // TODO: Fetch the combined data from the database (appointments by type and month)

        Map<String, Map<String, Integer>> fetchedData = fetchAppointmentsByTypeAndMonthFromDB();

        // Update the combined map with the fetched data
        for (Map.Entry<String, Map<String, Integer>> monthEntry : fetchedData.entrySet()) {
            String month = monthEntry.getKey();
            Map<String, Integer> typeCounts = monthEntry.getValue();
            for (Map.Entry<String, Integer> typeEntry : typeCounts.entrySet()) {
                String type = typeEntry.getKey();
                int count = typeEntry.getValue();
                combinedMap.get(month).put(type, count);
            }
        }
        return combinedMap;
    }

    private Map<String, Map<String, Integer>> fetchAppointmentsByTypeAndMonthFromDB() {
        DatabaseConnection.establishConnection();

        String query = "SELECT MONTHNAME(Start) as Month, Type, COUNT(*) as Count FROM appointments GROUP BY MONTHNAME(Start), Type";
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int count = rs.getInt("Count");

                resultMap
                        .computeIfAbsent(month, k -> new HashMap<>())
                        .put(type, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // maybe close the database connection here
        }

        return resultMap;
    }



    //report/schedule that tracks the total number of appointments by contact
    public Map<Contact, List<Appointment>> getScheduleForContacts() {
        DatabaseConnection.establishConnection();

        String query = "SELECT * FROM appointments JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID ORDER BY contacts.Contact_ID";
        Map<Contact, List<Appointment>> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Contact contact = new Contact(rs.getInt("Contact_ID"), rs.getString("Contact_Name"));

                // Check if the contact is already in the map
                resultMap.putIfAbsent(contact, new ArrayList<>());

                Appointment appointment = new Appointment(
                        rs.getInt("appointment_ID"),
                        rs.getString("title"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getTimestamp("start").toLocalDateTime(),
                        rs.getTimestamp("end").toLocalDateTime(),
                        rs.getInt("customer_ID")
                );

                resultMap.get(contact).add(appointment);
                //populate the table view
                ObservableList<Appointment> items = FXCollections.observableArrayList(resultMap.get(contact));
                contactReportTable.setItems(items);

            }
        } catch (SQLException e) {
            // TODO Handle the exception in a more user-friendly way
            e.printStackTrace();
        }
        return resultMap;
    }



    // report that tracks the total number of updates made by user
    public void userUpdateReport() {
        DatabaseConnection.establishConnection();

        // Set cell value factories
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        updateCountColumn.setCellValueFactory(new PropertyValueFactory<>("Update_Count"));

        // Fetch data and convert to list of User objects
        Map<String, Integer> userUpdates = fetchUserUpdates();
        List<User> userList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : userUpdates.entrySet()) {
            User user = new User();
            user.setUser_Name(entry.getKey());
            user.setUpdate_Count(entry.getValue());
            userList.add(user);
        }

        // Populate the TableView
        ObservableList<User> items = FXCollections.observableArrayList(userList);
        userReportTable.setItems(items);
    }


    //fetches the number of updates made by each user
    public Map<String, Integer> fetchUserUpdates() {
        DatabaseConnection.establishConnection();

        String query = "SELECT User_Name, COUNT(*) as Update_Count FROM users JOIN appointments ON users.User_ID = appointments.User_ID GROUP BY User_Name";
        Map<String, Integer> resultMap = new HashMap<>();

        try {
            Statement stmt = DatabaseConnection.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String username = rs.getString("User_Name");
                int count = rs.getInt("Update_Count");
                resultMap.put(username, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }



    //displays the report that tracks the total number of customer appointments by type and month
    public void customerAppointmentReport() {
        DatabaseConnection.establishConnection();

        appointmentMonthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        appointmentCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));


        Map<String, Map<String, Integer>> data = getAppointmentsByTypeAndMonth();

        List<AppointmentReportEntry> reportEntries = new ArrayList<>();
        for (Map.Entry<String, Map<String, Integer>> monthEntry : data.entrySet()) {
            for (Map.Entry<String, Integer> typeEntry : monthEntry.getValue().entrySet()) {
                reportEntries.add(new AppointmentReportEntry(monthEntry.getKey(), typeEntry.getKey(), typeEntry.getValue()));
            }
        }

        ObservableList<AppointmentReportEntry> items = FXCollections.observableArrayList(reportEntries);
        customerReportTable.setItems(items);
    }

    // Create a new class to represent each row in the report
    public static class AppointmentReportEntry {
        private final SimpleStringProperty month;
        private final SimpleStringProperty type;
        private final SimpleIntegerProperty count;

        public AppointmentReportEntry(String month, String type, int count) {
            this.month = new SimpleStringProperty(month);
            this.type = new SimpleStringProperty(type);
            this.count = new SimpleIntegerProperty(count);
        }

        public String getMonth() {
            return month.get();
        }

        public String getType() {
            return type.get();
        }

        public int getCount() {
            return count.get();
        }
    }

    //displays the report that tracks the total number of appointments by contact
    public void contactAppointmentReport() {
        DatabaseConnection.establishConnection();

        Map<Contact, List<Appointment>> data = getScheduleForContacts();

        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));

        contactList.forEach(contact -> {
            ObservableList<Appointment> items = FXCollections.observableArrayList(data.get(contact));
            contactReportTable.setItems(items);
        });
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
        //load the first displayed report
        customerAppointmentReport();

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
