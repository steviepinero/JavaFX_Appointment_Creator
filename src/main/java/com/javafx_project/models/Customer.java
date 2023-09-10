package com.javafx_project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Customer.
 */
public class Customer {

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Country_ID;
    private int Division_ID;

    /**
     * Instantiates a new Customer.
     *
     * @param Customer_ID     the customer id
     * @param customer_Name   the customer name
     * @param address         the address
     * @param postal_Code     the postal code
     * @param phone           the phone
     * @param Create_Date     the create date
     * @param Created_By      the created by
     * @param Last_Update     the last update
     * @param Last_Updated_By the last updated by
     * @param Division_ID     the division id
     */
    public Customer(int Customer_ID, String customer_Name, String address, String postal_Code, String phone, LocalDateTime Create_Date, String Created_By, LocalDateTime Last_Update, String Last_Updated_By, Integer Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;
    }

    /**
     * Instantiates a new Customer.
     */
    public Customer() {

    }

    /**
     * Instantiates a new Customer.
     *
     * @param Customer_ID   the customer id
     * @param customer_Name the customer name
     */
    public Customer(int Customer_ID, String customer_Name) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = customer_Name;
    }


    /**
     * Instantiates a new Customer.
     *
     * @param Customer_ID     the customer id
     * @param customer_Name   the customer name
     * @param address         the address
     * @param postal_Code     the postal code
     * @param phone           the phone
     * @param Create_Date     the create date
     * @param Created_By      the created by
     * @param Last_Update     the last update
     * @param Last_Updated_By the last updated by
     * @param Country_ID      the country id
     * @param Division_ID     the division id
     */
    public Customer(int Customer_ID, String customer_Name, String address, String postal_Code, String phone, String Create_Date, String Created_By, String Last_Update, String Last_Updated_By, int Country_ID, int Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.Create_Date = LocalDateTime.parse(Create_Date, formatter);
        this.Created_By = Created_By;
        this.Last_Update = LocalDateTime.parse(Last_Update, formatter);
        this.Last_Updated_By = Last_Updated_By;
        this.Country_ID = Country_ID;
        this.Division_ID = Division_ID;
    }


    @Override
    public String toString() {
        return this.Customer_Name;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets create date.
     *
     * @param create_Date the create date
     */
    public void setCreate_Date(LocalDate create_Date) {
        this.Create_Date = create_Date.atStartOfDay();
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreated_By() {
        return Created_By;
    }

    /**
     * Sets created by.
     *
     * @param created_By the created by
     */
    public void setCreated_By(String created_By) {
        this.Created_By = created_By;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets last update.
     *
     * @param last_Update the last update
     */
    public void setLast_Update(LocalDateTime last_Update) {
        this.Last_Update = last_Update;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    /**
     * Sets last updated by.
     *
     * @param last_Updated_By the last updated by
     */
    public void setLast_Updated_By(String last_Updated_By) {
        this.Last_Updated_By = last_Updated_By;
    }


    // getters and setters

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public int getCountry_ID() {
        return Country_ID;
    }

    /**
     * Sets country id.
     *
     * @param country_ID the country id
     */
    public void setCountry_ID(int country_ID) {
        this.Country_ID = country_ID;
    }


    /**
     * Gets customer id.
     *
     * @return the customer id
     */
    public int getCustomer_ID() {
        return Customer_ID;
    }

    /**
     * Sets customer id.
     *
     * @param customer_ID the customer id
     */
    public void setCustomer_ID(int customer_ID) {
        this.Customer_ID = customer_ID;
    }

    /**
     * Gets customer name.
     *
     * @return the customer name
     */
    public String getCustomer_Name() {
        return Customer_Name;
    }

    /**
     * Sets customer name.
     *
     * @param customer_Name the customer name
     */
    public void setCustomer_Name(String customer_Name) {
        this.Customer_Name = customer_Name;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.Address = address;
    }

    /**
     * Gets postal code.
     *
     * @return the postal code
     */
    public String getPostal_Code() {
        return Postal_Code;
    }

    /**
     * Sets postal code.
     *
     * @param postal_Code the postal code
     */
    public void setPostal_Code(String postal_Code) {
        this.Postal_Code = postal_Code;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.Phone = phone;
    }

    /**
     * Gets division id.
     *
     * @return the division id
     */
    public int getDivision_ID() {
        return Division_ID;
    }

    /**
     * Sets division id.
     *
     * @param division_ID the division id
     */
    public void setDivision_ID(int division_ID) {
        this.Division_ID = division_ID;
    }


}
