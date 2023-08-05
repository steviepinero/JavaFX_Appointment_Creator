package com.javafx_project.models;

public class User {
    private int User_ID;
    private String User_Name;

    private String password;

    private String Create_Date;

    private String Created_By;

    private String Last_Update;

    private String Last_Updated_By;

    public User() {

    }

    // getters and setters

    public int getUserId() {
        return User_ID;
    }

    public void setUserId(int userId) {
        this.User_ID = userId;
    }

    public String getUserName() {
        return User_Name;
    }

    public void setUserName(String userName) {
        this.User_Name = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateDate() {
        return Create_Date;
    }

    public void setCreateDate(String createDate) {
        this.Create_Date = createDate;
    }

    public String getCreatedBy() {
        return Created_By;
    }

    public void setCreatedBy(String createdBy) {
        this.Created_By = createdBy;
    }

    public String getLastUpdate() {
        return Last_Update;
    }

    public void setLastUpdate(String lastUpdate) {
        this.Last_Update = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return Last_Updated_By;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.Last_Updated_By = lastUpdatedBy;
    }
}
