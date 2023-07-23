package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private TextField postalCodeField;

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
    private ComboBox<Appointment> typeBox;
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


    public AppointmentController(TableColumn appointmentIdColumn, AppointmentDAO appointmentDAO) {
        appointment_ID_Column = appointmentIdColumn;
        this.appointmentDAO = appointmentDAO;
    }

    public AppointmentController() {
    }

    @FXML
    void initialize() {
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


        //Load data from database
        appointmentTable.getItems().addAll(appointmentDAO.getAllAppointments());
        typeBox = new ComboBox<>();
        typeBox.getItems();
        contactBox.getItems().addAll(contactDAO.getAllContacts());
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

        // Update properties
        appointment.setTitle(titleField.getText());
        appointment.setDescription(descriptionField.getText());
        appointment.setLocation(postalCodeField.getText());
        appointment.setContactId(parseInt(contactBox.getId()));
        appointment.setType(String.valueOf(typeBox.getValue()));
        appointment.setStart(startDatePicker.getValue());
        appointment.setEnd(endDatePicker.getValue());
        appointment.setCustomerId(parseInt(customerBox.getId()));
        appointment.setUserId(parseInt(user_ID_Column.getId()));
        appointment.setAppointmentId(parseInt(appointment_ID_Column.getId()));
        appointment.setLastUpdate(LocalDate.parse(lastUpdateColumn.getText()));
        appointment.setLastUpdatedBy(lastUpdatedByColumn.getText());


        // Update in database
        appointmentDAO.updateAppointment(appointment);

        // Refresh table
        appointmentTable.refresh();
    }

    @FXML
    private void addAppointment() {
        // Get data from UI elements
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = postalCodeField.getText();
        String contact = String.valueOf(contactBox.getValue());
        String type = String.valueOf(typeBox.getValue());
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        int customerId = parseInt(customerBox.getId());
        int userId = parseInt(user_ID_Column.getId());
        String createdBy = createdByColumn.getText();
        LocalDate createDate = LocalDate.parse(createDateColumn.getText());
        String lastUpdatedBy = lastUpdatedByColumn.getText();
        LocalDate lastUpdate = LocalDate.parse(lastUpdateColumn.getText());


        // Create new Appointment object
        Appointment appointment = new Appointment();
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setContactId(parseInt(contact));
        appointment.setType(type);
        appointment.setStart(startDate);
        appointment.setEnd(endDate);
        appointment.setCustomerId(customerId);
        appointment.setUserId(userId);
        appointment.setCreatedBy(createdBy);
        appointment.setCreateDate(createDate);
        appointment.setLastUpdatedBy(lastUpdatedBy);
        appointment.setLastUpdate(lastUpdate);

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