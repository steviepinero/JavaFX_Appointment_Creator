package com.javafx_project.controllers;

import com.javafx_project.dao.DatabaseConnection;
import com.javafx_project.dao.UserDAO;
import com.javafx_project.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;

/**
 * The type Login controller.
 */
public class LoginController extends Pane {
    /**
     * The constant loggedInUser.
     */
    public static User loggedInUser;
    /**
     * The Username field.
     */
    @FXML
    public TextField usernameField;

    /**
     * The Password field.
     */
    @FXML
    public PasswordField passwordField;

    /**
     * The Zone id label.
     */
    @FXML
    public Label zoneIdLabel;
    /**
     * The User dao.
     */
    public UserDAO userDAO;

    @FXML
    private Button loginButton;
    @FXML
    private Label loginLabel;

    /**
     * Instantiates a new Login controller.
     *
     * @param usernameField the username field
     * @param passwordField the password field
     * @param loginButton   the login button
     */
    public LoginController(TextField usernameField, PasswordField passwordField, Button loginButton) {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.loginButton = loginButton;
    }

    /**
     * Instantiates a new Login controller.
     */
    public LoginController() {
        // No-argument constructor
    }

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        Locale currentLocale = Locale.getDefault();
        if ("fr".equals(currentLocale.getLanguage())) {
            // Set text to French
            loginLabel.setText("Page de Connexion");
            loginButton.setText("Connexion");
            usernameField.setPromptText("Nom d'utilisateur");
            passwordField.setPromptText("Mot de passe");
            zoneIdLabel.setText("Zone ID: " + ZoneId.systemDefault());
            ZoneId zoneId = ZoneId.of("Europe/Paris");
            zoneId.getRules();
        } else {
            // Default to English
            loginButton.setText("Login");
            usernameField.setPromptText("Username");
            passwordField.setPromptText("Password");
            zoneIdLabel.setText("Zone ID: " + ZoneId.systemDefault());
            ZoneId zoneId = ZoneId.of("America/New_York");
            zoneId.getRules();
            System.out.println("Defaulting to English");
        }


    }

    /**
     * Login.
     *
     * @param event the event
     * @throws SQLException the sql exception
     */
    @FXML
    public void login(ActionEvent event) throws SQLException {
        // Establish connection
        DatabaseConnection.establishConnection();

        String enteredUsername = usernameField.getText();
        String enteredPassword = passwordField.getText();

        // Log the entered username immediately
        UserActivityLogger.logUserActivity(enteredUsername, false); // Assume unsuccessful login initially

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByUsername(enteredUsername);
        System.out.print("~login method \n");

        if (user == null) {
            // User doesn't exist in the database
            showLoginError();
            return;
        }

        User userId = userDAO.getUserByUserId(user.getUser_ID());
        System.out.print(userId);
        //save logged in username to a variable
        loggedInUser = user;
        System.out.print(loggedInUser);

        //save logged in user_ID to a variable
        User loggedInUserID = userId;
        int savedID = loggedInUserID.getUser_ID();
        System.out.println("Fetched User: " + savedID);
        System.out.println("Fetched User_ID: " + loggedInUserID.getUser_ID());

        //check if username and password match
        if (user.getPassword().equals(enteredPassword)) {
            // login successful
            loggedInUser = user;
            // Update the log to indicate successful login
            UserActivityLogger.logUserActivity(enteredUsername, true);
            // navigate to the next scene.
            try {
                FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/com/javafx_project/homeView.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) loginButton.getScene().getWindow();

                // Create new scene and set it on the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);

                //check for upcoming appointments
                AppointmentController.checkUpcomingAppointments();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // login failed
            showLoginError();
        }
    }


    /**
     * Show login error.
     */
    public void showLoginError() {
        Locale currentLocale = Locale.getDefault();
        if ("fr".equals(currentLocale.getLanguage())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Connexion");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect.");

            alert.showAndWait();
        } else {//default to english
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password.");

            alert.showAndWait();
        }
    }

}
