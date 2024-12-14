package com.dealership.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/CarSaleServlet")
public class CarSaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String carId = request.getParameter("carId");
        String carModel = request.getParameter("carModel");
        String salePrice = request.getParameter("salePrice");
        String purchaseDate = request.getParameter("purchaseDate");

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            conn.setAutoCommit(false); // Enable transaction

            // insert customer
            String insertCustomer = "INSERT INTO customer (customer_id, f_name, l_name) VALUES (?, ?, ?) ON CONFLICT (customer_id) DO NOTHING";
            try (PreparedStatement ps = conn.prepareStatement(insertCustomer)) {
                ps.setInt(1, Integer.parseInt(customerId));
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.executeUpdate();
            }

            // insert purchase details
            String insertPurchase = "INSERT INTO purchase (purchase_id, car_id, date_of_purchase, sale_price) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertPurchase)) {
                ps.setInt(1, generatePurchaseId(conn));
                ps.setInt(2, Integer.parseInt(carId));
                ps.setDate(3, java.sql.Date.valueOf(purchaseDate));
                ps.setBigDecimal(4, new BigDecimal(salePrice));
                ps.executeUpdate();
            }

            // update car availability
            String updateCar = "UPDATE car SET car_availability = 'sold' WHERE car_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateCar)) {
                ps.setInt(1, Integer.parseInt(carId));
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Car ID not found in database.");
                }
            }

            // validate model
            String validateModelQuery = "SELECT model FROM vehicle_type vt JOIN car c ON vt.vehicle_id = c.vehicle_id WHERE c.car_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(validateModelQuery)) {
                ps.setInt(1, Integer.parseInt(carId));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String dbModel = rs.getString("model");
                    if (!dbModel.equalsIgnoreCase(carModel)) {
                        throw new SQLException("Model mismatch: Expected " + dbModel + ", but got " + carModel);
                    }
                } else {
                    throw new SQLException("Car ID does not exist in database.");
                }
            }

            conn.commit(); // commit sale transaction
            response.getWriter().println("Car sale registered successfully!");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while registering the sale: " + e.getMessage());
        }
    }

    private int generatePurchaseId(Connection conn) throws SQLException {
        String query = "SELECT COALESCE(MAX(purchase_id), 0) + 1 AS next_id FROM purchase";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        }
        throw new SQLException("Unable to generate purchase ID");
    }
}