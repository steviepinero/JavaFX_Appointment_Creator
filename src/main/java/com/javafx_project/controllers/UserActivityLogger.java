package com.javafx_project.controllers;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserActivityLogger {
    private static final String FILE_NAME = "login_activity.txt";

    /**
     * Logs the user activity.
     *
     * @param  username         the username of the user
     * @param  loginSuccessful  true if the login was successful, false otherwise
     */
    public static void logUserActivity(String username, boolean loginSuccessful) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {  // true means append to file
            String status = loginSuccessful ? "SUCCESSFUL" : "FAILED";
            String logMessage = String.format("%s - Login attempt by user: %s was %s%n", LocalDateTime.now(), username, status);
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
