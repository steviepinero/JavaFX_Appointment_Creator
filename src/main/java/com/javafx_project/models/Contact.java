package com.javafx_project.models;

import java.sql.Timestamp;

public class Contact {
    private int Contact_ID;
    private String Contact_Name;
    private String Email;


    public Contact() {

    }

    public Contact(int contact_ID, String Contact_Name, String email) {
        this.Contact_ID = contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = email;

    }

    public Contact(int id, String name) {
        this.Contact_ID = id;
        this.Contact_Name = name;
    }

    @Override
    public String toString() {
        return Contact_ID + " - " + Contact_Name;
    }
    // getters and setters


    public int getContact_ID() {
        return Contact_ID;
    }

    public void setContact_ID(int contact_ID) {
        this.Contact_ID = contact_ID;
    }

    public String getContact_Name() {
        return Contact_Name;
    }

    public void setContact_Name(String contact_Name) {
        this.Contact_Name = contact_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }
}
