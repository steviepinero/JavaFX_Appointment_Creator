package com.javafx_project.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.javafx_project.dao.*;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import com.javafx_project.models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<?, ?> endDateTimeColumn;

    @FXML
    private TableColumn<?, ?> endDateTimeColumn1;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TableColumn<?, ?> startDateTimeColumn;

    @FXML
    private TableColumn<?, ?> startDateTimeColumn1;

    @FXML
    private TextField startTimeField;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private TableColumn<?, ?> user_ID_Column;

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
        //initialize DAOs
        appointmentDAO = new AppointmentDAO();
        contactDAO = new ContactDAO();
        countryDAO = new CountryDAO();
        customerDAO = new CustomerDAO();
        firstLevelDivisionDAO = new FirstLevelDivisionDAO();
        userdao = new UserDAO();

        //Load data from database
        appointmentTable.getItems().addAll(appointmentDAO.getAllAppointments());
        typeBox.getItems().addAll(appointmentDAO.getAllTypes());
        contactBox.getItems().addAll(contactDAO.getAllContacts());
        customerBox.getItems().addAll(customerDAO.getAllCustomers());

        // Set up event handlers for buttons
        addAppointmentButton.setOnAction(e -> addAppointment());
        updateAppointmentButton.setOnAction(e -> updateAppointment());
        deleteAppointmentButton.setOnAction(e -> deleteAppointment());



        //fxml tests
        assert addAppointmentButton != null : "fx:id=\"addAppointmentButton\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert appointment_ID_Column != null : "fx:id=\"appointment_ID_Column\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert contactBox != null : "fx:id=\"contactBox\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert contactColumn != null : "fx:id=\"contactColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert customerBox != null : "fx:id=\"customerBox\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert customer_ID_Column != null : "fx:id=\"customer_ID_Column\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert deleteAppointmentButton != null : "fx:id=\"deleteAppointmentButton\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert descriptionColumn != null : "fx:id=\"descriptionColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert endDateTimeColumn != null : "fx:id=\"endDateTimeColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert endDateTimeColumn1 != null : "fx:id=\"endDateTimeColumn1\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert endTimeField != null : "fx:id=\"endTimeField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert postalCodeField != null : "fx:id=\"postalCodeField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert startDateTimeColumn != null : "fx:id=\"startDateTimeColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert startDateTimeColumn1 != null : "fx:id=\"startDateTimeColumn1\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert startTimeField != null : "fx:id=\"startTimeField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert titleColumn != null : "fx:id=\"titleColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert titleField != null : "fx:id=\"titleField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert updateAppointmentButton != null : "fx:id=\"updateAppointmentButton\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert user_ID_Column != null : "fx:id=\"user_ID_Column\" was not injected: check your FXML file 'appointmentView.fxml'.";



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
        appointment.setStart(LocalDateTime.parse(String.valueOf(startTimeField.getText())));
        appointment.setEnd(LocalDateTime.parse(String.valueOf(endTimeField.getText())));
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
        LocalDateTime startDate = LocalDateTime.parse(String.valueOf(startTimeField));
        LocalDateTime endDate = LocalDateTime.parse(String.valueOf(endTimeField));
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


}