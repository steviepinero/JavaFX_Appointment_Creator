package com.javafx_project.models;

/**
 * The type Report.
 */
public class Report {
    private String type;
    private String month;
    private int count;

    /**
     * Instantiates a new Report.
     *
     * @param type  the type
     * @param month the month
     * @param count the count
     */
    public Report(String type, String month, int count) {
        this.type = type;
        this.month = month;
        this.count = count;
    }

    /**
     * Instantiates a new Report.
     */
    public Report() {
        // No-argument constructor
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * Sets count.
     *
     * @param count the count
     */
    public void setCount(int count) {
        this.count = count;
    }
}
