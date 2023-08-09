package com.javafx_project.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate start;
    private LocalDate end;
    private LocalDate create_date;
    private String created_By;
    private LocalDate last_Update;
    private String last_Updated_By;
    private int customer_ID;
    private Customer customer;
    private int user_Id;
    private int contact_Id;
    private Contact contact;

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customer_ID, int user_Id, int contact_Id, String last_Updated_By) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customer_ID = customer_ID;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
        this.last_Updated_By = last_Updated_By;
    }

    public Appointment(int appointment_ID, String title, String description, String type, String location, LocalDate start, LocalDate end, LocalDate create_date, String created_By, LocalDate last_Update, String last_Updated_By, int customer_ID, int user_Id, int contact_Id) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create_date = create_date;
        this.created_By = created_By;
        this.last_Update = last_Update;
        this.last_Updated_By = last_Updated_By;
        this.customer_ID = customer_ID;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
    }

    public Appointment(int appointment_ID, String title, String description, String location, int contact_Id, String type, LocalDate start, LocalDate end, int customer_ID, int user_Id) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact_Id = contact_Id;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_Id = user_Id;
    }

    public Appointment(int appointment_ID, String title, String description, String location, int contact_Id, String type, LocalDate start, LocalDate end, int customer_ID, int user_Id, LocalDate create_date, String created_By, LocalDate last_Update, String last_Updated_By) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact_Id = contact_Id;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.user_Id = user_Id;
        this.create_date = create_date;
        this.created_By = created_By;
        this.last_Update = last_Update;
        this.last_Updated_By = last_Updated_By;
    }

    public Appointment(String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customer_ID, int user_Id, int contact_Id, String created_By, String last_Updated_By) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customer_ID = customer_ID;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
        this.created_By = created_By;
        this.last_Updated_By = last_Updated_By;


    }

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customer_ID, int contact_Id, String last_Updated_By) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customer_ID = customer_ID;
        this.contact_Id = contact_Id;
        this.last_Updated_By = last_Updated_By;

    }

    public Appointment() {

    }

    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Timestamp create_date, String created_By, Timestamp last_Update, String last_Updated_By, int customer, int user_Id, int contact_Id) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = LocalDate.from(start);
        this.end = LocalDate.from(end);
        this.create_date = LocalDate.from(create_date.toLocalDateTime());
        this.created_By = created_By;
        this.last_Update = LocalDate.from(last_Update.toLocalDateTime());
        this.last_Updated_By = last_Updated_By;
        this.customer_ID = customer;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
    }

    public Appointment(String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customer, int user_Id, int contact_Id, String created_By, String last_Updated_By, Timestamp create_date, Timestamp last_Update) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customer_ID = customer;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
        this.created_By = created_By;
        this.last_Updated_By = last_Updated_By;
        this.create_date = LocalDate.from(create_date.toLocalDateTime());
        this.last_Update = LocalDate.from(last_Update.toLocalDateTime());

    }

    public Appointment(String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, int customer, int user_Id, int contact_Id, String created_By, String last_Updated_By, Timestamp create_date, Timestamp last_Update) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = LocalDate.from(startDate);
        this.end = LocalDate.from(endDate);
        this.customer_ID = customer;
        this.user_Id = user_Id;
        this.contact_Id = contact_Id;
        this.created_By = created_By;
        this.last_Updated_By = last_Updated_By;
        this.create_date = LocalDate.from(create_date.toLocalDateTime());
        this.last_Update = LocalDate.from(last_Update.toLocalDateTime());

    }


    // getters and setters


    public int getAppointment_ID() {
        return appointment_ID;
    }

    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
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

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    public String getCreated_By() {
        return created_By;
    }

    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    public LocalDate getLast_Update() {
        return last_Update;
    }

    public void setLast_Update(LocalDate last_Update) {
        this.last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.last_Updated_By = last_Updated_By;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public int getContact_Id() {
        return contact_Id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setContact_Id(int contact_Id) {
        this.contact_Id = contact_Id;
    }

    public void setContactId(String id) {
        this.contact_Id = Integer.parseInt(id);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
