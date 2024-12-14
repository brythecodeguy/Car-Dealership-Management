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

@WebServlet("/GetPackagesServlet")
public class GetPackagesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URI);
            String query = "SELECT package_id, name, time_since_purchase FROM package";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> packageList = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> pkg = new HashMap<>();
                pkg.put("packageId", rs.getInt("package_id"));
                pkg.put("name", rs.getString("name"));
                pkg.put("timeSincePurchase", rs.getInt("time_since_purchase"));
                packageList.add(pkg);
            }

            request.setAttribute("packages", packageList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Service.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error fetching service packages: " + e.getMessage());
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

