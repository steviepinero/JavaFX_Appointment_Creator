package com.javafx_project.models;

public class Report {
    private String type;
    private String month;
    private int count;

    public Report(String type, String month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    public Report() {
        // No-argument constructor
    }

    public String getType() {
        return type;
    }

    public String getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
