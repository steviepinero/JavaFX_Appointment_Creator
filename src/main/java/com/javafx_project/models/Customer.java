package com.javafx_project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDate Last_Update;
    private String Last_Updated_By;
    private int Country_ID;
    private int Division_ID;

    public Customer(int Customer_ID, String customer_Name, String address, String postal_Code, String phone, LocalDateTime Create_Date, String Created_By, LocalDate Last_Update, String Last_Updated_By, Integer Division_ID) {
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

    public Customer() {

    }

    public Customer(int Customer_ID, String customer_Name) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = customer_Name;
    }


    public Customer(int Customer_ID, String customer_Name, String address, String postal_Code, String phone, String Create_Date, String Created_By, String Last_Update, String Last_Updated_By, int Country_ID, int Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = customer_Name;
        this.Address = address;
        this.Postal_Code = postal_Code;
        this.Phone = phone;
        this.Create_Date = LocalDate.parse(Create_Date).atStartOfDay();
        this.Created_By = Created_By;
        this.Last_Update = LocalDate.parse(Last_Update);
        this.Last_Updated_By = Last_Updated_By;
        this.Country_ID = Country_ID;
        this.Division_ID = Division_ID;
    }

    @Override
    public String toString() {
        return this.Customer_Name;
    }

    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(LocalDate create_Date) {
        this.Create_Date = create_Date.atStartOfDay();
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        this.Created_By = created_By;
    }

    public LocalDate getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(LocalDate last_Update) {
        this.Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.Last_Updated_By = last_Updated_By;
    }


    // getters and setters

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        this.Country_ID = country_ID;
    }


    public int getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(int customer_ID) {
        this.Customer_ID = customer_ID;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.Customer_Name = customer_Name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        this.Postal_Code = postal_Code;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.Division_ID = division_ID;
    }


}
