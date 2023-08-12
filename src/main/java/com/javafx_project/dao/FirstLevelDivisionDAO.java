package com.javafx_project.dao;

import com.javafx_project.models.FirstLevelDivision;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionDAO {
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
                     FirstLevelDivision division = new FirstLevelDivision(division_ID, division_Name, country_ID);

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
