package com.javafx_project.dao;

import com.javafx_project.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public Customer getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customers";
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setAddress(rs.getString("address"));
                customer.setPostalCode(rs.getString("postal_code"));
                customer.setPhoneNumber(rs.getString("phone"));
                customer.setCreateDate(rs.getDate("create_date").toLocalDate());
                customer.setCreatedBy(rs.getString("created_by"));
                customer.setLastUpdate(rs.getDate("last_update").toLocalDate());
                customer.setLastUpdatedBy(rs.getString("last_updated_by"));
                customer.setDivision(String.valueOf(rs.getInt("division_id")));
                customers.add(new Customer(customer.getCustomerId(), customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getCreateDate(), customer.getCreatedBy(), customer.getLastUpdate(), customer.getLastUpdatedBy(), customer.getDivision()));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
