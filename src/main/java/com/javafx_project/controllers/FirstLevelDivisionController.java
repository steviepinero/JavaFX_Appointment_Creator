package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.dao.FirstLevelDivisionDAO;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.javafx_project.dao.DatabaseConnection.connection;
import static java.lang.Integer.valueOf;

public class FirstLevelDivisionController implements Initializable {

    ObservableList<FirstLevelDivision> divisionList = FirstLevelDivisionDAO.getAllDivisions();
    private FirstLevelDivision selectedDivision;
    /** Division table */
    @FXML
    private TableView<FirstLevelDivision> divisionTable;
    @FXML
    private TableColumn<FirstLevelDivision, Integer> divisionIdColumn;
    @FXML
    private TableColumn<FirstLevelDivision, String> divisionNameColumn;
    @FXML
    private TableColumn<FirstLevelDivision,Integer> countryIdColumn;
    @FXML
    private TableColumn<FirstLevelDivision, Timestamp> createDateColumn;
    @FXML
    private TableColumn<FirstLevelDivision, String> createdByColumn;
    @FXML
    private TableColumn<FirstLevelDivision, Timestamp> lastUpdateColumn;
    @FXML
    private TableColumn<FirstLevelDivision, String> lastUpdatedByColumn;
    /** Division table */

    @FXML
    private TextField divisionNameField;
    @FXML
    private ComboBox<Country> countryComboBox;

    public FirstLevelDivisionController(FirstLevelDivision selectedDivision, TableView<FirstLevelDivision> divisionTable, TableColumn<FirstLevelDivision, Integer> divisionIdColumn, TableColumn<FirstLevelDivision, String> divisionNameColumn, TableColumn<FirstLevelDivision, Integer> countryIdColumn, TextField divisionNameField, ComboBox<Country> countryComboBox) {
        this.selectedDivision = selectedDivision;
        this.divisionTable = divisionTable;
        this.divisionIdColumn = divisionIdColumn;
        this.divisionNameColumn = divisionNameColumn;
        this.countryIdColumn = countryIdColumn;
        this.divisionNameField = divisionNameField;
        this.countryComboBox = countryComboBox;
    }

    public FirstLevelDivisionController() {

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

    @FXML
    public void addDivision(ActionEvent actionEvent) {
        String divisionName, country;
        Integer countryId;
        divisionName = divisionNameField.getText();
        countryId = countryComboBox.getValue().getCountry_ID();
        country = String.valueOf(valueOf(countryId.toString()));
        DatabaseConnection.establishConnection();

        try {
            PreparedStatement addDivision = DatabaseConnection.getConnection().prepareStatement("INSERT INTO divisions (Division_Name, Country_ID) VALUES (?, ?)");
            addDivision.setString(1, divisionName);
            addDivision.setInt(2, countryId);
            addDivision.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Division Added");
            alert.setHeaderText("Division Added");
            alert.setContentText("Division Added successfully");
            alert.showAndWait();

            setDivisionTable();
            divisionTable.setItems(FirstLevelDivisionDAO.getAllDivisions());
            divisionTable.refresh();

            divisionNameField.setText("");
            countryComboBox.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void updateDivision(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        this.selectedDivision = divisionTable.getSelectionModel().getSelectedItem();

        int divisionId = this.selectedDivision.getDivision_ID();
        String divisionName = divisionNameField.getText();
        Integer countryId = countryComboBox.getValue().getCountry_ID();

        try {
            PreparedStatement updateDivision = DatabaseConnection.getConnection().prepareStatement("UPDATE divisions SET Division_Name = ?, Country_ID = ? WHERE Division_ID = ?");
            updateDivision.setString(1, divisionName);
            updateDivision.setInt(2, countryId);
            updateDivision.setInt(3, divisionId);
            updateDivision.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Division Updated");
            alert.setHeaderText("Division Updated");
            alert.setContentText("Division Updated successfully");
            alert.showAndWait();

            setDivisionTable();
            divisionTable.setItems(FirstLevelDivisionDAO.getAllDivisions());
            divisionTable.refresh();
            divisionNameField.setText("");
            countryComboBox.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(FirstLevelDivisionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void deleteDivision(ActionEvent actionEvent) {
        DatabaseConnection.establishConnection();
        this.selectedDivision = divisionTable.getSelectionModel().getSelectedItem();
        int divisionId = this.selectedDivision.getDivision_ID();
        try {
            PreparedStatement deleteDivision = DatabaseConnection.getConnection().prepareStatement("DELETE FROM divisions WHERE Division_ID = ?");
            deleteDivision.setInt(1, divisionId);
            deleteDivision.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Division Deleted");
            alert.setHeaderText("Division Deleted");
            alert.setContentText("Division Deleted successfully");
            alert.showAndWait();

            setDivisionTable();
            divisionTable.setItems(FirstLevelDivisionDAO.getAllDivisions());
            divisionTable.refresh();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(FirstLevelDivisionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }



    public void setDivisionTable() {
        divisionTable.setItems(divisionList);
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
        divisionNameColumn.setCellValueFactory(new PropertyValueFactory<>("Division_Name"));
        countryIdColumn.setCellValueFactory(new PropertyValueFactory<>("Country_ID"));
        createDateColumn.setCellValueFactory(new PropertyValueFactory<>("Create_Date"));
        createdByColumn.setCellValueFactory(new PropertyValueFactory<>("Created_By"));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Update"));
        lastUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("Last_Updated_By"));
        populateCountryBox();

    }

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
                countryComboBox.getItems().add(country);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            Logger.getLogger(FirstLevelDivisionController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void handleTableRowClick() {
        // Get the selected appointment from the table
        FirstLevelDivision selectedDivision = divisionTable.getSelectionModel().getSelectedItem();

        if (selectedDivision != null) {
            // Populate the fields with the selected Division's data
            divisionNameField.setText(selectedDivision.getDivision_Name());
            countryComboBox.setValue(new Country(selectedDivision.getCountry_ID(), ""));

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection.establishConnection();
        setDivisionTable();
        divisionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleTableRowClick();
            }
        });
    }
}
