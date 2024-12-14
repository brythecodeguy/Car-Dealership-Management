package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/GetTimeSlotsServlet")
public class GetTimeSlotsServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String date = request.getParameter("date");
        String dbUri = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

        response.setContentType("text/html");
        try (Connection conn = DriverManager.getConnection(dbUri)) {
            // Check if the date is valid
            if (date == null || date.trim().isEmpty()) {
                response.getWriter().write("<option value=''>Invalid Date</option>");
                return;
            }

            // Prepare SQL query to fetch time slots for the selected date
            String query = "SELECT time_slot_id, start_time, end_time FROM time_slot WHERE date = ? ORDER BY start_time";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(date)); // Convert the date string to SQL Date
            ResultSet rs = stmt.executeQuery();

            // Generate valid <option> elements for each time slot
            StringBuilder options = new StringBuilder();
            while (rs.next()) {
                int timeSlotId = rs.getInt("time_slot_id");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("end_time");

                options.append("<option value='").append(timeSlotId).append("'>")
                       .append(startTime).append(" - ").append(endTime).append("</option>");
            }

            // If no time slots are available, show a message
            if (options.length() == 0) {
                options.append("<option value=''>No Time Slots Available</option>");
            }

            response.getWriter().write(options.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<option value=''>Error Loading Time Slots</option>");
        }
    }
}
