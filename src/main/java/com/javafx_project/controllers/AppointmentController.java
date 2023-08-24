package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.javafx_project.dao.*;
import com.javafx_project.models.Appointment;
import com.javafx_project.models.Contact;
import com.javafx_project.models.Customer;
import com.javafx_project.models.User;
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
import static com.javafx_project.controllers.LoginController.loggedInUser;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class AppointmentController implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addAppointmentButton;

    private  Appointment selectedAppointment;


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
    private TableColumn <Appointment, Integer> user_ID_Column;
    @FXML
    private TableColumn <Appointment, String> locationColumn;
    @FXML
    private TableColumn <Appointment, String> createdByColumn;
    @FXML
    private TableColumn <Appointment, String> lastUpdatedByColumn;
    @FXML
    private TableColumn <Appointment, Timestamp> createDateColumn;
    @FXML
    private TableColumn <Appointment, Timestamp> lastUpdateColumn;

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
    private  String createdBy = LoginController.loggedInUser.getUser_Name();
    private int userId = LoginController.loggedInUser.getUser_ID();

 // TODO fix update and delete
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
        appointment_ID_Column.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("create_date"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("created_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("last_Update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("last_Updated_By"));
        customer_ID_Column.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        user_ID_Column.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));


        populateContactBox();
        populateCustomerBox();

        // List of all types
         List<String> allTypes = Arrays.asList("Planning Session", "De-Briefing", "Scrum", "Presentation", "Consultation", "Interview", "Training", "Other");
        // Add all types to the typeBox
        typeBox.getItems().addAll(allTypes);
    }

    @FXML
    private void addAppointment() {
        DatabaseConnection.establishConnection();
        String createdBy, lastUpdatedBy;
        int loggedInUserID;

       createdBy = String.valueOf(loggedInUser.getUser_Name());
       lastUpdatedBy = createdBy;

        loggedInUserID = valueOf(userId);
        System.out.print("~Add Appt method \n");

        //save logged in userID to a variable

        System.out.print(loggedInUserID);
       try {
           if (connection == null || connection.isClosed()) {
               // Open connection
               DatabaseConnection.establishConnection();
           }
           PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

           preparedStatement.setString(1, titleField.getText());
           preparedStatement.setString(2, descriptionField.getText());
           preparedStatement.setString(3, locationField.getText());
           preparedStatement.setString(4, typeBox.getValue());
           preparedStatement.setDate(5, Date.valueOf(startDatePicker.getValue()));
           preparedStatement.setObject(6, Date.valueOf(endDatePicker.getValue()));
           preparedStatement.setObject(7, LocalDate.now());
           preparedStatement.setString(8, createdBy);
           preparedStatement.setObject(9, LocalDate.now());
           preparedStatement.setString(10, lastUpdatedBy);
           preparedStatement.setInt(11, customerBox.getValue().getCustomer_ID());
           preparedStatement.setInt(12, loggedInUserID);
           preparedStatement.setInt(13, contactBox.getValue().getContact_ID());
           System.out.print("Add Appt method \n" + loggedInUserID);
           preparedStatement.executeUpdate();

           // Show dialog
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Appointment Added");
           alert.setHeaderText(null);
           alert.setContentText("Appointment added successfully!");
           alert.showAndWait();

           setAppointmentTable();
           appointmentTable.setItems(AppointmentDAO.getAllAppointments());
           appointmentTable.refresh();

           // Clear text fields
           titleField.clear();
           descriptionField.clear();
           locationField.clear();
           typeBox.getSelectionModel().clearSelection();
           startDatePicker.setValue(null);
           endDatePicker.setValue(null);
           customerBox.getSelectionModel().clearSelection();
           contactBox.getSelectionModel().clearSelection();

       } catch (SQLException e) {
           Logger.getLogger(AppointmentController.class.getName()).log(Level.SEVERE, null, e);
       }

    }
    @FXML
    public void updateAppointment(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();

        String title, description, location, type, lastUpdatedBy;
        LocalDate startDate, endDate;
        int customerId, contactId, appointmentId;

        title = titleField.getText();
        description = descriptionField.getText();
        location = locationField.getText();
        type = typeBox.getValue();
        startDate = startDatePicker.getValue();
        endDate = endDatePicker.getValue();
        customerId = customerBox.getValue().getCustomer_ID();
        contactId = contactBox.getValue().getContact_ID();
        lastUpdatedBy = String.valueOf(LoginController.loggedInUser.getUser_Name());


        // Get appointment id from selected appointment
        appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID();


        try {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, Contact_ID = ?, Last_Update = NOW(), Last_Updated_By = ? WHERE Appointment_ID = ?");

            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, location);
            preparedStatement.setString(4, type);
            preparedStatement.setDate(5, Date.valueOf(startDate));
            preparedStatement.setDate(6, Date.valueOf(endDate));
            preparedStatement.setInt(7, customerId);
            preparedStatement.setInt(8, contactId);
            preparedStatement.setString(9, lastUpdatedBy);
            preparedStatement.setInt(10, appointmentId);
            preparedStatement.executeUpdate();



            //display success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Appointment updated");
            alert.setContentText("Appointment updated successfully");
            alert.showAndWait();

            setAppointmentTable();
            appointmentTable.setItems(AppointmentDAO.getAllAppointments());
            appointmentTable.refresh();

            // Clear text fields
            titleField.clear();
            descriptionField.clear();
            locationField.clear();
            typeBox.getSelectionModel().clearSelection();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            customerBox.getSelectionModel().clearSelection();
            contactBox.getSelectionModel().clearSelection();


        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void deleteAppointment(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        // Get selected appointment
        selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

    if (selectedAppointment != null) {
        // Delete from database
        appointmentDAO.deleteAppointment(selectedAppointment.getAppointment_ID());


        // Show dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText(null);
        alert.setContentText("Appointment " + this.selectedAppointment.getAppointment_ID() + " has been deleted.");
        alert.showAndWait();

        //Refresh table
        appointmentTable.setItems(AppointmentDAO.getAllAppointments());
        appointmentTable.refresh();
    } else {
            // Display an alert or message to inform the user to select a customer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select an item to delete");
            alert.showAndWait();
            System.out.println("Please select an item to delete.");
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

    public void setAppointmentDAO(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    @FXML
    private void handleTableRowClick() {
        // Get the selected appointment from the table
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            // Populate the fields with the selected appointment's data
            titleField.setText(selectedAppointment.getTitle());
            descriptionField.setText(selectedAppointment.getDescription());
            locationField.setText(selectedAppointment.getLocation());
            typeBox.setValue(selectedAppointment.getType());
            startDatePicker.setValue(selectedAppointment.getStart());
            endDatePicker.setValue(selectedAppointment.getEnd());
            customerBox.setValue(new Customer(selectedAppointment.getCustomer_ID(), ""));
            contactBox.setValue(new Contact(selectedAppointment.getContact_ID(), ""));
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setAppointmentTable();
        this.appointmentDAO = new AppointmentDAO();
        // Add a listener to the table's selection model
        appointmentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleTableRowClick();
            }
        });
    }
}