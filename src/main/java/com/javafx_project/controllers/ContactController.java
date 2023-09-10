package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Contact;
import com.javafx_project.dao.ContactDAO;
import com.javafx_project.models.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.dao.DatabaseConnection.connection;


/**
 * The type Contact controller.
 */
public class ContactController implements Initializable {
    /**
     * The Contact list.
     */
    static ObservableList<Contact> contactList = ContactDAO.getAllContacts();

    private static Contact selectedContact;


    /** Contact table */
    @FXML
    private TableView<Contact> contactTable;
    @FXML
    private TableColumn<Contact, Integer> contactIdColumn;
    @FXML
    private TableColumn<Contact, String> contactNameColumn;
    @FXML
    private TableColumn<Contact, String> emailColumn;
    /** Contact table */

    @FXML
    private TextField contactNameField;
    @FXML
    private TextField emailField;

    /**
     * Instantiates a new Contact controller.
     */
//constructor
    public ContactController() {

    }

    /**
     * Add contact.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void addContact(ActionEvent actionEvent) {
        String contactName, email;
        contactName = contactNameField.getText();
        email = emailField.getText();
        DatabaseConnection.establishConnection();


        try{
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contacts (contact_name, Email) VALUES (?, ?)");
            preparedStatement.setString(1, contactName);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Contact added");
            alert.setContentText("Contact added successfully");
            alert.showAndWait();

            setContactTable();

            contactNameField.setText("");
            emailField.setText("");

            contactTable.setItems(ContactDAO.getAllContacts());
            contactTable.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(ContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Populate fields.
     */
//TODO populate fields with selected contact
    @FXML
    public void populateFields() {
        selectedContact = contactTable.getSelectionModel().getSelectedItem();
        contactNameField.setText(selectedContact.getContact_Name());
        emailField.setText(selectedContact.getEmail());
    }

    /**
     * Update contact.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void updateContact(ActionEvent actionEvent) {
        String contactName, email;
        int contact_ID;
        selectedContact = contactTable.getSelectionModel().getSelectedItem();
        contact_ID = selectedContact.getContact_ID();
        contactName = contactNameField.getText();
        email = emailField.getText();
        DatabaseConnection.establishConnection();

        String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_ID = ?";
        try {
            PreparedStatement updateContact = connection.prepareStatement(sql);
            updateContact.setString(1, contactName);
            updateContact.setString(2, email);
            updateContact.setInt(3, contact_ID);
            updateContact.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Contact updated");
            alert.setContentText("Contact updated successfully");
            alert.showAndWait();

            setContactTable();
            contactTable.setItems(ContactDAO.getAllContacts());
            contactTable.refresh();

            contactNameField.setText("");
            emailField.setText("");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Delete contact.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void deleteContact(ActionEvent actionEvent) {
        int contact_ID;
        selectedContact = contactTable.getSelectionModel().getSelectedItem();
        contact_ID = selectedContact.getContact_ID();
        DatabaseConnection.establishConnection();

        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        try {
            PreparedStatement deleteContact = connection.prepareStatement(sql);
            deleteContact.setInt(1, contact_ID);
            deleteContact.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Contact");
            alert.setHeaderText("Delete Contact");
            alert.setContentText("Are you sure you want to delete this contact?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {

                Alert alertSuccess = new Alert(Alert.AlertType.INFORMATION);
                alertSuccess.setTitle("Success");
                alertSuccess.setHeaderText("Contact deleted");
                alertSuccess.setContentText("Contact deleted successfully");
                alertSuccess.showAndWait();

                setContactTable();
                contactTable.setItems(ContactDAO.getAllContacts());
                contactTable.refresh();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Back button action.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
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

    /**
     * Set contact table.
     */
    public void setContactTable(){
        contactTable.setItems(contactList);
         contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
         contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
         emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));


    }

    @FXML
    private void handleTableRowClick() {
        // Get the selected appointment from the table
        Contact selectedContact = contactTable.getSelectionModel().getSelectedItem();

        if (selectedContact != null) {
            // Populate the fields with the selected contact's data
            contactNameField.setText(selectedContact.getContact_Name());
            emailField.setText(selectedContact.getEmail());

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection.establishConnection();
        setContactTable();
        contactTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleTableRowClick();
            }
        });

    }
}
