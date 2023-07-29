package com.javafx_project.dao;

import com.javafx_project.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;

public class AppointmentDAO {
    public ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
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
                appointment.setContactId(String.valueOf(rs.getInt("contact_id")));
                appointment.setType(rs.getString("type"));
                appointment.setStart(rs.getDate("start").toLocalDate());
                appointment.setEnd(rs.getDate("end").toLocalDate());
                appointment.setCustomerId(rs.getInt("customer_id"));
                appointment.setUserId(rs.getInt("user_id"));
                appointment.setCreateDate(rs.getDate("create_date").toLocalDate());
                appointment.setCreatedBy(rs.getString("created_by"));
                appointment.setLastUpdate(rs.getDate("last_update").toLocalDate());
                appointment.setLastUpdatedBy(rs.getString("last_updated_by"));
                appointments.add(new Appointment(appointment.getAppointmentId(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(), appointment.getContactId(), appointment.getType(), appointment.getStart(), appointment.getEnd(), appointment.getCustomerId(), appointment.getUserId(), appointment.getCreateDate(), appointment.getCreatedBy(), appointment.getLastUpdate(), appointment.getLastUpdatedBy()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return appointments;
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
        String sql = "UPDATE appointments SET title = ?, description = ?, location = ?, contact_id = ?, type = ?, start = ?, end = ?, customer_id = ?, user_id = ?, create_date = ?, created_by = ?, last_update = ?, last_updated_by = ? WHERE appointment_id = ?";

        setTable(appointment, sql);

    }
    private void setTable(Appointment appointment, String sql) {
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

            if (appointment.getCreateDate() != null && !appointment.getCreateDate().equals("Create Date")) {
                LocalDate createDate = appointment.getCreateDate();
                pstmt.setDate(11, Date.valueOf(createDate));
            } else {
                pstmt.setNull(11, java.sql.Types.DATE);
            }

            pstmt.setString(12, appointment.getCreatedBy());

            if (appointment.getLastUpdate() != null && !appointment.getLastUpdate().equals("Last Update")) {
                LocalDate lastUpdate = appointment.getLastUpdate();
                pstmt.setDate(13, Date.valueOf(lastUpdate));
            } else {
                pstmt.setNull(13, java.sql.Types.DATE);
            }

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (title, description, location, contact_id, type, start, end, customer_id, user_id, create_date, created_by, last_update, last_updated_by ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        setTable(appointment, sql);
    }
}
