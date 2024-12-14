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

@WebServlet("/ScheduleServiceServlet")
public class ScheduleServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //form inputs
        String customerId = request.getParameter("customerId");
        String carId = request.getParameter("carId");
        String appointmentDate = request.getParameter("appointmentDate");
        String timeSlot = request.getParameter("timeSlot");
        String packageId = request.getParameter("packageId");

        // Connect to the database and insert appointment
        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            String insertAppointment = "INSERT INTO Appointment (Customer_ID, Car_ID, Appointment_Made_Date, Time_Slot_ID, Package_ID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertAppointment)) {
                pstmt.setString(1, customerId);
                pstmt.setString(2, carId);
                pstmt.setString(3, appointmentDate);
                pstmt.setString(4, timeSlot);
                pstmt.setString(5, packageId);
                pstmt.executeUpdate();
            }

            // success message and catch exception
            response.getWriter().println("Your service appointment has been scheduled successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error scheduling service: " + e.getMessage());
        }
    }
}

