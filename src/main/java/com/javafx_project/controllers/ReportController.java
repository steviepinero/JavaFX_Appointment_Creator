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
    public TableColumn<AppointmentReportEntry, String> appointmentTypeColumn;
    @FXML
    public TableColumn<AppointmentReportEntry, String> typeColumn;
    @FXML
    public TableColumn<AppointmentReportEntry, Integer> typeCountColumn;

    @FXML
    public TableView<AppointmentReportEntry> monthlyTotalTable;
    @FXML
    public TableColumn<Map.Entry<String, Map<String, Integer>>, String> appointmentMonthColumn;
    @FXML
    public TableColumn<Map.Entry<String, Map<String, Integer>>, String> apptTypeColumn;
    @FXML
    public TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> appointmentCountColumn;



    @FXML
    public TableView<Appointment> contactReportTable;
    @FXML
    public TableColumn<Appointment, Integer> appointmentIDColumn;

    @FXML
    public TableColumn<Appointment, String> titleColumn;
    @FXML
    public TableColumn<Appointment, String> monthColumn;


    @FXML
    public TableColumn<Appointment, String> descriptionColumn;
    @FXML
    public TableColumn<Appointment, String> startDateColumn;

    @FXML
    public TableColumn<Appointment, String> endDateColumn;
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

    @FXML
    public TableColumn<AppointmentReportEntry, Integer> totalCountColumn;


    @FXML
    private TableColumn<Map.Entry<String, Integer>, Integer> monthlyTotalColumn;

    @FXML
    private TableView<AppointmentReportEntry> typeTotalTable;

    @FXML
    private TableColumn<AppointmentReportEntry, Integer> typeTotalColumn;




    public static ObservableList<User> userList = UserDAO.getAllUsers();
    public static com.javafx_project.dao.AppointmentDAO AppointmentDAO;
    static ObservableList<Appointment> appointmentList = AppointmentDAO.getAllAppointments();
    static ObservableList<Contact> contactList = ContactDAO.getAllContacts();
    public TableColumn<Appointment, String> contactNameColumn;

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
        System.out.println("Appointments by Month: " + resultMap);
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
        System.out.println("Appointments by Type: " + resultMap);
        return resultMap;
    }



    //report that tracks the total number of customer appointments by type and month
    public Map<String, Map<String, Integer>> getAppointmentsByTypeAndMonth() {
        // Construct the initial combined map
        Map<String, Integer> appointmentsByMonth = getAppointmentsByMonth();
        Map<String, Integer> appointmentsByType = getAppointmentsByType();

        Map<String, Map<String, Integer>> combinedMap = new HashMap<>();
        for (String month : appointmentsByMonth.keySet()) {
            Map<String, Integer> typeMap = new HashMap<>();
            for (String type : appointmentsByType.keySet()) {
                typeMap.put(type, 0);
            }
            combinedMap.put(month, typeMap);
        }
        System.out.println("Initial combined map: " + combinedMap);

        // Fetch the data from the database
        Map<String, Map<String, Integer>> fetchedData = fetchAppointmentsByTypeAndMonthFromDB();
        System.out.println("Fetched data from DB: " + fetchedData);

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
        System.out.println("Final combined map: " + combinedMap);

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
                String contactName = rs.getString("Contact_Name");
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
                appointment.setContactName(contactName);
                resultMap.get(contact).add(appointment);
                System.out.println("Contact: " + contact);
                System.out.println("Appointment: " + appointment);
            }
        } catch (SQLException e) {
            // TODO Handle the exception in a more user-friendly way
            e.printStackTrace();
        }
        return resultMap;
    }



    /**
     * Generates a report that tracks the total number of updates made by each user in the database.
     */
    public void generateUserUpdateReport() {
        // Establish database connection
        DatabaseConnection.establishConnection();

        // Set cell values for the username and update count columns
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        updateCountColumn.setCellValueFactory(new PropertyValueFactory<>("Update_Count"));

        // Fetch data and convert to list of User objects
        Map<String, Integer> userUpdates = fetchUserUpdates();
        List<User> userList = new ArrayList<>();
        userUpdates.forEach((username, updateCount) -> {
            User user = new User();
            user.setUser_Name(username);
            user.setUpdate_Count(updateCount);
            userList.add(user);
        });

        // Populate the TableView with the user list
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

        // For Appointments by Month Table
        appointmentMonthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        totalCountColumn.setCellValueFactory(new PropertyValueFactory<>("totalCount"));

        Map<String, Integer> monthData = getAppointmentsByMonth();
        ObservableList<AppointmentReportEntry> monthItems = FXCollections.observableArrayList();
        monthData.forEach((month, count) -> {
            monthItems.add(new AppointmentReportEntry(month, count));
            System.out.println("Added report entry for month: " + month);
        });
        monthlyTotalTable.setItems(monthItems);

        // For Appointments by Type Table
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeTotalColumn.setCellValueFactory(new PropertyValueFactory<>("typeCount"));

        Map<String, Integer> typeData = getAppointmentsByType();
        ObservableList<AppointmentReportEntry> typeItems = FXCollections.observableArrayList();
        typeData.forEach((type, count) -> {
            typeItems.add(new AppointmentReportEntry(type, count));
            System.out.println("Added report entry for type: " + type);
        });
        typeTotalTable.setItems(typeItems);
    }




    //displays the report that tracks the total number of appointments by contact
    public void contactAppointmentReport() {
        DatabaseConnection.establishConnection();

        Map<Contact, List<Appointment>> data = getScheduleForContacts();

        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));

        ObservableList<Appointment> allItems = FXCollections.observableArrayList();
        System.out.println("Contact list: " + contactList);
        for (Contact contact : data.keySet()) {
            List<Appointment> appointments = data.get(contact);
            if (appointments != null) {
                allItems.addAll(appointments);
            }
        }
        System.out.println("All items: " + allItems);
        contactReportTable.setItems(allItems);
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
                typeTotalTable.refresh();
            }
        });

        contactReportTab.setOnSelectionChanged(event -> {
            if (contactReportTab.isSelected()) {
                contactAppointmentReport();
            }
        });

        userReportTab.setOnSelectionChanged(event -> {
            if (userReportTab.isSelected()) {
                generateUserUpdateReport();
            }
        });
    }
}
