package com.javafx_project.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Country.
 */
public class Country {
    private int Country_ID;

    private String Country_Name;

    private LocalDateTime Create_Date;

    private String Created_By;

    private LocalDateTime Last_Update;

    private String Last_Updated_By;

    /**
     * Instantiates a new Country.
     */
    public Country() {

    };

    /**
     * Instantiates a new Country.
     *
     * @param countryId     the country id
     * @param countryName   the country name
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     */
//will need to add create and last update
    public Country(Integer countryId, String countryName, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.Country_ID = countryId;
        this.Country_Name = countryName;
        this.Create_Date = createDate.toLocalDateTime();
        this.Created_By = createdBy;
        this.Last_Update = lastUpdate.toLocalDateTime();
        this.Last_Updated_By = lastUpdatedBy;
    }

    /**
     * Instantiates a new Country.
     *
     * @param countryId   the country id
     * @param countryName the country name
     */
    public Country(int countryId, String countryName) {
        this.Country_ID = countryId;
        this.Country_Name = countryName;
    }

    /**
     * Instantiates a new Country.
     *
     * @param countryId     the country id
     * @param countryName   the country name
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lastUpdate    the last update
     * @param lastUpdatedBy the last updated by
     */
    public Country(Integer countryId, String countryName, String createDate, String createdBy, String lastUpdate, String lastUpdatedBy) {
        this.Country_ID = countryId;
        this.Country_Name = countryName;
        this.Create_Date = LocalDateTime.parse(String.valueOf(createDate));
        this.Created_By = createdBy;
        this.Last_Update = LocalDateTime.parse(lastUpdate);
        this.Last_Updated_By = lastUpdatedBy;
    }

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
     * Gets country name.
     *
     * @return the country name
     */
    public String getCountry_Name() {
        return Country_Name;
    }

    /**
     * Sets country name.
     *
     * @param country_Name the country name
     */
    public void setCountry_Name(String country_Name) {
        this.Country_Name = country_Name;
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
    public void setCreate_Date(LocalDateTime create_Date) {
        this.Create_Date = create_Date;
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

    @Override
    public String toString() {
        return Country_ID + " - " + Country_Name;
    }
}
