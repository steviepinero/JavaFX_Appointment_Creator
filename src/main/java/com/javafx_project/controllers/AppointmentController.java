package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.javafx_project.dao.*;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import com.javafx_project.models.Customer;
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

import static com.javafx_project.controllers.UserController.appointmentList;
import static com.javafx_project.dao.DatabaseConnection.connection;
import static java.lang.Integer.parseInt;

public class AppointmentController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addAppointmentButton;


    /** Appointment table */
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, Integer> appointment_ID_Column;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, Integer> contactColumn;
    @FXML
    private TableColumn<Appointment, String> customer_ID_Column;
    @FXML
    private TableColumn<Appointment, String> descriptionColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> endDateColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> startDateColumn;
    @FXML
    private TableColumn<Appointment, Integer> user_ID_Column;
    @FXML
    private TableColumn <Appointment, String> locationColumn;
    @FXML
    private TableColumn <Appointment, String> createdByColumn;
    @FXML
    private TableColumn <Appointment, String> lastUpdatedByColumn;
    @FXML
    private TableColumn <Appointment, String> createDateColumn;
    @FXML
    private TableColumn <Appointment, String> lastUpdateColumn;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private TextField descriptionField;
    @FXML
    private TextField locationField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField titleField;

    @FXML
    private Button updateAppointmentButton;

    private AppointmentDAO appointmentDAO;

    @FXML
    private ComboBox<String> typeBox = new ComboBox<>();
    @FXML
    private ComboBox<Contact> contactBox;
    @FXML
    private ComboBox<Customer> customerBox;
    private String lastUpdatedBy = LoginController.loggedInUser.getUser_Name();
    private final String createdBy = LoginController.loggedInUser.getUser_Name();

    public AppointmentController(TableColumn appointmentIdColumn, AppointmentDAO appointmentDAO, String lastUpdatedBy) {
        this.appointment_ID_Column = appointmentIdColumn;
        this.appointmentDAO = appointmentDAO;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public AppointmentController() {
    }

    public void populateContactBox() {
        String query = "SELECT * FROM contacts";
        try {
            // Check if connection is open
            if (connection == null || connection.isClosed()) {
                // Open connection
                DatabaseConnection.establishConnection();
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                Contact contact = new Contact(id, name);
                contactBox.getItems().add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateCustomerBox() {
        String query = "SELECT * FROM customers";
        try {
            // Check if connection is open
            if (connection == null || connection.isClosed()) {
                // Open connection
                DatabaseConnection.establishConnection();
            }
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                Customer customer = new Customer(id, name);
                customerBox.getItems().add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setAppointmentTable() {
        // Set up the columns in the table
        appointmentTable.setItems(appointmentList);
        appointment_ID_Column.setCellValueFactory(new PropertyValueFactory<>("appointment_Id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("create_date"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("created_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("last_update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("last_updated_by"));
        customer_ID_Column.setCellValueFactory(new PropertyValueFactory<>("customer_Id"));
        user_ID_Column.setCellValueFactory(new PropertyValueFactory<>("user_Id"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact_Id"));


        populateContactBox();
        populateCustomerBox();

        // List of all types
         List<String> allTypes = Arrays.asList("Planning Session", "De-Briefing", "Scrum", "Presentation", "Consultation", "Interview", "Training", "Other");
        // Add all types to the typeBox
        typeBox.getItems().addAll(allTypes);
    }

    @FXML
    private void addAppointment() {
        // Get the values from the text fields
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeBox.getValue();
        LocalDateTime startDate = startDatePicker.getValue().atStartOfDay();
        LocalDateTime endDate = endDatePicker.getValue().atStartOfDay();
        String createdBy = LoginController.loggedInUser.getUser_Name();
        String lastUpdatedBy = LoginController.loggedInUser.getUser_Name();
        Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
        int customerId = customerBox.getValue().getCustomer_Id();
        int userId = LoginController.loggedInUser.getUser_ID();
        int contactId = contactBox.getValue().getContact_ID();

        // Create a new Appointment object
        Appointment appointment = new Appointment(title, description, location, type, startDate, endDate, customerId, userId, contactId, createdBy, lastUpdatedBy, createDate, lastUpdate);

        // Add to database
        AppointmentDAO.addAppointment(title, description, location, type, startDate, endDate, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);

        // Add to table
        appointmentTable.getItems().add(appointment);

        // Show dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Added");
        alert.setHeaderText(null);
        alert.setContentText("Appointment added successfully!");
        alert.showAndWait();

        // Clear text fields
        titleField.clear();
        descriptionField.clear();
        locationField.clear();
        typeBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        customerBox.getSelectionModel().clearSelection();
        contactBox.getSelectionModel().clearSelection();

    }
@FXML
    private void updateAppointment() {
        // Get the values from the text fields
        int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        int customerId = customerBox.getValue().getCustomer_Id();
        int contactId = contactBox.getValue().getContact_ID();
        String lastUpdatedBy = LoginController.loggedInUser.getUser_Name();

        // Create a new Appointment object
        Appointment updatedAppointment = new Appointment(appointmentId, title, description, location, type, startDate, endDate, customerId, contactId, lastUpdatedBy);

        // Update database
        appointmentDAO.updateAppointment(updatedAppointment);

        // Update table
        appointmentTable.getItems().set(appointmentTable.getSelectionModel().getSelectedIndex(), updatedAppointment);

            }


    @FXML
    private void deleteAppointment() {
        // Get selected appointment
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        // Delete from database
        appointmentDAO.deleteAppointment(appointment.getAppointment_ID());

        // Delete from table
        appointmentTable.getItems().remove(appointment);

        // Show dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText(null);
        alert.setContentText("Appointment " + appointment.getAppointment_ID() + " has been deleted.");
        alert.showAndWait();

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

    private void loadAppointmentData() {
        try {
            // Get all appointments
            List<Appointment> appointments = AppointmentDAO.getAllAppointments();

            // Convert to observable list and add to table
            ObservableList<Appointment> appointmentData = FXCollections.observableArrayList(appointments);
            appointmentTable.setItems(appointmentData);

            // Refresh table
            appointmentTable.refresh();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setAppointmentTable();
    }
}