package com.javafx_project.models;

public class FirstLevelDivision {
    //This is like 'States' in the United States
    private int Division_ID;

    private String Division_Name;

    private String division;

    private String Create_Date;

    private String Created_By;

    private String Last_Update;

    private String Last_Updated_By;

    private Integer Country_ID;

    public FirstLevelDivision() {

    }

    public FirstLevelDivision(Integer divisionId, String divisionName, Integer countryId) {
        this.Division_ID = divisionId;
        this.Division_Name = divisionName;
        this.Country_ID = countryId;
    }

    public int getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(int division_ID) {
        this.Division_ID = division_ID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String create_Date) {
        this.Create_Date = create_Date;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String created_By) {
        this.Created_By = created_By;
    }

    public String getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(String last_Update) {
        this.Last_Update = last_Update;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String last_Updated_By) {
        this.Last_Updated_By = last_Updated_By;
    }

    public Integer getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(int country_ID) {
        this.Country_ID = country_ID;
    }


    public String getDivision_Name() {
        return Division_Name;
    }

    public void setDivision_Name(String division_Name) {
        Division_Name = division_Name;
    }
}

