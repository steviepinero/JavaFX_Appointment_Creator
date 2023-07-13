package com.javafx_project.models;

public class Country {
    private int countryID;

    private String country;

    private String createDate;

    private String createdBy;

    private String lastUpdate;

    private String lasteUpdatedBy;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLasteUpdatedBy() {
        return lasteUpdatedBy;
    }

    public void setLasteUpdatedBy(String lasteUpdatedBy) {
        this.lasteUpdatedBy = lasteUpdatedBy;
    }
}
