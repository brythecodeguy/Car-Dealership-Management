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
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/CarSaleServlet")
public class CarSaleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URI = "jdbc:postgresql://db.trakaslnzfwzndivozdm.supabase.co:5432/postgres?user=postgres&password=E0q6p0Eal2b8xLLq";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve customer information from the form
        String firstName = request.getParameter("firstName");
        String middleInitial = request.getParameter("middleInitial");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String aptNumber = request.getParameter("aptNumber");
        String streetNumber = request.getParameter("streetNumber");
        String city = request.getParameter("city");

        // Retrieve vehicle and sale information from the form
        String carId = request.getParameter("carId");
        String salePrice = request.getParameter("salePrice");
        String purchaseDate = request.getParameter("purchaseDate");

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            conn.setAutoCommit(false); // Enable transaction management

            // Insert customer information into the database
            String insertCustomerSQL = """
            	    INSERT INTO customer (f_name, m_init, l_name, phone_number, email, apt_number, street_number, city)
            	    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            	    RETURNING customer_id;
            	""";

            	try (PreparedStatement customerStmt = conn.prepareStatement(insertCustomerSQL)) {
            	    customerStmt.setString(1, firstName);
            	    customerStmt.setString(2, middleInitial.isEmpty() ? null : middleInitial);
            	    customerStmt.setString(3, lastName);
            	    customerStmt.setString(4, phoneNumber);
            	    customerStmt.setString(5, email);
            	    customerStmt.setString(6, aptNumber.isEmpty() ? null : aptNumber);
            	    customerStmt.setString(7, streetNumber);
            	    customerStmt.setString(8, city);

            	    // Execute the query and get the generated customer_id
            	    ResultSet rs = customerStmt.executeQuery();
            	    int customerId = -1; // Initialize customerId to catch errors
            	    if (rs.next()) {
            	        customerId = rs.getInt("customer_id");
            	    }

            	    if (customerId == -1) {
            	        throw new SQLException("Customer ID not generated.");
            	    }

            	    System.out.println("Generated Customer ID: " + customerId);

            // Insert purchase information into the database
            String insertPurchaseSQL = """
            	    INSERT INTO purchase (customer_id, car_id, date_of_purchase, sale_price)
            	    VALUES (?, ?, ?, ?);
            	""";
            	try (PreparedStatement purchaseStmt = conn.prepareStatement(insertPurchaseSQL)) {
            	    purchaseStmt.setInt(1, customerId); // Use the generated customerId
            	    purchaseStmt.setInt(2, Integer.parseInt(carId));
            	    purchaseStmt.setDate(3, java.sql.Date.valueOf(purchaseDate));
            	    purchaseStmt.setBigDecimal(4, new java.math.BigDecimal(salePrice));
            	    purchaseStmt.executeUpdate();
            	}
            

            conn.commit(); // Commit transaction
            response.getWriter().println("Sale successfully registered!");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while registering the sale: " + e.getMessage());
        }
    } catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
}
