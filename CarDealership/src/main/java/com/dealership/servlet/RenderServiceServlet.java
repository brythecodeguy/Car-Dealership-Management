package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/RenderServiceServlet")
public class RenderServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // URI connection
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");
        String taskId = request.getParameter("taskId");
        String laborCost = request.getParameter("laborCost");
        String time = request.getParameter("time");
        String partId = request.getParameter("partId");

        if (appointmentId == null || appointmentId.isEmpty()) {
            response.getWriter().println("Error: Appointment ID is required.");
            return;
        }

        Connection conn =null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URI);

            // insert task performed
            String insertTask = "INSERT INTO task_performed (appointment_id, task_id, labor_cost, time) VALUES (?, ?, ?, ?::INTERVAL)";
            stmt = conn.prepareStatement(insertTask);
            stmt.setInt(1, Integer.parseInt(appointmentId));
            stmt.setInt(2, Integer.parseInt(taskId));
            stmt.setDouble(3, Double.parseDouble(laborCost));
            stmt.setString(4, time); // Pass time as a String like "1 hour" or "30 minutes"
            stmt.executeUpdate();

            // insert part replaced
            String insertPart = "INSERT INTO part_replaced (appointment_id, part_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(insertPart);
            stmt.setInt(1, Integer.parseInt(appointmentId));
            stmt.setInt(2, Integer.parseInt(partId));
            stmt.executeUpdate();

            // fetch task performed from database for invoice
            String taskQuery = "SELECT t.name, tp.labor_cost, tp.time FROM task_performed tp JOIN task t ON tp.task_id = t.task_id WHERE tp.appointment_id = ?";
            stmt = conn.prepareStatement(taskQuery);
            stmt.setInt(1, Integer.parseInt(appointmentId));
            ResultSet taskResults = stmt.executeQuery();

            List<Map<String, Object>> taskList = new ArrayList<>();
            double totalLaborCost = 0;
            while (taskResults.next()) {
                Map<String, Object> task = new HashMap<>();
                task.put("name", taskResults.getString("name"));
                task.put("labor_cost", taskResults.getDouble("labor_cost"));
                task.put("time", taskResults.getString("time"));
                taskList.add(task);
                totalLaborCost += taskResults.getDouble("labor_cost");
            }

            // fetch part replaced from database for invoice
            String partQuery = "SELECT p.name, p.cost_of_part FROM part p JOIN part_replaced pr ON p.part_id = pr.part_id WHERE pr.appointment_id = ?";
            stmt = conn.prepareStatement(partQuery);
            stmt.setInt(1, Integer.parseInt(appointmentId));
            ResultSet partResults = stmt.executeQuery();

            List<Map<String, Object>> partList = new ArrayList<>();
            double totalPartsCost = 0;
            while (partResults.next()) {
                Map<String, Object> part = new HashMap<>();
                part.put("name", partResults.getString("name"));
                part.put("cost", partResults.getDouble("cost_of_part"));
                partList.add(part);
                totalPartsCost += partResults.getDouble("cost_of_part");
            }

            // grandtotal calculation
            double grandTotal = totalLaborCost + totalPartsCost;

            // attributes for JSP
            request.setAttribute("taskList", taskList);
            request.setAttribute("partList", partList);
            request.setAttribute("totalLaborCost", totalLaborCost);
            request.setAttribute("totalPartsCost", totalPartsCost);
            request.setAttribute("grandTotal", grandTotal);

            // to invoice.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("Invoice.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error rendering service or generating invoice: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

