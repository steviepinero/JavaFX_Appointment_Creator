package com.javafx_project.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Contact {
    private int Contact_ID;
    int contactID = Contact_ID;

    private String Contact_Name;
    private String Email;

    private String contactName;



    public Contact() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return contactID == contact.contactID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactID);
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

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
