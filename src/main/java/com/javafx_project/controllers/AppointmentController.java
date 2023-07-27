package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.javafx_project.dao.DatabaseConnection.connection;
import static java.lang.Integer.parseInt;

public class AppointmentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appointment_ID_Column;

    @FXML
    private ComboBox<Contact> contactBox;

    @FXML
    private TableColumn<Appointment, Integer> contactColumn;

    @FXML
    private ComboBox<Customer> customerBox;

    @FXML
    private TableColumn<Appointment, Integer> customer_ID_Column;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private TableColumn<Appointment, String> descriptionColumn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<Appointment, LocalDate> endDateColumn;

    @FXML
    private TextField locationField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableColumn<Appointment, LocalDate> startDateColumn;

    @FXML
    private TableView<Appointment> table;

    @FXML
    private TableColumn<Appointment, String> titleColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TableColumn<Appointment, String> typeColumn;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private TableColumn<Appointment, Integer> user_ID_Column;

    private AppointmentDAO appointmentDAO;
    private ContactDAO contactDAO;
    private CountryDAO countryDAO;
    private CustomerDAO customerDAO;
    private FirstLevelDivisionDAO firstLevelDivisionDAO;
    private UserDAO userdao;
    @FXML
    private ComboBox<String> typeBox = new ComboBox<>();
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
    private String lastUpdatedBy;


    public AppointmentController(TableColumn appointmentIdColumn, AppointmentDAO appointmentDAO) {
        appointment_ID_Column = appointmentIdColumn;
        this.appointmentDAO = appointmentDAO;
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


    @FXML
    void initialize() throws SQLException {
        //initialize DAOs
        appointmentDAO = new AppointmentDAO();
        contactDAO = new ContactDAO();
        countryDAO = new CountryDAO();
        customerDAO = new CustomerDAO();
        firstLevelDivisionDAO = new FirstLevelDivisionDAO();
        userdao = new UserDAO();

        // Set up the columns in the table
        appointment_ID_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDate>("end"));
        customer_ID_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        user_ID_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("createdBy"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("lastUpdate"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("lastUpdatedBy"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("createDate"));

        // Load data
        loadAppointmentData();

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        List<Appointment> appointments = appointmentDAO.getAllAppointments();

        ObservableList<Appointment> data = FXCollections.observableList(appointments);
        appointmentTable.setItems(data);


/*
        List<Contact> allContacts = contactDAO.getAllContacts();
*/
        List<Customer> allCustomers = (List<Customer>) customerDAO.getAllCustomers();
        List<String> allTypes = Arrays.asList("Planning Session", "De-Briefing", "Scrum", "Presentation", "Consultation", "Interview", "Training", "Other");


        // Populate the ComboBoxes
/*
        contactBox.getItems().addAll(allContacts);
*/
        populateContactBox();
        typeBox.getItems().addAll(allTypes);
        //Load data from database
        customerBox.getItems().addAll(customerDAO.getAllCustomers());

        // Set up event handlers for buttons
        addAppointmentButton.setOnAction(e -> addAppointment());
        updateAppointmentButton.setOnAction(e -> updateAppointment());
        deleteAppointmentButton.setOnAction(e -> deleteAppointment());





    }




    @FXML
    private void deleteAppointment() {
        // Get selected appointment
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        // Delete from database
        appointmentDAO.deleteAppointment(appointment.getAppointmentId());

        // Delete from table
        appointmentTable.getItems().remove(appointment);

        // Show dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText(null);
        alert.setContentText("Appointment " + appointment.getAppointmentId() + " has been deleted.");
        alert.showAndWait();

    }

    @FXML
    private void updateAppointment() {
        // Get selected appointment
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();

        // Get the selected Customer and Contact objects from the ComboBoxes
        Customer selectedCustomer = customerBox.getValue();
        Contact selectedContact = contactBox.getValue();

// Get the IDs from the selected Customer and Contact objects
        String customerId = String.valueOf(selectedCustomer.getCustomerId());  // Assuming Customer and Contact classes have getId() method
        String contactId = String.valueOf(selectedContact.getContactId());
        // Update properties
        appointment.setTitle(titleField.getText());
        appointment.setDescription(descriptionField.getText());
        appointment.setLocation(locationField.getText());
        appointment.setContactId(contactId);
        appointment.setType(String.valueOf(typeBox.getValue()));
        appointment.setStart(startDatePicker.getValue());
        appointment.setEnd(endDatePicker.getValue());
        appointment.setCustomerId(Integer.parseInt(customerId));
        appointment.setUserId(parseInt(user_ID_Column.getId()));
        appointment.setAppointmentId(appointment.getAppointmentId());
        appointment.setLastUpdate(LocalDate.parse(lastUpdateColumn.getText()));
        appointment.setLastUpdatedBy(lastUpdatedByColumn.getText());


        // Update in database
        appointmentDAO.updateAppointment(appointment);

        // Refresh table
        appointmentTable.refresh();
    }

    @FXML
    private void addAppointment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        String startDateString = startDatePicker.getValue().format(formatter);
        String endDateString = endDatePicker.getValue().format(formatter);

        // Get the selected Customer and Contact objects from the ComboBoxes
        Customer selectedCustomer = customerBox.getValue();
        Contact selectedContact = contactBox.getValue();

// Get the IDs from the selected Customer and Contact objects
        String customerId = String.valueOf(selectedCustomer.getCustomerId());
        String contact = String.valueOf(selectedContact.getContactId());

        // Get data from UI elements
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = String.valueOf(typeBox.getValue());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String userId = user_ID_Column.getId();
        String createdBy = createdByColumn.getText();
        LocalDate createDate = null;
        LocalDate lastUpdate = null;
        String lastUpdatedBy = lastUpdatedByColumn.getText();
        try {
            createDate = LocalDate.parse(createDateColumn.getText(), formatter);
            lastUpdate = LocalDate.parse(lastUpdateColumn.getText(), formatter);
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse date: " + e.getMessage());
        }

        // Create new Appointment object
        Appointment appointment = new Appointment();
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setContactId(contact);
        appointment.setType(type);
        appointment.setStart(startDate);
        appointment.setEnd(endDate);
        appointment.setCustomerId(parseInt(customerId));
        appointment.setUserId(parseInt(userId));
        appointment.setCreatedBy(createdBy);
        appointment.setLastUpdatedBy(lastUpdatedBy);
        appointment.setCreateDate(LocalDate.now());


        if (createDate != null) {
            appointment.setCreateDate(createDate);
        }
        appointment.setLastUpdatedBy(lastUpdatedBy);
        if (lastUpdate != null) {
            appointment.setLastUpdate(lastUpdate);
        }

        // Add to database
        appointmentDAO.addAppointment(appointment);

        // Add to table
        appointmentTable.getItems().add(appointment);
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
            List<Appointment> appointments = appointmentDAO.getAllAppointments();

            // Convert to observable list and add to table
            ObservableList<Appointment> appointmentData = FXCollections.observableArrayList(appointments);
            appointmentTable.setItems(appointmentData);

            // Refresh table
            appointmentTable.refresh();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

}