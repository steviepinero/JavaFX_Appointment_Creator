package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Contact;
import com.javafx_project.dao.ContactDAO;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.dao.DatabaseConnection.connection;


public class ContactController implements Initializable {
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

    //constructor
    public ContactController() {

    }
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

    @FXML
    public void updateContact(ActionEvent actionEvent) {
    }
    @FXML
    public void deleteContact(ActionEvent actionEvent) {
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

    public void setContactTable(){
        contactTable.setItems(contactList);
    contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
    contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_Name"));
    emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection.establishConnection();
        setContactTable();

    }
}
