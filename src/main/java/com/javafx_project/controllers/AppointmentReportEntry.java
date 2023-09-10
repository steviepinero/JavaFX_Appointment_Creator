package com.javafx_project.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

/**
 * The type Appointment report entry.
 */
// Report class to represent each row in the report
public class AppointmentReportEntry {
    private final SimpleStringProperty month;
    private final SimpleIntegerProperty totalCount;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty typeCount;


    /**
     * Instantiates a new Appointment report entry.
     *
     * @param key   the key
     * @param key1  the key 1
     * @param value the value
     */
    public AppointmentReportEntry(String key, String key1, Integer value) {
        this.month = new SimpleStringProperty(key);
        this.totalCount = new SimpleIntegerProperty(0);
        this.type = new SimpleStringProperty(key1);
        this.typeCount = (value != null) ? new SimpleIntegerProperty(value) : new SimpleIntegerProperty(0);
    }

    /**
     * Instantiates a new Appointment report entry.
     *
     * @param key   the key
     * @param value the value
     */
    public AppointmentReportEntry(String key, Integer value) {
        this.month = new SimpleStringProperty(key);
        this.totalCount = (value != null) ? new SimpleIntegerProperty(value) : new SimpleIntegerProperty(0);
        this.type = new SimpleStringProperty(key);
        this.typeCount = (value != null) ? new SimpleIntegerProperty(value) : new SimpleIntegerProperty(0);
    }


    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return month.get();
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(String month) {
        this.month.set(month);
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type.get();
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type.set(type);
    }

    /**
     * Gets type count.
     *
     * @return the type count
     */
    public int getTypeCount() {
        return typeCount.get();
    }

    /**
     * Sets type count.
     *
     * @param typeCount the type count
     */
    public void setTypeCount(int typeCount) {
        this.typeCount.set(typeCount);
    }

    /**
     * Gets total count.
     *
     * @return the total count
     */
    public int getTotalCount() {
        return totalCount.get();
    }

    /**
     * Sets total count.
     *
     * @param totalCount the total count
     */
    public void setTotalCount(int totalCount) {
        this.totalCount.set(totalCount);
    }

    /**
     * Total count property simple integer property.
     *
     * @return the simple integer property
     */
    public SimpleIntegerProperty totalCountProperty() {
        return totalCount;
    }
}
