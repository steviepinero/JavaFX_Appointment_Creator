package com.javafx_project.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

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
    private  String createdBy = LoginController.loggedInUser.getUser_Name();

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
    private void addAppointment(ActionEvent actionEvent) {
        String createdBy, lastUpdatedBy;

       createdBy = String.valueOf(LoginController.loggedInUser.getUser_Name());
       lastUpdatedBy = createdBy;

        UserDAO userDAO = new UserDAO();
        User loggedInUserID = userDAO.getUserByUserId(loggedInUser.getUser_ID());
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
           preparedStatement.setInt(12, loggedInUserID.getUser_ID());
           preparedStatement.setInt(13, contactBox.getValue().getContact_ID());
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

       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }
@FXML
    private void updateAppointment() {

    this.selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

    // Get the values from the text fields
        int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID();
        String title = titleField.getText();
        String description = descriptionField.getText();
        String location = locationField.getText();
        String type = typeBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        int customerId = customerBox.getValue().getCustomer_ID();
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
        this.selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

        // Delete from database
        appointmentDAO.deleteAppointment(this.selectedAppointment.getAppointment_ID());

        // Delete from table
        appointmentTable.getItems().remove(this.selectedAppointment);

        // Show dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText(null);
        alert.setContentText("Appointment " + this.selectedAppointment.getAppointment_ID() + " has been deleted.");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DatabaseConnection.getConnection();
        setAppointmentTable();
    }
}