package com.javafx_project.models;

import com.javafx_project.dao.UserDAO;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The type User.
 */
public class User extends UserDAO {
    private int User_ID;
    private String User_Name;

    private String password;

    private String Create_Date;

    private String Created_By;

    private String Last_Update;

    private String Last_Updated_By;

    /**
     * The Update count.
     */
    public IntegerProperty updateCount = new SimpleIntegerProperty();


    /**
     * Instantiates a new User.
     */
    public User() {
        this.updateCount = new SimpleIntegerProperty();
    }

    /**
     * Instantiates a new User.
     *
     * @param userId        the user id
     * @param userName      the user name
     * @param password      the password
     * @param createDate    the create date
     * @param createdBy     the created by
     * @param lasUpdate     the las update
     * @param lastUpdatedBy the last updated by
     */
    public User(int userId, String userName, String password, String createDate, String createdBy, String lasUpdate, String lastUpdatedBy) {
        this.User_ID = userId;
        this.User_Name = userName;
        this.password = password;
        this.Create_Date = createDate;
        this.Created_By = createdBy;
        this.Last_Update = lasUpdate;
        this.Last_Updated_By = lastUpdatedBy;
        this.updateCount = updateCount;
    }

    /**
     * Instantiates a new User.
     *
     * @param user_ID  the user id
     * @param test     the test
     * @param password the password
     */
    public User(int user_ID, String test, String password) {
        super();
    }

    // getters and setters

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUser_ID() {
        return User_ID;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUser_ID(int userId) {
        this.User_ID = userId;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUser_Name() {
        return User_Name;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUser_Name(String userName) {
        this.User_Name = userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets create date.
     *
     * @return the create date
     */
    public String getCreate_Date() {
        return Create_Date;
    }

    /**
     * Sets create date.
     *
     * @param createDate the create date
     */
    public void setCreate_Date(String createDate) {
        this.Create_Date = createDate;
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
     * @param createdBy the created by
     */
    public void setCreated_By(String createdBy) {
        this.Created_By = createdBy;
    }

    /**
     * Gets last update.
     *
     * @return the last update
     */
    public String getLast_Update() {
        return Last_Update;
    }

    /**
     * Sets last update.
     *
     * @param lastUpdate the last update
     */
    public void setLast_Update(String lastUpdate) {
        this.Last_Update = lastUpdate;
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
     * @param lastUpdatedBy the last updated by
     */
    public void setLast_Updated_By(String lastUpdatedBy) {
        this.Last_Updated_By = lastUpdatedBy;
    }

    /**
     * Gets update count.
     *
     * @return the update count
     */
    public int getUpdate_Count() {
        return updateCount.get();
    }

    /**
     * Sets update count.
     *
     * @param updateCount the update count
     */
    public void setUpdate_Count(int updateCount) {
        this.updateCount.set(updateCount);
    }

    /**
     * Update count property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty updateCountProperty() {
        return updateCount;
    }
}
