package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.javafx_project.dao.*;
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

import static com.javafx_project.dao.DatabaseConnection.connection;

public class CustomerController implements Initializable {

    static ObservableList<Customer> customerList = CustomerDAO.getAllCustomers();
    private static Customer selectedCustomer;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    /** Customer table */
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;
    @FXML
    private TableColumn<Customer, String> addressColumn;
    @FXML
    private TableColumn<Customer, String> postalCodeColumn;
    @FXML
    private TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    private TableColumn<Customer, Timestamp> createDateColumn;
    @FXML
    private TableColumn<Customer, String> createdByColumn;
    @FXML
    private TableColumn<Customer, Timestamp> lastUpdateColumn;
    @FXML
    private TableColumn<Customer, String> lastUpdatedByColumn;
    @FXML
    private TableColumn<Customer, Integer> countryIdColumn;
    @FXML
    private TableColumn<Customer, Integer> divisionIdColumn;
    /** Customer table */


    // combo boxes
    @FXML
    private ComboBox<?> countryBox;
    @FXML
    private ComboBox<?> divisionBox;

    // fields
    @FXML
    private TextField customerName;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField postalCodeField;

    // buttons
    @FXML
    private Button updateCustomerButton;
    @FXML
    private Button addCustomerButton;
    @FXML
    private Button deleteCustomerButton;

    // database objects
    private AppointmentDAO appointmentDAO;
    private ContactDAO contactDAO;
    private CountryDAO countryDAO;
    private CustomerDAO customerDAO;
    private FirstLevelDivisionDAO firstLevelDivisionDAO;
    private UserDAO userdao;




    @FXML
    private void deleteCustomer() {
        // Get selected customer from table
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        // Delete customer from database
        customerDAO.deleteCustomer(customer.getCustomer_Id());
        //display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer deleted");
        alert.setContentText("Customer deleted successfully");
        alert.showAndWait();
        // Refresh table
        customerTable.refresh();

    }

    @FXML
    private void updateCustomer() {
        DatabaseConnection.establishConnection();
        int customerId;
        String lastUpdatedBy;

        lastUpdatedBy = String.valueOf(LoginController.loggedInUser.getUser_Name());

        // Get selected customer from table
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

        // Get customer id from database
        customerId = selectedCustomer.getCustomer_Id();

        try {
            DatabaseConnection.establishConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customers WHERE customer_id = ?");
            preparedStatement.setInt(1, customerId);
            //TODO stopped here for dinner 8/6/23

        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }


        // Update customer in database
        customerDAO.updateCustomer(selectedCustomer);

        // Update table
        customerTable.getItems().set(customerTable.getSelectionModel().getSelectedIndex(), selectedCustomer);

        //display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer updated");
        alert.setContentText("Customer updated successfully");
        alert.showAndWait();

        // Refresh table
        customerTable.setItems(CustomerDAO.getAllCustomers());
        customerTable.refresh();

        // Clear text fields
        customerName.clear();
        addressField.clear();
        postalCodeField.clear();
        phoneNumberField.clear();
        countryBox.getSelectionModel().clearSelection();
        divisionBox.getSelectionModel().clearSelection();




    }

    @FXML
    private void addCustomer()  {
        String createdBy, lastUpdatedBy;
        createdBy = String.valueOf(LoginController.loggedInUser.getUser_Name());
        lastUpdatedBy = createdBy;

        try {
            DatabaseConnection.establishConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, country_id, division_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, customerName.getText());
            preparedStatement.setString(2, addressField.getText());
            preparedStatement.setString(3, postalCodeField.getText());
            preparedStatement.setString(4, phoneNumberField.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            preparedStatement.setString(6, createdBy);
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            preparedStatement.setString(8, lastUpdatedBy);
            preparedStatement.setString(9, countryBox.getValue().toString());
            preparedStatement.setString(10, divisionBox.getValue().toString());
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer added");
            alert.setContentText("Customer added successfully");
            alert.showAndWait();

            setCustomerTable();

            customerName.clear();
            addressField.clear();
            postalCodeField.clear();
            phoneNumberField.clear();
            countryBox.getSelectionModel().clearSelection();
            divisionBox.getSelectionModel().clearSelection();

            customerTable.setItems(CustomerDAO.getAllCustomers());
            customerTable.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public void setCustomerTable() {
        customerTable.setItems(customerList);
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("Create_Date"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("Create_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));
        countryIdColumn.setCellValueFactory(new PropertyValueFactory<>("Country_ID"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setCustomerTable();

    }
}