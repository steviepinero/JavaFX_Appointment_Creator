module com.javafx_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;
    requires java.security.jgss;

    opens com.javafx_project to javafx.fxml;
    exports com.javafx_project;
    exports com.javafx_project.controllers;
    opens com.javafx_project.models to javafx.base;
    opens com.javafx_project.controllers to javafx.fxml;
}