package com.javafx_project.models;

public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    public Contact() {

    }

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public Contact(int id, String name) {
        this.contactId = id;
        this.contactName = name;
    }

    @Override
    public String toString() {
        return contactId + " - " + contactName;
    }
    // getters and setters


    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
