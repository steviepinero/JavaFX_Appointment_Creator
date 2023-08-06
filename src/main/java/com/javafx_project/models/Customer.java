package com.javafx_project.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Customer {

    private int customer_Id;
    private String customer_Name;
    private String address;
    private String postal_Code;
    private String phone;
    private LocalDateTime create_Date;
    private String created_By;
    private LocalDateTime last_Update;
    private String last_Updated_By;
    private int country_Id;
    private int division_Id;

    public Customer(int customer_Id, String customer_Name, String address, String postal_Code, String phone, LocalDateTime createDate, String createdBy, LocalDate lastUpdate, String lastUpdatedBy, Integer division_Id) {
        this.customer_Id = customer_Id;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.division_Id = division_Id;
    }

    public Customer() {

    }

    public Customer(int customer_Id, String customer_Name) {
        this.customer_Id = customer_Id;
        this.customer_Name = customer_Name;
    }

    public Customer(String customer_Name, String address, String postal_Code, String phone, String division_Id) {
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_Id = Integer.parseInt(division_Id);
    }

    public Customer(String customer_Name, String address, String postal_Code, String phone, String division_Id, String country_Id) {
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_Id = Integer.parseInt(division_Id);
        this.country_Id = Integer.parseInt(country_Id);
    }

    public Customer(int customer_Id, String customer_Name, String address, String postal_Code, String phone, String division_Id) {
        this.customer_Id = customer_Id;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.division_Id = Integer.parseInt(division_Id);
    }

    public Customer(int customer_Id, String customer_Name, String address, String postal_Code, String phone, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy, int country_Id, int division_Id) {
        this.customer_Id = customer_Id;
        this.customer_Name = customer_Name;
        this.address = address;
        this.postal_Code = postal_Code;
        this.phone = phone;
        this.createDate = LocalDate.parse(createDate).atStartOfDay();
        this.createdBy = createdBy;
        this.lastUpdate = LocalDate.parse(lastUpdate);
        this.lastUpdatedBy = lastUpdatedBy;
        this.country_Id = country_Id;
        this.division_Id = division_Id;
    }

    @Override
    public String toString() {
        return this.customer_Name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate.atStartOfDay();
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

    private LocalDateTime createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdatedBy;

    // getters and setters

    public int getCountry_Id() {
        return country_Id;
    }

    public void setCountry_Id(int country_Id) {
        this.country_Id = country_Id;
    }


    public int getCustomer_Id() {
        return customer_Id;
    }

    public void setCustomer_Id(int customer_Id) {
        this.customer_Id = customer_Id;
    }

    public String getCustomer_Name() {
        return customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        this.customer_Name = customer_Name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal_Code() {
        return postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        this.postal_Code = postal_Code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivision_Id() {
        return division_Id;
    }

    public void setDivision_Id(int division_Id) {
        this.division_Id = division_Id;
    }


}
