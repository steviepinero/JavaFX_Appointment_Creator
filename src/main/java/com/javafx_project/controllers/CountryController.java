package com.javafx_project.controllers;

import com.javafx_project.dao.CountryDAO;
import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.dao.UserDAO;
import com.javafx_project.models.Country;
import com.javafx_project.models.User;
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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.controllers.LoginController.loggedInUser;
import static com.javafx_project.dao.DatabaseConnection.connection;


public class CountryController implements Initializable {

    static ObservableList<Country> countryList = CountryDAO.getAllCountries();
    private static Country selectedCountry;

    /** Country table */
    @FXML
    private TableView<Country> countryTable;
    @FXML
    private TableColumn<Country, Integer> countryIdColumn;
    @FXML
    private TableColumn<Country, String> countryNameColumn;
    @FXML
    private TableColumn<Country, Timestamp> createDateColumn;
    @FXML
    private TableColumn<Country, String> createdByColumn;
    @FXML
    private TableColumn<Country, Timestamp> lastUpdateColumn;
    @FXML
    private TableColumn<Country, String> lastUpdatedByColumn;
    /** Country table */

    @FXML
    private TextField countryNameField;

    public CountryController() {
    }

    public void addCountry(ActionEvent actionEvent) {
        String countryName, createdBy, lastUpdatedBy;
        countryName = countryNameField.getText();
        createdBy = String.valueOf(LoginController.loggedInUser.getUser_Name());
        lastUpdatedBy = createdBy;
        DatabaseConnection.establishConnection();


        try {
            PreparedStatement addCountry = connection.prepareStatement("INSERT INTO countries (Country_Name, Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES (?,NOW(), ?,NOW(), ?)");
            addCountry.setString(1, countryName);
            addCountry.setString(2, createdBy);
            addCountry.setString(3, lastUpdatedBy);

            addCountry.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Country Added");
            alert.setHeaderText("Country Added");
            alert.setContentText("Country Added");
            alert.showAndWait();

            setCountryTable();

            countryNameField.setText("");

            countryTable.setItems(CountryDAO.getAllCountries());
            countryTable.refresh();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(CountryController.class.getName()).log(Level.SEVERE, null, e);
        }


    }

    public void setCountryTable() {
        countryTable.setItems(countryList);
        countryIdColumn.setCellValueFactory(new PropertyValueFactory<>("Country_ID"));
        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("Country_Name"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("Create_Date"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));

    }


    public void updateCountry(ActionEvent actionEvent) {
        String countryName, lastUpdatedBy;
        int country_ID;
        selectedCountry = countryTable.getSelectionModel().getSelectedItem();
        country_ID = selectedCountry.getCountry_ID();
        countryName = countryNameField.getText();
        lastUpdatedBy = String.valueOf(LoginController.loggedInUser.getUser_Name());
        DatabaseConnection.establishConnection();

        String sql = "UPDATE countries SET Country_Name = ?, Last_Update = NOW(), Last_Updated_By = ? WHERE Country_ID = ?";
        try {
            PreparedStatement updateCountry = connection.prepareStatement(sql);
            updateCountry.setString(1, countryName);
            updateCountry.setString(2, lastUpdatedBy);
            updateCountry.setInt(3, country_ID);
            updateCountry.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Country updated");
            alert.setContentText("Country updated successfully");
            alert.showAndWait();

            setCountryTable();
            countryTable.setItems(CountryDAO.getAllCountries());
            countryTable.refresh();

            countryNameField.setText("");

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCountry(ActionEvent actionEvent) {
        int country_ID;
        selectedCountry = countryTable.getSelectionModel().getSelectedItem();
        country_ID = selectedCountry.getCountry_ID();

        if (selectedCountry != null) {
            CountryDAO.deleteCountry(country_ID);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Country deleted");
            alert.setContentText("Country deleted successfully");
            alert.showAndWait();

            setCountryTable();
            countryTable.setItems(CountryDAO.getAllCountries());
            countryTable.refresh();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection.establishConnection();
        setCountryTable();

    }
}
