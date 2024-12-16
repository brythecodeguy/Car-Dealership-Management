package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ScheduleServiceServlet")
public class ScheduleServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String carId = request.getParameter("carId");
        String packageName = request.getParameter("package");
        String startTime = request.getParameter("startTime"); // from JSP
        String endTime = request.getParameter("endTime"); // from JSP
        String appointmentDate = request.getParameter("appointmentDate"); // from JSP

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            conn.setAutoCommit(false);

            // Retrieve the package_id using the package name
            int packageId = -1;
            String selectPackageSQL = "SELECT package_id FROM package WHERE name = ?";
            try (PreparedStatement packageStmt = conn.prepareStatement(selectPackageSQL)) {
                packageStmt.setString(1, packageName);
                try (ResultSet rs = packageStmt.executeQuery()) {
                    if (rs.next()) {
                        packageId = rs.getInt("package_id");
                    } else {
                        throw new SQLException("Invalid package name.");
                    }
                }
            }

            // Retrieve or insert the time slot and get its ID
            int timeSlotId = -1;
            String selectTimeSlotSQL = """
                SELECT time_slot_id FROM time_slot 
                WHERE start_time = ? AND end_time = ? AND date = ?
            """;
            try (PreparedStatement timeSlotStmt = conn.prepareStatement(selectTimeSlotSQL)) {
                timeSlotStmt.setTime(1, java.sql.Time.valueOf(startTime));
                timeSlotStmt.setTime(2, java.sql.Time.valueOf(endTime));
                timeSlotStmt.setDate(3, java.sql.Date.valueOf(appointmentDate));
                try (ResultSet rs = timeSlotStmt.executeQuery()) {
                    if (rs.next()) {
                        timeSlotId = rs.getInt("time_slot_id");
                    } else {
                        // Insert the time slot if it doesn't exist
                        String insertTimeSlotSQL = """
                            INSERT INTO time_slot (start_time, end_time, date)
                            VALUES (?, ?, ?)
                            RETURNING time_slot_id;
                        """;
                        try (PreparedStatement insertTimeSlotStmt = conn.prepareStatement(insertTimeSlotSQL)) {
                            insertTimeSlotStmt.setTime(1, java.sql.Time.valueOf(startTime));
                            insertTimeSlotStmt.setTime(2, java.sql.Time.valueOf(endTime));
                            insertTimeSlotStmt.setDate(3, java.sql.Date.valueOf(appointmentDate));
                            try (ResultSet insertRS = insertTimeSlotStmt.executeQuery()) {
                                if (insertRS.next()) {
                                    timeSlotId = insertRS.getInt("time_slot_id");
                                } else {
                                    throw new SQLException("Failed to insert time slot.");
                                }
                            }
                        }
                    }
                }
            }

            // Insert the appointment into the database
            String insertAppointmentSQL = """
                INSERT INTO appointment (customer_id, car_id, package_id, time_slot_id, appointment_made_date)
                VALUES (?, ?, ?, ?, CURRENT_DATE);
            """;
            try (PreparedStatement appointmentStmt = conn.prepareStatement(insertAppointmentSQL)) {
                appointmentStmt.setInt(1, Integer.parseInt(customerId));
                appointmentStmt.setInt(2, Integer.parseInt(carId));
                appointmentStmt.setInt(3, packageId);
                appointmentStmt.setInt(4, timeSlotId);
                appointmentStmt.executeUpdate();
            }

            conn.commit();
            response.getWriter().println("Service appointment successfully scheduled!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error scheduling service: " + e.getMessage());
        }
    }
}
