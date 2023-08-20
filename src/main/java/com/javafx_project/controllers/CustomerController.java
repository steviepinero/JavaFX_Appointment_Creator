package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.javafx_project.dao.*;
import com.javafx_project.models.Country;
import com.javafx_project.models.FirstLevelDivision;
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
    private ComboBox<Country> countryBox;
    @FXML
    private ComboBox<FirstLevelDivision> divisionBox;

    // fields
    @FXML
    private TextField customerNameField;
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

    public CustomerController() {
    }


    @FXML
    public void populateCountryBox() {
        String query = "SELECT * FROM countries";
        try {
            // Check if connection is open
            if (connection == null || connection.isClosed()) {
                // Open connection
                DatabaseConnection.establishConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int countryId = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country_Name");
                Country country = new Country(countryId, countryName);
                countryBox.getItems().add(country);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(FirstLevelDivisionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void populateDivisionBox() {
        String query = "SELECT * FROM divisions";
        try {
            // Check if connection is open
            if (connection == null || connection.isClosed()) {
                // Open connection
                DatabaseConnection.establishConnection();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division_Name");
                FirstLevelDivision division = new FirstLevelDivision(divisionId, divisionName);
                divisionBox.getItems().add(division);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(FirstLevelDivisionController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    private void deleteCustomer(ActionEvent actionEvent) {
        // Get selected customer from table
        DatabaseConnection.establishConnection();
        selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        // Delete customer from database
        customerDAO.deleteCustomer(selectedCustomer.getCustomer_ID());
        //display success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Customer deleted");
        alert.setContentText("Customer deleted successfully");
        alert.showAndWait();
        // Refresh table
        customerTable.setItems(CustomerDAO.getAllCustomers());
        customerTable.refresh();

    }

    @FXML
    public void updateCustomer(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();

        String customerName = customerNameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phone = phoneNumberField.getText();
        String lastUpdatedBy = String.valueOf(LoginController.loggedInUser.getUser_Name());


        //Update fields with selectedCustomer data
        customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //get selected customer from table
                Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();

                // Update text fields
                customerNameField.setText(selectedCustomer.getCustomer_Name());
                addressField.setText(selectedCustomer.getAddress());
                postalCodeField.setText(selectedCustomer.getPostal_Code());
                phoneNumberField.setText(selectedCustomer.getPhone());

                // Update combo boxes
//                countryBox.setValue(selectedCustomer.getCountry_ID());
//                divisionBox.setValue(selectedCustomer.getDivision_ID());
            }
        });

        // Get customer id from database
        int customerId = selectedCustomer.getCustomer_ID();


        try {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ? WHERE customer_id = ?");
//            ResultSet resultSet = preparedStatement.executeQuery();

           preparedStatement.setString(1, customerName);
           preparedStatement.setString(2, address);
           preparedStatement.setString(3, postalCode);
           preparedStatement.setString(4, phone);
           preparedStatement.setString(5, lastUpdatedBy);
           preparedStatement.setInt(6, customerId);
           preparedStatement.executeUpdate();



            //display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer updated");
            alert.setContentText("Customer updated successfully");
            alert.showAndWait();

            setCustomerTable();
            customerTable.setItems(CustomerDAO.getAllCustomers());
            customerTable.refresh();

            // Clear text fields
            customerNameField.clear();
            addressField.clear();
            postalCodeField.clear();
            phoneNumberField.clear();
            countryBox.getSelectionModel().clearSelection();
            divisionBox.getSelectionModel().clearSelection();


        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addCustomer()  {
        String createdBy, lastUpdatedBy;
        createdBy = String.valueOf(LoginController.loggedInUser.getUser_Name());
        lastUpdatedBy = createdBy;

        try {
            DatabaseConnection.establishConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO customers (customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, country_id, division_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, customerNameField.getText());
            preparedStatement.setString(2, addressField.getText());
            preparedStatement.setString(3, postalCodeField.getText());
            preparedStatement.setString(4, phoneNumberField.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            preparedStatement.setString(6, createdBy);
            preparedStatement.setTimestamp(7, Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            preparedStatement.setString(8, lastUpdatedBy);
            preparedStatement.setInt(9, countryBox.getValue().getCountry_ID());
            preparedStatement.setInt(10, divisionBox.getValue().getDivision_ID());
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer added");
            alert.setContentText("Customer added successfully");
            alert.showAndWait();

            setCustomerTable();
            customerTable.setItems(CustomerDAO.getAllCustomers());
            customerTable.refresh();

            customerNameField.clear();
            addressField.clear();
            postalCodeField.clear();
            phoneNumberField.clear();
            countryBox.getSelectionModel().clearSelection();
            divisionBox.getSelectionModel().clearSelection();




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
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        countryIdColumn.setCellValueFactory(new PropertyValueFactory<>("Country_ID"));

        populateCountryBox();
        populateDivisionBox();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setCustomerTable();

    }
}