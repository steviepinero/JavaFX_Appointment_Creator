module com.javafx_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.security.jgss;
    requires org.testng;
    requires junit;
    requires mockito.all;

    opens com.javafx_project to javafx.fxml;
    exports com.javafx_project;
    exports com.javafx_project.controllers;
    opens com.javafx_project.models to javafx.base;
    opens com.javafx_project.controllers to javafx.fxml;
}