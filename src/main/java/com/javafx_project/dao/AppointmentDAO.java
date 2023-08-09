package com.javafx_project.dao;

import com.javafx_project.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

public class AppointmentDAO {
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        // get all values from the appointments table
        String sql = "SELECT * FROM appointments ORDER BY appointment_id";

        try (PreparedStatement loadAppointments = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = loadAppointments.executeQuery()) {

            while (rs.next()) {

                int appointmentId = rs.getInt("appointment_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String type = rs.getString("type");
                LocalDateTime start = rs.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("end").toLocalDateTime();
                Timestamp createDate = rs.getTimestamp("create_date");
                String createdBy = rs.getString("created_by");
                Timestamp lastUpdate = rs.getTimestamp("last_update");
                String lastUpdatedBy = rs.getString("last_updated_by");
                int customerId = rs.getInt("customer_id");
                int userId = rs.getInt("user_id");
                int contactId = rs.getInt("contact_id");

                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                appointments.add(appointment);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    private static void setTable(Appointment appointment, String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println(appointment.getCreate_date() + " is the create date in SetTable");
            System.out.println(appointment.getLast_Update());

            pstmt.setString(1, appointment.getTitle());
            pstmt.setString(2, appointment.getDescription());
            pstmt.setString(3, appointment.getLocation());
            pstmt.setString(4, String.valueOf(appointment.getContact_ID()));
            pstmt.setString(5, appointment.getType());
            pstmt.setDate(6, Date.valueOf(appointment.getStart()));
            pstmt.setDate(7, Date.valueOf(appointment.getEnd()));
            pstmt.setInt(8, appointment.getCustomer_ID());
            pstmt.setInt(9, appointment.getUser_ID());
            pstmt.setInt(10, appointment.getAppointment_ID());

            if (appointment.getCreate_date() != null && !appointment.getCreate_date().equals("Create Date")) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(appointment.getCreate_date());
                Date createDate = Date.valueOf(appointment.getCreate_date());
                pstmt.setDate(11, createDate);
            } else {
                pstmt.setDate(11, Date.valueOf(LocalDate.now()));
            }

            pstmt.setString(12, appointment.getCreated_By());

            if (appointment.getLast_Update() != null && !appointment.getLast_Update().equals("Last Update")) {
                LocalDate lastUpdate = appointment.getLast_Update();
                pstmt.setDate(13, Date.valueOf(lastUpdate));
            } else {
                pstmt.setDate(13, Date.valueOf(LocalDate.now()));
            }

            pstmt.setString(14, appointment.getLast_Updated_By());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    public static void addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Timestamp create_date, String created_by, Timestamp last_update, String last_updated_by, int customerId, int userId, int contactId) {
        //add appointment to database

        try{
            PreparedStatement addAppointments = DatabaseConnection.getConnection().prepareStatement("INSERT INTO appointments (title, description, location, type, start, end, create_date, created_by, last_update, last_updated_by, customer_id, user_id, contact_id ) VALUES (? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?)");
            addAppointments.setString(1, title);
            addAppointments.setString(2, description);
            addAppointments.setString(3, location);
            addAppointments.setString(4, type);
            addAppointments.setTimestamp(5, Timestamp.valueOf(start));
            addAppointments.setTimestamp(6, Timestamp.valueOf(end));
            addAppointments.setTimestamp(7, create_date);
            addAppointments.setString(8, created_by);
            addAppointments.setTimestamp(9, last_update);
            addAppointments.setString(10, last_updated_by);
            addAppointments.setInt(11, customerId);
            addAppointments.setInt(12, userId);
            addAppointments.setInt(13, contactId);
            addAppointments.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
