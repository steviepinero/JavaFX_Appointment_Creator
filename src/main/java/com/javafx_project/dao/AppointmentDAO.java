package com.javafx_project.dao;

import com.javafx_project.models.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

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
                appointment.setContactId(rs.getInt("contact_id"));
                appointment.setType(rs.getString("type"));
                appointment.setStart(rs.getDate("start").toLocalDate());
                appointment.setEnd(rs.getDate("end").toLocalDate());
                appointment.setCustomerIdInt(rs.getInt("customer_id"));
                appointment.setUserId(rs.getInt("user_id"));
                Date createDate = rs.getDate("create_date");
                System.out.println(createDate);
                    if (createDate != null) {
                        appointment.setCreateDate(rs.getDate("create_date").toLocalDate());
/*
                        appointment.setCreateDate(createDate.toLocalDate());
*/
                        System.out.println(createDate + " is not null"  + appointment.getCreateDate());
                    } else {
                        appointment.setCreateDate(LocalDate.now());
                        System.out.println(createDate + " is null" + appointment.getCreateDate());
                    }
                appointment.setCreatedBy(rs.getString("created_by"));
                    Date lastUpdate = rs.getDate("last_update");
                    if (lastUpdate != null) {
                        appointment.setLastUpdate(lastUpdate.toLocalDate());
                    } else {
                        appointment.setLastUpdate(LocalDate.now());
                    }
                appointment.setLastUpdatedBy(rs.getString("last_updated_by"));
                appointments.add(new Appointment(appointment.getAppointmentId(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(), appointment.getContactId(), appointment.getType(), appointment.getStart(), appointment.getEnd(), appointment.getCustomerIdInt(), appointment.getUserId(), appointment.getCreateDate(), appointment.getCreatedBy(), appointment.getLastUpdate(), appointment.getLastUpdatedBy()));
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

            System.out.println(appointment.getCreateDate() + " is the create date in SetTable");
            System.out.println(appointment.getLastUpdate());

            pstmt.setString(1, appointment.getTitle());
            pstmt.setString(2, appointment.getDescription());
            pstmt.setString(3, appointment.getLocation());
            pstmt.setString(4, String.valueOf(appointment.getContactId()));
            pstmt.setString(5, appointment.getType());
            pstmt.setDate(6, Date.valueOf(appointment.getStart()));
            pstmt.setDate(7, Date.valueOf(appointment.getEnd()));
            pstmt.setInt(8, appointment.getCustomerIdInt());
            pstmt.setInt(9, appointment.getUserId());
            pstmt.setInt(10, appointment.getAppointmentId());

            if (appointment.getCreateDate() != null && !appointment.getCreateDate().equals("Create Date")) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(appointment.getCreateDate());
                //TODO last change was made here 8/1/23
                Date createDate = Date.valueOf(appointment.getCreateDate());
                pstmt.setDate(11, createDate);
            } else {
                pstmt.setDate(11, Date.valueOf(LocalDate.now()));
            }

            pstmt.setString(12, appointment.getCreatedBy());

            if (appointment.getLastUpdate() != null && !appointment.getLastUpdate().equals("Last Update")) {
                LocalDate lastUpdate = appointment.getLastUpdate();
                pstmt.setDate(13, Date.valueOf(lastUpdate));
            } else {
                pstmt.setDate(13, Date.valueOf(LocalDate.now()));
            }

//            pstmt.setString(14, appointment.getLastUpdatedBy());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (title, description, location, contact_id, type, start, end, customer_id, user_id, create_date, created_by, last_update, last_updated_by ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        setTable(appointment, sql);

        // TODO fix error: Data truncation: Incorrect date value: '0' for column 'Create_Date' at row 1
    }
}
