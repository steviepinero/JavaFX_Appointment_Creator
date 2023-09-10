package com.javafx_project.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * The type Appointment.
 */
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
    private int User_ID;
    private int Contact_ID;
    private Contact contact;
    private String contactName;

    /**
     * Instantiates a new Appointment.
     *
     * @param appointment_ID  the appointment id
     * @param title           the title
     * @param description     the description
     * @param type            the type
     * @param location        the location
     * @param start           the start
     * @param end             the end
     * @param create_date     the create date
     * @param created_By      the created by
     * @param last_Update     the last update
     * @param last_Updated_By the last updated by
     * @param customer_ID     the customer id
     * @param User_ID         the user id
     * @param Contact_ID      the contact id
     */
    public Appointment(int appointment_ID, String title, String description, String type, String location, LocalDate start, LocalDate end, LocalDate create_date, String created_By, LocalDate last_Update, String last_Updated_By, int customer_ID, int User_ID, int Contact_ID) {
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
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointment_ID  the appointment id
     * @param title           the title
     * @param description     the description
     * @param location        the location
     * @param Contact_ID      the contact id
     * @param type            the type
     * @param start           the start
     * @param end             the end
     * @param customer_ID     the customer id
     * @param User_ID         the user id
     * @param create_date     the create date
     * @param created_By      the created by
     * @param last_Update     the last update
     * @param last_Updated_By the last updated by
     */
    public Appointment(int appointment_ID, String title, String description, String location, int Contact_ID, String type, LocalDate start, LocalDate end, int customer_ID, int User_ID, LocalDate create_date, String created_By, LocalDate last_Update, String last_Updated_By) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.Contact_ID = Contact_ID;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_ID = customer_ID;
        this.User_ID = User_ID;
        this.create_date = create_date;
        this.created_By = created_By;
        this.last_Update = last_Update;
        this.last_Updated_By = last_Updated_By;
    }


    /**
     * Instantiates a new Appointment.
     *
     * @param appointment_ID  the appointment id
     * @param title           the title
     * @param description     the description
     * @param location        the location
     * @param type            the type
     * @param startDate       the start date
     * @param endDate         the end date
     * @param customer_ID     the customer id
     * @param Contact_ID      the contact id
     * @param last_Updated_By the last updated by
     */
    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, int customer_ID, int Contact_ID, String last_Updated_By) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = startDate;
        this.end = endDate;
        this.customer_ID = customer_ID;
        this.Contact_ID = Contact_ID;
        this.last_Updated_By = last_Updated_By;

    }

    /**
     * Instantiates a new Appointment.
     */
    public Appointment() {

    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointment_ID  the appointment id
     * @param title           the title
     * @param description     the description
     * @param location        the location
     * @param type            the type
     * @param start           the start
     * @param end             the end
     * @param create_date     the create date
     * @param created_By      the created by
     * @param last_Update     the last update
     * @param last_Updated_By the last updated by
     * @param customer        the customer
     * @param User_ID         the user id
     * @param Contact_ID      the contact id
     */
    public Appointment(int appointment_ID, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Timestamp create_date, String created_By, Timestamp last_Update, String last_Updated_By, int customer, int User_ID, int Contact_ID) {
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
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }


    /**
     * Instantiates a new Appointment.
     *
     * @param title           the title
     * @param description     the description
     * @param location        the location
     * @param type            the type
     * @param startDate       the start date
     * @param endDate         the end date
     * @param customer        the customer
     * @param User_ID         the user id
     * @param Contact_ID      the contact id
     * @param created_By      the created by
     * @param last_Updated_By the last updated by
     * @param create_date     the create date
     * @param last_Update     the last update
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime startDate, LocalDateTime endDate, int customer, int User_ID, int Contact_ID, String created_By, String last_Updated_By, Timestamp create_date, Timestamp last_Update) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = LocalDate.from(startDate);
        this.end = LocalDate.from(endDate);
        this.customer_ID = customer;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
        this.created_By = created_By;
        this.last_Updated_By = last_Updated_By;
        this.create_date = LocalDate.from(create_date.toLocalDateTime());
        this.last_Update = LocalDate.from(last_Update.toLocalDateTime());

    }

    /**
     * Instantiates a new Appointment.
     *
     * @param appointmentId the appointment id
     * @param title         the title
     * @param type          the type
     * @param description   the description
     * @param start         the start
     * @param end           the end
     * @param customerId    the customer id
     */
    public Appointment(int appointmentId, String title, String type, String description, LocalDateTime start, LocalDateTime end, int customerId) {
        this.appointment_ID = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = LocalDate.from(start);
        this.end = LocalDate.from(end);
        this.customer_ID = customerId;

    }


    // getters and setters


    /**
     * Gets appointment id.
     *
     * @return the appointment id
     */
    public int getAppointment_ID() {
        return appointment_ID;
    }

    /**
     * Sets appointment id.
     *
     * @param appointment_ID the appointment id
     */
    public void setAppointment_ID(int appointment_ID) {
        this.appointment_ID = appointment_ID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets start.
     *
     * @return the start
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Sets start.
     *
     * @param start the start
     */
    public void setStart(LocalDate start) {
        this.start = start;
    }

    /**
     * Gets end.
     *
     * @return the end
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Sets end.
     *
     * @param end the end
     */
    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDate getCreate_date() {
        return create_date;
    }

    /**
     * Sets create date.
     *
     * @param create_date the create date
     */
    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreated_By() {
        return created_By;
    }

    /**
     * Sets created by.
     *
     * @param created_By the created by
     */
    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public LocalDate getLast_Update() {
        return last_Update;
    }

    /**
     * Sets last update.
     *
     * @param last_Update the last update
     */
    public void setLast_Update(LocalDate last_Update) {
        this.last_Update = last_Update;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public String getLast_Updated_By() {
        return last_Updated_By;
    }

    /**
     * Sets last updated by.
     *
     * @param last_Updated_By the last updated by
     */
    public void setLast_Updated_By(String last_Updated_By) {
        this.last_Updated_By = last_Updated_By;
    }

    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomer_ID() {
        return customer_ID;
    }

    /**
     * Sets customer id.
     *
     * @param customer_ID the customer id
     */
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * Sets user id.
     *
     * @param user_ID the user id
     */
    public void setUser_ID(int user_ID) {
        this.User_ID = user_ID;
    }

    /**
     * Gets contact id.
     *
     * @return the contact id
     */
    public int getContact_ID() {
        return Contact_ID;
    }

    /**
     * Gets contact name.
     *
     * @return the contact name
     */
    public String getContactName() { return contactName;}

    /**
     * Sets contact name.
     *
     * @param contactName the contact name
     */
    public void setContactName(String contactName) { this.contactName = contactName; }

    /**
     * Sets contact id.
     *
     * @param contact_ID the contact id
     */
    public void setContact_ID(int contact_ID) {
        this.Contact_ID = contact_ID;
    }


    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return start.getMonth().toString();
    }
}
