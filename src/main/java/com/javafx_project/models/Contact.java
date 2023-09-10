package com.javafx_project.models;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * The type Contact.
 */
public class Contact {
    private int Contact_ID;
    /**
     * The Contact id.
     */
    int contactID = Contact_ID;

    private String Contact_Name;
    private String Email;

    private String contactName;


    /**
     * Instantiates a new Contact.
     */
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


    /**
     * Instantiates a new Contact.
     *
     * @param contact_ID   the contact id
     * @param Contact_Name the contact name
     * @param email        the email
     */
    public Contact(int contact_ID, String Contact_Name, String email) {
        this.Contact_ID = contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = email;

    }

    /**
     * Instantiates a new Contact.
     *
     * @param id   the id
     * @param name the name
     */
    public Contact(int id, String name) {
        this.Contact_ID = id;
        this.Contact_Name = name;
    }

    @Override
    public String toString() {
        return Contact_ID + " - " + Contact_Name;
    }
    // getters and setters


    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Sets contact id.
     *
     * @param contact_ID the contact id
     */
    public void setContact_ID(int contact_ID) {
        this.Contact_ID = contact_ID;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContact_Name() {
        return Contact_Name;
    }

    /**
     * Sets contact name.
     *
     * @param contact_Name the contact name
     */
    public void setContact_Name(String contact_Name) {
        this.Contact_Name = contact_Name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.Email = email;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
