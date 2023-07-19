package com.javafx_project.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.javafx_project.dao.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.javafx_project.dao.*;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import com.javafx_project.models.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<?> countryBox;

    @FXML
    private TableColumn<?, ?> countryColumn;

    @FXML
    private TextField customerName;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<?> divisionBox;

    @FXML
    private TableColumn<?, ?> divisionColumn;

    @FXML
    private TableColumn<?, ?> phoneNumberColumn;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TableColumn<?, ?> postalCodeColumn;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TableView<?> tableCustomers;

    @FXML
    private Button updateCustomerButton;
    @FXML

    private Button addCustomerButton;
    @FXML

    private Button deleteCustomerButton;

    private AppointmentDAO appointmentDAO;
    private ContactDAO contactDAO;
    private CountryDAO countryDAO;
    private CustomerDAO customerDAO;
    private FirstLevelDivisionDAO firstLevelDivisionDAO;
    private UserDAO userdao;


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
       /* tableCustomers.getItems().addAll(customerDAO.getAllCustomers());
        divisionBox.getItems().addAll(firstLevelDivisionDAO.getAllDivisions());
        countryBox.getItems().addAll(countryDAO.getAllCountries());*/

        // Set up event handlers for buttons
        addCustomerButton.setOnAction(e -> addCustomer());
        updateCustomerButton.setOnAction(e -> updateCustomer());
        deleteCustomerButton.setOnAction(e -> deleteCustomer());

    }

    private void deleteCustomer() {
    }

    private void updateCustomer() {
    }

    private void addCustomer() {
    }

}