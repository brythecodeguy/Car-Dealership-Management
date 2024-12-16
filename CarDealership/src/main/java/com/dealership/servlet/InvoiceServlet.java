package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/InvoiceServlet")
public class InvoiceServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String appointmentId = request.getParameter("appointmentId");

        if (appointmentId == null || appointmentId.isEmpty()) {
            request.setAttribute("error", "Invalid Appointment ID.");
            request.getRequestDispatcher("Invoice.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
        	String query = "WITH labor_cost AS ("
        	        + "    SELECT tp.appointment_id, "
        	        + "           STRING_AGG(t.name || ' ($' || tp.labor_cost || ')', ', ') AS tasks_performed, "
        	        + "           SUM(tp.labor_cost) AS total_labor_cost "
        	        + "    FROM task_performed tp "
        	        + "    JOIN task t ON tp.task_id = t.task_id "
        	        + "    GROUP BY tp.appointment_id"
        	        + "), part_cost AS ("
        	        + "    SELECT pr.appointment_id, "
        	        + "           STRING_AGG(pa.name, ', ') AS parts_replaced, "
        	        + "           SUM(pa.cost_of_part) AS total_part_cost "
        	        + "    FROM part_replaced pr "
        	        + "    JOIN part pa ON pr.part_id = pa.part_id "
        	        + "    GROUP BY pr.appointment_id"
        	        + ") "
        	        + "SELECT a.appointment_id, a.drop_off, a.pick_up, a.appointment_made_date, "
        	        + "a.customer_id, a.car_id, "
        	        + "COALESCE(lc.tasks_performed, 'No tasks performed') AS tasks_performed, "
        	        + "COALESCE(lc.total_labor_cost, 0) AS total_labor_cost, "
        	        + "COALESCE(pc.parts_replaced, 'No parts replaced') AS parts_replaced, "
        	        + "COALESCE(pc.total_part_cost, 0) AS total_part_cost, "
        	        + "(COALESCE(lc.total_labor_cost, 0) + COALESCE(pc.total_part_cost, 0)) AS total_invoice_cost "
        	        + "FROM appointment a "
        	        + "LEFT JOIN labor_cost lc ON a.appointment_id = lc.appointment_id "
        	        + "LEFT JOIN part_cost pc ON a.appointment_id = pc.appointment_id "
        	        + "WHERE a.appointment_id = ?";

        	try (PreparedStatement stmt = conn.prepareStatement(query)) {
        	    stmt.setInt(1, Integer.parseInt(appointmentId));
        	    try (ResultSet rs = stmt.executeQuery()) {
        	        if (rs.next()) {
        	            Map<String, Object> invoiceData = new HashMap<>();
        	            invoiceData.put("appointmentId", rs.getInt("appointment_id"));
        	            invoiceData.put("dropOff", rs.getTimestamp("drop_off"));
        	            invoiceData.put("pickUp", rs.getTimestamp("pick_up"));
        	            invoiceData.put("tasksPerformed", rs.getString("tasks_performed"));
        	            invoiceData.put("totalLaborCost", rs.getDouble("total_labor_cost"));
        	            invoiceData.put("partsReplaced", rs.getString("parts_replaced"));
        	            invoiceData.put("totalPartCost", rs.getDouble("total_part_cost"));
        	            invoiceData.put("totalInvoiceCost", rs.getDouble("total_invoice_cost"));

        	            request.setAttribute("invoiceData", invoiceData);
        	            request.getRequestDispatcher("Invoice.jsp").forward(request, response);
        	        }
        	    }
        	}

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("Invoice.jsp").forward(request, response);
        }
    }
}
