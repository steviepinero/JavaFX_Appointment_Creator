package com.javafx_project.dao;

import com.javafx_project.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customers";

        try (Connection conn = DatabaseConnection.establishConnection();
             Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(Integer.parseInt(rs.getString("customer_id")));
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setAddress(rs.getString("address"));
                customer.setPostalCode(rs.getString("postal_code"));
                customer.setPhoneNumber(rs.getString("phone"));
                customer.setCreateDate(rs.getDate("create_date").toLocalDate());
                customer.setCreatedBy(rs.getString("created_by"));
                customer.setLastUpdate(rs.getDate("last_update").toLocalDate());
                customer.setLastUpdatedBy(rs.getString("last_updated_by"));
                customer.setDivision(String.valueOf(rs.getString("division_id")));
                customers.add(new Customer(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getCreateDate(), customer.getCreatedBy(), customer.getLastUpdate(), customer.getLastUpdatedBy(), customer.getDivision()));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, division_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPostalCode());
            pstmt.setString(4, String.valueOf(customer.getCreateDate()));
            pstmt.setString(5, customer.getCreatedBy());
            pstmt.setString(6, String.valueOf(customer.getLastUpdate()));
            pstmt.setString(7, customer.getLastUpdatedBy());
            pstmt.setString(8, customer.getDivision());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateCustomer(Customer customer) {

        String sql = "UPDATE customers SET customer_name = ?, address = ?, postal_code = ?, phone = ?, create_date = ?, created_by = ?, last_update = ?, last_updated_by = ?, division_id = ? WHERE customer_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPostalCode());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setDate(5, Date.valueOf(String.valueOf(customer.getCreateDate())));
            pstmt.setString(6, customer.getCreatedBy());
            pstmt.setDate(7, Date.valueOf(customer.getLastUpdate()));
            pstmt.setString(8, customer.getLastUpdatedBy());
            pstmt.setString(9, customer.getDivision());
            pstmt.setInt(10, customer.getCustomerId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void deleteCustomer(int customerId) {
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
