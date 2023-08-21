package com.javafx_project.dao;

import com.javafx_project.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class CustomerDAO {
    public static ObservableList<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customers ORDER BY customer_id";
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        try {Connection conn = DatabaseConnection.getConnection();
            PreparedStatement getCustomers = conn.prepareStatement(sql);
            ResultSet rs = getCustomers.executeQuery();
            {

                while (rs.next()) {

                    int customerId = rs.getInt("customer_id");
                    String customerName = rs.getString("customer_name");
                    String address = rs.getString("address");
                    String postalCode = rs.getString("postal_code");
                    String phoneNumber = rs.getString("phone");
                    String createDate = rs.getString("create_date");
                    String createdBy = rs.getString("created_by");
                    String lastUpdate = rs.getString("last_update");
                    String lastUpdatedBy = rs.getString("last_updated_by");
                    int countryId = rs.getInt("country_id");
                    int divisionId = rs.getInt("division_id");


                    Customer customer = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId, divisionId);
                    customerList.add(customer);
                }

            }
            return customerList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }



        public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, country_id, division_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set strings, dates and integers to the prepared statement
            statementPreparation(customer, pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void statementPreparation(Customer customer, PreparedStatement pstmt) throws SQLException {
        // Set strings, dates and integers to the prepared statement
        pstmt.setString(1, customer.getCustomer_Name());
        pstmt.setString(2, customer.getAddress());
        pstmt.setString(3, customer.getPostal_Code());
        pstmt.setString(4, customer.getPhone());
        pstmt.setDate(5, Date.valueOf(String.valueOf(customer.getCreate_Date())));
        pstmt.setString(6, customer.getCreated_By());
        pstmt.setDate(7, Date.valueOf(String.valueOf(customer.getLast_Update())));
        pstmt.setString(8, customer.getLast_Updated_By());
        pstmt.setInt(9, customer.getCountry_ID());
        pstmt.setInt(10, customer.getDivision_ID());
    }

    public void updateCustomer(Customer customer) {

        String sql = "UPDATE customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, create_date = ?, created_by = ?, last_update = ?, last_updated_by = ?, division_id = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            statementPreparation(customer, pstmt);
            pstmt.setInt(11, customer.getCustomer_ID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customerId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
