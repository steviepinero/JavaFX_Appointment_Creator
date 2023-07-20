package com.javafx_project.dao;

import com.javafx_project.models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments = new ArrayList<>();
        // get all values from the appointments table
        String sql = "SELECT * FROM appointments";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setTitle(rs.getString("title"));
                appointment.setDescription(rs.getString("description"));
                appointment.setLocation(rs.getString("location"));
                appointment.setContactId(rs.getInt("contact_id"));
                appointment.setType(rs.getString("type"));
                appointment.setStart(rs.getDate("start").toLocalDate());
                appointment.setEnd(rs.getDate("end").toLocalDate());
                appointment.setCustomerId(rs.getInt("customer_id"));
                appointment.setUserId(rs.getInt("user_id"));
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return appointments;
    }

    public List<String> getAllTypes() {
        // get all values from the type column in the appointments table
        String sql = "SELECT DISTINCT type FROM appointments";
        List<String> types = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                types.add(rs.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return types;
    }


    public void deleteAppointment(int appointmentId) {
        //delete appointment from database
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointmentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAppointment(Appointment appointment) {
        //update appointment table in database
        String sql = "UPDATE appointments SET title = ?, description = ?, location = ?, contact_id = ?, type = ?, start = ?, end = ?, customer_id = ?, user_id = ? WHERE appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, appointment.getTitle());
            pstmt.setString(2, appointment.getDescription());
            pstmt.setString(3, appointment.getLocation());
            pstmt.setString(4, String.valueOf(appointment.getContactId()));
            pstmt.setString(5, appointment.getType());
            pstmt.setDate(6, Date.valueOf(appointment.getStart()));
            pstmt.setDate(7, Date.valueOf(appointment.getEnd()));
            pstmt.setInt(8, appointment.getCustomerId());
            pstmt.setInt(9, appointment.getUserId());
            pstmt.setInt(10, appointment.getAppointmentId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }



    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (title, description, location, contact_id, type, start, end, customer_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, appointment.getTitle());
            pstmt.setString(2, appointment.getDescription());
            pstmt.setString(3, appointment.getLocation());
            pstmt.setString(4, String.valueOf(appointment.getContactId()));
            pstmt.setString(5, appointment.getType());
            pstmt.setDate(6, Date.valueOf(appointment.getStart()));
            pstmt.setDate(7, Date.valueOf(appointment.getEnd()));
            pstmt.setInt(8, appointment.getCustomerId());
            pstmt.setInt(9, appointment.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
