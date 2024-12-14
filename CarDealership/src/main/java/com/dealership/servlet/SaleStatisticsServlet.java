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

@WebServlet("/SaleStatisticsServlet")
public class SaleStatisticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String beginDate = request.getParameter("begin_date");
        String endDate = request.getParameter("end_date");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Establish database connection
            conn = DriverManager.getConnection(DB_URI);

            // Query for total cars sold and profit per car type
            String query = """
                SELECT 
                    v.make, v.model, v.year, COUNT(p.car_id) AS cars_sold, 
                    SUM(p.sale_price - c.cost) AS profit 
                FROM purchase p 
                JOIN car c ON p.car_id = c.car_id 
                JOIN vehicle_type v ON c.vehicle_id = v.vehicle_id 
                WHERE p.date_of_purchase BETWEEN ? AND ? 
                GROUP BY v.make, v.model, v.year
            """;
            stmt = conn.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(beginDate));
            stmt.setDate(2, Date.valueOf(endDate));

            ResultSet rs = stmt.executeQuery();

            // list maps for storing results
            List<Map<String, Object>> stats = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> record = new HashMap<>();
                record.put("make", rs.getString("make"));
                record.put("model", rs.getString("model"));
                record.put("year", rs.getInt("year"));
                record.put("cars_sold", rs.getInt("cars_sold"));
                record.put("profit", rs.getDouble("profit"));
                stats.add(record);
            }

            //data to JSP
            request.setAttribute("stats", stats);
            RequestDispatcher dispatcher = request.getRequestDispatcher("saleStatisticsResult.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error generating statistics: " + e.getMessage());
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
