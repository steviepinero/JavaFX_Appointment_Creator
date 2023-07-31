package com.javafx_project.models;

import java.time.LocalDate;

public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate start;
    private LocalDate end;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdatedBy;
    private int customerIdInt;
    private Customer customerId;
    private int userId;
    private int contactId;
    private Contact contact;

    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customerIdInt, int userId, int contactId, String lastUpdatedBy) {

    }

    public Appointment(int appointmentId, String title, String description, String type, String location, LocalDate start, LocalDate end, LocalDate createDate, String createdBy, LocalDate lastUpdate, String lastUpdatedBy, int customerIdInt, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerIdInt = customerIdInt;
        this.userId = userId;
        this.contactId = contactId;
    }

    public Appointment(int appointmentId, String title, String description, String location, int contactId, String type, LocalDate start, LocalDate end, int customerIdInt, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerIdInt = customerIdInt;
        this.userId = userId;
    }

    public Appointment(int appointmentId, String title, String description, String location, int contactId, String type, LocalDate start, LocalDate end, int customerIdInt, int userId, LocalDate createDate, String createdBy, LocalDate lastUpdate, String lastUpdatedBy) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerIdInt = customerIdInt;
        this.userId = userId;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Appointment(String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customerIdInt, int userId, int contactId, String createdBy, String lastUpdatedBy) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customerIdInt = customerIdInt;
        this.userId = userId;
        this.contactId = contactId;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;


    }

    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customerIdInt, int contactId, String lastUpdatedBy) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customerIdInt = customerIdInt;
        this.contactId = contactId;
        this.lastUpdatedBy = lastUpdatedBy;

    }

    public Appointment() {

    }


    // getters and setters


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCustomerIdInt() {
        return customerIdInt;
    }

    public void setCustomerIdInt(int customerIdInt) {
        this.customerIdInt = customerIdInt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setContactId(String id) {
        this.contactId = Integer.parseInt(id);
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }
}
