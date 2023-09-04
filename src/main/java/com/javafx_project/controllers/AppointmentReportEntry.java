package com.javafx_project.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Map;

// Report class to represent each row in the report
public class AppointmentReportEntry {
    private final SimpleStringProperty month;
    private final SimpleIntegerProperty totalCount;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty typeCount;


    public AppointmentReportEntry(String month, SimpleIntegerProperty totalCount, String type, SimpleIntegerProperty typeCount) {
        this.month = new SimpleStringProperty(month);
        this.totalCount = totalCount;
        this.type = new SimpleStringProperty(type);
        this.typeCount = typeCount;
    }

    public AppointmentReportEntry(String key, Map<String, Integer> value, String key1, Integer value1) {
        this.month = new SimpleStringProperty(key);

        Integer monthValue = value.get(key);
        this.totalCount = (monthValue != null) ? new SimpleIntegerProperty(monthValue) : new SimpleIntegerProperty(0);

        this.type = new SimpleStringProperty(key1);
        this.typeCount = (value1 != null) ? new SimpleIntegerProperty(value1) : new SimpleIntegerProperty(0);
    }

    public AppointmentReportEntry(String key, int totalForMonth, String key1, Integer value) {
        this.month = new SimpleStringProperty(key);
        this.totalCount = new SimpleIntegerProperty(totalForMonth);
        this.type = new SimpleStringProperty(key1);
        this.typeCount = (value != null) ? new SimpleIntegerProperty(value) : new SimpleIntegerProperty(0);
    }


    public String getMonth() {
        return month.get();
    }

    public void setMonth(String month) {
        this.month.set(month);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getTypeCount() {
        return typeCount.get();
    }

    public void setTypeCount(int typeCount) {
        this.typeCount.set(typeCount);
    }

    public int getTotalCount() {
        return totalCount.get();
    }

    public void setTotalCount(int totalCount) {
        this.totalCount.set(totalCount);
    }

    public SimpleIntegerProperty totalCountProperty() {
        return totalCount;
    }
}
