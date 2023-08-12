package com.javafx_project.controllers;

import com.javafx_project.dao.CountryDAO;
import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.models.Country;
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
    /** Country table */

    @FXML
    private TextField countryNameField;

    public void addCountry(ActionEvent actionEvent) {
        String countryName, countryID;
        countryName = countryNameField.getText();
        DatabaseConnection.establishConnection();

        try {
            PreparedStatement addCountry = DatabaseConnection.getConnection().prepareStatement("INSERT INTO countries (Country_Name) VALUES (?)");
            addCountry.setString(1, countryName);
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
        countryIdColumn.setCellValueFactory(new PropertyValueFactory<>("country_ID"));
        countryNameColumn.setCellValueFactory(new PropertyValueFactory<>("country_Name"));
    }


    public void updateCountry(ActionEvent actionEvent) {
        String countryName;
        int country_ID;
        selectedCountry = countryTable.getSelectionModel().getSelectedItem();
        country_ID = selectedCountry.getCountry_ID();
        countryName = countryNameField.getText();
        DatabaseConnection.establishConnection();

        String sql = "UPDATE countries SET Country_Name = ? WHERE Country_ID = ?";
        try {
            PreparedStatement updateCountry = connection.prepareStatement(sql);
            updateCountry.setString(1, countryName);
            updateCountry.setInt(2, country_ID);
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
        CountryDAO.deleteCountry(country_ID);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Country deleted");
        alert.setContentText("Country deleted successfully");
        alert.showAndWait();

        setCountryTable();
        countryTable.setItems(CountryDAO.getAllCountries());
        countryTable.refresh();
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
