package com.javafx_project.dao;

import com.javafx_project.models.Contact;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO {
    public static ObservableList<Contact> getAllContacts() {
        String sql = "SELECT * FROM contacts ORDER BY Contact_ID";
        ObservableList<Contact> contactList = javafx.collections.FXCollections.observableArrayList();

        try {
            PreparedStatement loadContacts = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = loadContacts.executeQuery();
            {

                while (rs.next()) {
                    Integer contact_ID = rs.getInt("Contact_ID");
                    String contact_Name = rs.getString("Contact_Name");
                    String email = rs.getString("Email");
                    Contact contact = new Contact(contact_ID, contact_Name, email);

                    contactList.add(contact);
                }
            }
            return contactList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void deleteContact(int contact_ID) {
        String sql = "DELETE FROM contacts WHERE Contact_ID = ?";
        try {
            PreparedStatement deleteContact = DatabaseConnection.getConnection().prepareStatement(sql);
            deleteContact.setInt(1, contact_ID);
            deleteContact.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





}

