module com.javafx_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.javafx_project to javafx.fxml;
    exports com.javafx_project;
    exports com.javafx_project.controllers;
    opens com.javafx_project.controllers to javafx.fxml;
}