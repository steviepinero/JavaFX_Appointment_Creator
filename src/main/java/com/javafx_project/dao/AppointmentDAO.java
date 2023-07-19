package com.javafx_project.dao;

import com.javafx_project.models.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {
    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments = new ArrayList<>();
        //  TODO fetch data from the database and add to the list ...
        return appointments;
    }

    public Appointment getAllTypes() {
        return null;
    }

    public void deleteAppointment(int appointmentId) {
    }

    public void updateAppointment(Appointment appointment) {
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
            pstmt.setTimestamp(6, Timestamp.valueOf(appointment.getStart()));
            pstmt.setTimestamp(7, Timestamp.valueOf(appointment.getEnd()));
            pstmt.setInt(8, appointment.getCustomerId());
            pstmt.setInt(9, appointment.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
