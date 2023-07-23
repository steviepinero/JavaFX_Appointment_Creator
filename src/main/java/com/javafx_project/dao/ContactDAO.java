package com.javafx_project.dao;

import com.javafx_project.models.Contact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public List<Contact> getAllContacts() {
        String sql = "SELECT * FROM contacts";
        List<Contact> contacts = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setContactId(rs.getInt("contact_id"));
                contact.setContactName(rs.getString("contact_name"));
                contact.setEmail(rs.getString("email"));
                contacts.add(new Contact(contact.getContactId(), contact.getContactName(), contact.getEmail()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contacts;
    }
}
