package com.dealership.servlet;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        String DB_URI = "jdbc:postgresql://your-db-url:5432/dbname?user=username&password=password";

        try (Connection conn = DriverManager.getConnection(DB_URI)) {
            System.out.println("Connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
