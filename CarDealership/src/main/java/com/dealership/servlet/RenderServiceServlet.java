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
import java.sql.SQLException;
import java.sql.Timestamp;

@WebServlet("/RenderServiceServlet")
public class RenderServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");
        String[] taskIds = request.getParameterValues("taskIds");
        String[] partIds = request.getParameterValues("partIds");
        String dropOff = request.getParameter("dropOff");
        String pickUp = request.getParameter("pickUp");

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            conn.setAutoCommit(false);

            // Update drop_off and pick_up in the appointment table
            String updateAppointmentSQL = "UPDATE appointment SET drop_off = ?, pick_up = ? WHERE appointment_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateAppointmentSQL)) {
                updateStmt.setTimestamp(1, Timestamp.valueOf(dropOff));
                updateStmt.setTimestamp(2, Timestamp.valueOf(pickUp));
                updateStmt.setInt(3, Integer.parseInt(appointmentId));
                updateStmt.executeUpdate();
            }

            // Insert tasks into task_appointment table
            String insertTaskSQL = "INSERT INTO task_performed (appointment_id, task_id, labor_cost) VALUES (?, ?, ?)";
            try (PreparedStatement taskStmt = conn.prepareStatement(insertTaskSQL)) {
                for (String taskId : taskIds) {
                    double laborCost = fetchLaborCost(taskId, conn);
                    taskStmt.setInt(1, Integer.parseInt(appointmentId));
                    taskStmt.setInt(2, Integer.parseInt(taskId));
                    taskStmt.setDouble(3, laborCost); // Use setDouble for labor_cost
                    taskStmt.addBatch();
                }
                taskStmt.executeBatch();
            }

            // Insert parts into part_appointment table
            String insertPartSQL = "INSERT INTO part_replaced (appointment_id, part_id) VALUES (?, ?)";
            try (PreparedStatement partStmt = conn.prepareStatement(insertPartSQL)) {
                for (String partId : partIds) {
                    partStmt.setInt(1, Integer.parseInt(appointmentId));
                    partStmt.setInt(2, Integer.parseInt(partId));
                    partStmt.addBatch();
                }
                partStmt.executeBatch();
            }

            conn.commit();
            response.getWriter().println("Service successfully rendered!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error rendering service: " + e.getMessage());
        }
    }

    private double fetchLaborCost(String taskId, Connection conn) throws SQLException {
        String query = "SELECT estd_labor_cost FROM task WHERE task_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(taskId));
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("estd_labor_cost"); // Fetch labor cost as double
                } else {
                    throw new SQLException("Task ID " + taskId + " not found.");
                }
            }
        }
    }
}