package com.javafx_project.models;

import com.javafx_project.dao.UserDAO;

public class User extends UserDAO {
    private int User_ID;
    private String User_Name;

    private String password;

    private String Create_Date;

    private String Created_By;

    private String Last_Update;

    private String Last_Updated_By;

    public User() {

    }

    public User(int userId, String userName, String password, String createDate, String createdBy, String lasUpdate, String lastUpdatedBy) {
        this.User_ID = userId;
        this.User_Name = userName;
        this.password = password;
        this.Create_Date = createDate;
        this.Created_By = createdBy;
        this.Last_Update = lasUpdate;
        this.Last_Updated_By = lastUpdatedBy;
    }

    // getters and setters

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int userId) {
        this.User_ID = userId;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String userName) {
        this.User_Name = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String createDate) {
        this.Create_Date = createDate;
    }

    public String getCreated_By() {
        return Created_By;
    }

    public void setCreated_By(String createdBy) {
        this.Created_By = createdBy;
    }

    public String getLast_Update() {
        return Last_Update;
    }

    public void setLast_Update(String lastUpdate) {
        this.Last_Update = lastUpdate;
    }

    public String getLast_Updated_By() {
        return Last_Updated_By;
    }

    public void setLast_Updated_By(String lastUpdatedBy) {
        this.Last_Updated_By = lastUpdatedBy;
    }
}
