package com.javafx_project.models;

import java.sql.Date;

/**
 * The type First level division.
 */
public class FirstLevelDivision {
    //This is like 'States' in the United States
    private int Division_ID;

    private String Division_Name;

    private String division;

    private Date Create_Date;

    private String Created_By;

    private Date Last_Update;

    private String Last_Updated_By;

    private Integer Country_ID;

    /**
     * Instantiates a new First level division.
     */
    public FirstLevelDivision() {

    }

    /**
     * Instantiates a new First level division.
     *
     * @param divisionId      the division id
     * @param divisionName    the division name
     * @param countryId       the country id
     * @param Create_Date     the create date
     * @param Created_By      the created by
     * @param Last_Update     the last update
     * @param Last_Updated_By the last updated by
     */
    public FirstLevelDivision(Integer divisionId, String divisionName, Integer countryId, Date Create_Date, String Created_By, Date Last_Update, String Last_Updated_By) {
        this.Division_ID = divisionId;
        this.Division_Name = divisionName;
        this.Country_ID = countryId;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    /**
     * Instantiates a new First level division.
     *
     * @param divisionId   the division id
     * @param divisionName the division name
     */
    public FirstLevelDivision(int divisionId, String divisionName) {
        this.Division_ID = divisionId;
        this.Division_Name = divisionName;
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

    /**
     * Gets division.
     *
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets division.
     *
     * @param division the division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public Date getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets create date.
     *
     * @param create_Date the create date
     */
    public void setCreate_Date(String create_Date) {
        this.Create_Date = Date.valueOf(create_Date);
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
    public Date getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets last update.
     *
     * @param last_Update the last update
     */
    public void setLast_Update(String last_Update) {
        this.Last_Update = Date.valueOf(last_Update);
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

    /**
     * Gets country id.
     *
     * @return the country id
     */
    public Integer getCountry_ID() {
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
     * Gets division name.
     *
     * @return the division name
     */
    public String getDivision_Name() {
        return Division_Name;
    }

    /**
     * Sets division name.
     *
     * @param division_Name the division name
     */
    public void setDivision_Name(String division_Name) {
        Division_Name = division_Name;
    }

    @Override
    public String toString() {
        return Division_ID + " - " + Division_Name;
    }
}

