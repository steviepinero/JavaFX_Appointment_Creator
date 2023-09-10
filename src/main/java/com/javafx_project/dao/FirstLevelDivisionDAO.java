package com.javafx_project.dao;

import com.javafx_project.models.FirstLevelDivision;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type First level division dao.
 */
public class FirstLevelDivisionDAO {
    /**
     * Gets all divisions.
     *
     * @return the all divisions
     */
    public static ObservableList<FirstLevelDivision> getAllDivisions() {
         String sql = "SELECT * FROM divisions ORDER BY Division_ID";
         ObservableList<FirstLevelDivision> divisionList = javafx.collections.FXCollections.observableArrayList();

         try {
             PreparedStatement loadDivisions = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = loadDivisions.executeQuery();
             {

                 while (rs.next()) {
                     Integer division_ID = rs.getInt("Division_ID");
                     String division_Name = rs.getString("Division_Name");
                     Integer country_ID = rs.getInt("Country_ID");
                     Date create_Date = rs.getDate("Create_Date");
                     String created_By = rs.getString("Created_By");
                     Date last_Update = rs.getDate("Last_Update");
                     String last_Updated_By = rs.getString("Last_Updated_By");
                     FirstLevelDivision division = new FirstLevelDivision(division_ID, division_Name, country_ID, create_Date, created_By, last_Update, last_Updated_By);

                     divisionList.add(division);
                 }
             }
             return divisionList;
         } catch (SQLException e) {
             System.out.println(e.getMessage());
         }
         return null;
    }

}
