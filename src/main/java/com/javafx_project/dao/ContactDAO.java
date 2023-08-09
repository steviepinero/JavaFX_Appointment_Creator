package com.javafx_project.dao;

import com.javafx_project.models.Contact;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public ObservableList<Contact> getAllContacts() {
        String sql = "SELECT * FROM contacts";
        ObservableList<Contact> contacts = javafx.collections.FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setContact_ID(rs.getInt("Contact_ID"));
                contact.setContact_Name(rs.getString("Contact_Name"));
                contact.setEmail(rs.getString("Email"));
                contacts.add(new Contact(contact.getContact_ID(), contact.getContact_Name(), contact.getEmail()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contacts;
    }
}
