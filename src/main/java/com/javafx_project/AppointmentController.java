package com.javafx_project;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AppointmentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private TableColumn<?, ?> appointment_ID_Column;

    @FXML
    private ComboBox<?> contactBox;

    @FXML
    private TableColumn<?, ?> contactColumn;

    @FXML
    private ComboBox<?> customerBox;

    @FXML
    private TableColumn<?, ?> customer_ID_Column;

    @FXML
    private Button deleteAppointmentButton;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TableColumn<?, ?> endDateTimeColumn;

    @FXML
    private TableColumn<?, ?> endDateTimeColumn1;

    @FXML
    private TextField endTimeField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField postalCodeField;

    @FXML
    private TableColumn<?, ?> startDateTimeColumn;

    @FXML
    private TableColumn<?, ?> startDateTimeColumn1;

    @FXML
    private TextField startTimeField;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> titleColumn;

    @FXML
    private TextField titleField;

    @FXML
    private TableColumn<?, ?> typeColumn;

    @FXML
    private Button updateAppointmentButton;

    @FXML
    private TableColumn<?, ?> user_ID_Column;

    @FXML
    void initialize() {
        assert addAppointmentButton != null : "fx:id=\"addAppointmentButton\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert appointment_ID_Column != null : "fx:id=\"appointment_ID_Column\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert contactBox != null : "fx:id=\"contactBox\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert contactColumn != null : "fx:id=\"contactColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert customerBox != null : "fx:id=\"customerBox\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert customer_ID_Column != null : "fx:id=\"customer_ID_Column\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert deleteAppointmentButton != null : "fx:id=\"deleteAppointmentButton\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert descriptionColumn != null : "fx:id=\"descriptionColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert descriptionField != null : "fx:id=\"descriptionField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert endDateTimeColumn != null : "fx:id=\"endDateTimeColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert endDateTimeColumn1 != null : "fx:id=\"endDateTimeColumn1\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert endTimeField != null : "fx:id=\"endTimeField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert phoneNumberField != null : "fx:id=\"phoneNumberField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert postalCodeField != null : "fx:id=\"postalCodeField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert startDateTimeColumn != null : "fx:id=\"startDateTimeColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert startDateTimeColumn1 != null : "fx:id=\"startDateTimeColumn1\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert startTimeField != null : "fx:id=\"startTimeField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert titleColumn != null : "fx:id=\"titleColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert titleField != null : "fx:id=\"titleField\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert typeColumn != null : "fx:id=\"typeColumn\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert updateAppointmentButton != null : "fx:id=\"updateAppointmentButton\" was not injected: check your FXML file 'appointmentView.fxml'.";
        assert user_ID_Column != null : "fx:id=\"user_ID_Column\" was not injected: check your FXML file 'appointmentView.fxml'.";

    }

}