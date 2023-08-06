package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.javafx_project.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.javafx_project.models.Customer;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<Customer, String> addressColumn;

    @FXML
    private TextField addressField;

    @FXML
    private ComboBox<?> countryBox;

    @FXML
    private TableColumn<Customer, String> countryColumn;

    @FXML
    private TextField customerName;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private ComboBox<?> divisionBox;

    @FXML
    private TableColumn<Customer, String> divisionColumn;

    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TableColumn<Customer, String> postalCodeColumn;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TableView<Customer> tableCustomers;

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
    void initialize() throws SQLException {

        //initialize DAOs
        appointmentDAO = new AppointmentDAO();
        contactDAO = new ContactDAO();
        countryDAO = new CountryDAO();
        customerDAO = new CustomerDAO();
        firstLevelDivisionDAO = new FirstLevelDivisionDAO();
        userdao = new UserDAO();


        // Set up table columns
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        //Load data from database
        loadCustomerData();

        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customers = customerDAO.getAllCustomers();
        ObservableList<Customer> data = FXCollections.observableList(customers);
        tableCustomers.setItems(data);


        // Set up combo boxes
     /*   divisionBox.getItems().addAll(firstLevelDivisionDAO.getAllDivisions());
        countryBox.getItems().addAll(countryDAO.getAllCountries());*/


        // Set up event handlers for buttons
        addCustomerButton.setOnAction(e -> {
            try {
                addCustomer();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        updateCustomerButton.setOnAction(e -> updateCustomer());
        deleteCustomerButton.setOnAction(e -> deleteCustomer());

    }

    private void deleteCustomer() {
        // Get selected customer from table
        Customer customer = tableCustomers.getSelectionModel().getSelectedItem();
        // Delete customer from database
        customerDAO.deleteCustomer(customer.getCustomerId());
        // Refresh table
        tableCustomers.refresh();

    }

    private void updateCustomer() {
        // Get values from text fields
        String customerName = this.customerName.getText();
        String address = this.addressField.getText();
        String postalCode = this.postalCodeField.getText();
        String phoneNumber = this.phoneNumberField.getText();
        String division = this.divisionBox.getValue().toString();

        // Create new customer object
        Customer customer = new Customer(customerName, address, postalCode, phoneNumber, division);

        // Add customer to database
        customerDAO.updateCustomer(customer);

        // Clear text fields
        this.customerName.clear();
        this.addressField.clear();
        this.postalCodeField.clear();
        this.phoneNumberField.clear();
        this.divisionBox.getSelectionModel().clearSelection();

        // Display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer updated");
        alert.setContentText("Customer updated successfully");
        alert.showAndWait();

        // Refresh table
        tableCustomers.refresh();
    }

    private void addCustomer() throws SQLException {
        // Get values from text fields
        String customerName = this.customerName.getText();
        String address = this.addressField.getText();
        String postalCode = this.postalCodeField.getText();
        String phoneNumber = this.phoneNumberField.getText();
        String division = this.divisionBox.getValue().toString();

        // Create new customer object
        Customer customer = new Customer(customerName, address, postalCode, phoneNumber, division);

        // Add customer to database
        customerDAO.addCustomer(customer);

        // Clear text fields
        this.customerName.clear();
        this.addressField.clear();
        this.postalCodeField.clear();
        this.phoneNumberField.clear();
        this.divisionBox.getSelectionModel().clearSelection();

        // Display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer added");
        alert.setContentText("Customer added successfully");
        alert.showAndWait();

        // Refresh table
        tableCustomers.refresh();

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

    public void loadCustomerData() throws SQLException {
        // get all customers from database
        List<Customer> customersList = customerDAO.getAllCustomers();

        //convert customer list to observable list
        ObservableList<Customer> customerData = FXCollections.observableArrayList(customersList);

        // set up table columns
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<>("division"));


        // add customers to table
        tableCustomers.getItems().addAll(customerData);
        tableCustomers.refresh();
        // add countries to country combo box
/*
        countryBox.setItems(countryDAO.getAllCountries());
*/

        // add divisions to division combo box
/*
        divisionBox.setItems(firstLevelDivisionDAO.getAllDivisions());
*/

    }

    public void deleteButtonAction(ActionEvent actionEvent) throws SQLException {
        // Get selected customer
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();

        // Delete customer from database
        customerDAO.deleteCustomer(customer.getCustomerId());

        // Display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer deleted");
        alert.setContentText("Customer deleted successfully");
        alert.showAndWait();

        // Refresh table
        tableCustomers.getItems().clear();
        tableCustomers.getItems().addAll(customerDAO.getAllCustomers());
    }

    public void updateButtonAction(ActionEvent actionEvent) throws SQLException {
        // Get selected customer
        Customer customer = (Customer) tableCustomers.getSelectionModel().getSelectedItem();

        // Get values from text fields
        TableView<Customer> customerTable;
        customerTable = (TableView<Customer>) tableCustomers;
        int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
        String customerName = this.customerName.getText();
        String address = this.addressField.getText();
        String postalCode = this.postalCodeField.getText();
        String phoneNumber = this.phoneNumberField.getText();
        String division = this.divisionBox.getValue().toString();

        // Create new customer object
        Customer updatedCustomer = new Customer(customer.getCustomerId(), customerName, address, postalCode, phoneNumber, division);

        // Update customer in database
        customerDAO.updateCustomer(updatedCustomer);

        // Clear text fields
        this.customerName.clear();
        this.addressField.clear();
        this.postalCodeField.clear();
        this.phoneNumberField.clear();
        this.divisionBox.getSelectionModel().clearSelection();

        // Display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer updated");
        alert.setContentText("Customer updated successfully");
        alert.showAndWait();

        // Refresh table
        tableCustomers.getItems().clear();
        tableCustomers.getItems().addAll(customerDAO.getAllCustomers());


    }

    public void addButtonAction(ActionEvent actionEvent) {
        // Get values from text fields
        String customerName = this.customerName.getText();
        String address = this.addressField.getText();
        String postalCode = this.postalCodeField.getText();
        String phoneNumber = this.phoneNumberField.getText();
        String division = this.divisionBox.getValue().toString();
        String country = this.countryBox.getValue().toString();

        // Create new customer object
        Customer customer = new Customer(customerName, address, postalCode, phoneNumber, division, country);

        // Add customer to database
        customerDAO.addCustomer(customer);

        // Clear text fields
        this.customerName.clear();
        this.addressField.clear();
        this.postalCodeField.clear();
        this.phoneNumberField.clear();
        this.divisionBox.getSelectionModel().clearSelection();
        this.countryBox.getSelectionModel().clearSelection();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}