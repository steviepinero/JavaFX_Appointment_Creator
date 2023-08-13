package com.javafx_project.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Country {
    private int Country_ID;

    private String Country_Name;

    private LocalDateTime Create_Date;

    private String Created_By;

    private LocalDateTime Last_Update;

    private String Last_Updated_By;

    public Country() {

    };

    //will need to add create and last update
    public Country(Integer countryId, String countryName, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.Country_ID = countryId;
        this.Country_Name = countryName;
        this.Create_Date = createDate.toLocalDateTime();
        this.Created_By = createdBy;
        this.Last_Update = lastUpdate.toLocalDateTime();
        this.Last_Updated_By = lastUpdatedBy;
    }

    public Country(int countryId, String countryName) {
        this.Country_ID = countryId;
        this.Country_Name = countryName;
    }

    public Country(Integer countryId, String countryName, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.Country_ID = countryId;
        this.Country_Name = countryName;
        this.Create_Date = LocalDateTime.parse(String.valueOf(createDate));
        this.Created_By = createdBy;
        this.Last_Update = LocalDateTime.parse(lastUpdate);
        this.Last_Updated_By = lastUpdatedBy;
    }

    public int getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        this.Country_ID = country_ID;
    }

    public String getCountry_Name() {
        return Country_Name;
    }

    public void setCountry_Name(String country_Name) {
        this.Country_Name = country_Name;
    }

    public LocalDateTime getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(LocalDateTime create_Date) {
        this.Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        this.Created_By = created_By;
    }

    public LocalDateTime getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        this.Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.Last_Updated_By = last_Updated_By;
    }

    @Override
    public String toString() {
        return Country_ID + " - " + Country_Name;
    }
}
