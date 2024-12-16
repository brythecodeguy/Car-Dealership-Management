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

@WebServlet("/GenerateBillServlet")
public class GenerateBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carId = request.getParameter("carId");

        if (carId == null || carId.isEmpty()) {
            response.getWriter().println("Car ID is required to generate the bill.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            // Query customer and purchase details
            String query = """
                SELECT c.f_name, c.m_init, c.l_name, c.phone_number, c.email, c.apt_number, c.street_number, c.city,
                       p.date_of_purchase, p.sale_price,
                       v.make, v.model, v.year,
                       car.interior, car.odometer, car.color
                FROM customer c
                JOIN purchase p ON c.customer_id = p.purchase_id
                JOIN car ON p.car_id = car.car_id
                JOIN vehicle_type v ON car.vehicle_id = v.vehicle_id
                WHERE car.car_id = ?
            """;

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, Integer.parseInt(carId));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Map<String, Object> billData = new HashMap<>();
                    billData.put("customerName", rs.getString("f_name") + " " + rs.getString("m_init") + " " + rs.getString("l_name"));
                    billData.put("phone", rs.getString("phone_number"));
                    billData.put("email", rs.getString("email"));
                    billData.put("address", rs.getString("apt_number") + " " + rs.getString("street_number") + ", " + rs.getString("city"));
                    billData.put("purchaseDate", rs.getDate("date_of_purchase"));
                    billData.put("salePrice", rs.getDouble("sale_price"));
                    billData.put("carDetails", rs.getString("make") + " " + rs.getString("model") + " (" + rs.getInt("year") + ")");
                    billData.put("interior", rs.getString("interior"));
                    billData.put("odometer", rs.getString("odometer"));
                    billData.put("color", rs.getString("color"));

                    request.setAttribute("billData", billData);
                    request.getRequestDispatcher("Bill.jsp").forward(request, response);
                } else {
                    response.getWriter().println("No details found for Car ID: " + carId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error generating bill: " + e.getMessage());
        }
    }
}
