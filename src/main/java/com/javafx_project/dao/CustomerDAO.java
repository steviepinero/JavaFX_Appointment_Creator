package com.javafx_project.dao;

import com.javafx_project.models.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    public Customer getAllCustomers() {
        return null;
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_name, address, postal_code, phone, create_date, created_by, last_update, last_updated_by, division_id)";

        try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getPostalCode());
            pstmt.setString(4, String.valueOf(customer.getCreateDate()));
            pstmt.setString(5, customer.getCreatedBy());
            pstmt.setString(6, String.valueOf(customer.getLastUpdate()));
            pstmt.setString(7, customer.getLastUpdatedBy());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void updateCustomer(Customer customer) {

    }

    public void deleteCustomer(int customerId) {

    }
}
