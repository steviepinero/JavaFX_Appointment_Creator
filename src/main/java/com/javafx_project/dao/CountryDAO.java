package com.javafx_project.dao;

import com.javafx_project.models.Contact;
import com.javafx_project.models.Country;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The type Country dao.
 */
public class CountryDAO {
    /**
     * Gets all countries.
     *
     * @return the all countries
     */
    public static ObservableList<Country> getAllCountries() {
        String sql = "SELECT * FROM countries ORDER BY Country_ID";
        ObservableList<Country> countryList = javafx.collections.FXCollections.observableArrayList();

        try {
            PreparedStatement loadCountries = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = loadCountries.executeQuery();
            {

                while (rs.next()) {
                    Integer country_ID = rs.getInt("Country_ID");
                    String country_Name = rs.getString("Country_Name");
                    Timestamp createDate = rs.getTimestamp("Create_Date");
                    String createdBy = rs.getString("Created_By");
                    Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                    String lastUpdatedBy = rs.getString("Last_Updated_By");
                    Country country = new Country(country_ID, country_Name, createDate, createdBy, lastUpdate, lastUpdatedBy);

                    countryList.add(country);
                }
            }
            return countryList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    /**
     * Delete country.
     *
     * @param country_ID the country id
     */
    public static void deleteCountry(int country_ID) {
        String sql = "DELETE FROM countries WHERE Country_ID = ?";
        System.out.println(country_ID + " deleted");
        try {
            PreparedStatement deleteCountry = DatabaseConnection.getConnection().prepareStatement(sql);
            deleteCountry.setInt(1, country_ID);
            deleteCountry.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
