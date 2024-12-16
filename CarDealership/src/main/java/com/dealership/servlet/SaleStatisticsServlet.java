package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/SaleStatisticsServlet")
public class SaleStatisticsServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String year = request.getParameter("year");
        List<Map<String, Object>> salesStats = new ArrayList<>();

        String query = """
                SELECT year_of_sale, make, model, year, profit, number_of_cars_sold
                FROM sale_statistics
                """;
        if (year != null && !year.trim().isEmpty()) {
            query += " WHERE year_of_sale = ?";
        }

        try (Connection conn = DriverManager.getConnection(DB_URI);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (year != null && !year.trim().isEmpty()) {
                stmt.setInt(1, Integer.parseInt(year));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> stat = new HashMap<>();
                stat.put("year_of_sale", rs.getInt("year_of_sale"));
                stat.put("make", rs.getString("make"));
                stat.put("model", rs.getString("model"));
                stat.put("year", rs.getInt("year"));
                stat.put("profit", rs.getDouble("profit"));
                stat.put("number_of_cars_sold", rs.getInt("number_of_cars_sold"));
                salesStats.add(stat);
            }

            request.setAttribute("salesStats", salesStats);
            request.getRequestDispatcher("SaleStatistics.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error fetching sales statistics: " + e.getMessage());
        }
    }
}
