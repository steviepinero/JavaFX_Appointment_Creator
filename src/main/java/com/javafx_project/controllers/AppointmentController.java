package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.stage.Stage;

import static java.util.logging.Level.parse;

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
    private TableColumn<Appointment, String> contactColumn;

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
    private TableColumn<Appointment, Date> endDateColumn;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableColumn<Appointment, Date> startDateColumn;

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


    public AppointmentController(TableColumn appointmentIdColumn) {
        appointment_ID_Column = appointmentIdColumn;
    }

    public AppointmentController() {
    }

    @FXML
    void initialize() {

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        List<Appointment> appointments = appointmentDAO.getAllAppointments();

        ObservableList<Appointment> data = FXCollections.observableArrayList(appointments);
        appointmentTable.setItems(data);
        //initialize DAOs
        appointmentDAO = new AppointmentDAO();
        contactDAO = new ContactDAO();
        countryDAO = new CountryDAO();
        customerDAO = new CustomerDAO();
        firstLevelDivisionDAO = new FirstLevelDivisionDAO();
        userdao = new UserDAO();

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
        appointment.setContactId(Integer.parseInt(contactBox.getId()));
        appointment.setType(String.valueOf(typeBox.getValue()));
        appointment.setStart(startDatePicker.getValue());
        appointment.setEnd(endDatePicker.getValue());
        appointment.setCustomerId(Integer.parseInt(customerBox.getId()));
        appointment.setUserId(Integer.parseInt(user_ID_Column.getId()));
        // TODO verify functionality


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
        int customerId = Integer.parseInt(customerBox.getId());
        int userId = Integer.parseInt(user_ID_Column.getId());


        // Create new Appointment object
        Appointment appointment = new Appointment();
        appointment.setTitle(title);
        appointment.setDescription(description);
        appointment.setLocation(location);
        appointment.setContactId(Integer.parseInt(contact));
        appointment.setType(type);
        appointment.setStart(startDate);
        appointment.setEnd(endDate);
        appointment.setCustomerId(customerId);
        appointment.setUserId(userId);

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
}