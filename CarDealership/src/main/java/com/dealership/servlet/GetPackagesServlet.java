package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/PackageServlet")
public class GetPackagesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> packages = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            String query = "SELECT package_id, name FROM package";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, String> packageData = new HashMap<>();
                    packageData.put("id", String.valueOf(rs.getInt("package_id")));
                    packageData.put("name", rs.getString("name"));
                    packages.add(packageData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Send the package list to the JSP
        request.setAttribute("packages", packages);
        request.getRequestDispatcher("Service.jsp").forward(request, response);
    }
}


