<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sales Statistics</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #0078d7;
            color: white;
            padding: 20px 0;
            text-align: center;
            font-size: 28px;
        }
        nav {
            background-color: #005bb5;
            padding: 10px 0;
            text-align: center;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.2);
        }
        nav a {
            color: white;
            text-decoration: none;
            margin: 0 15px;
            font-size: 18px;
            padding: 10px 20px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        nav a:hover {
            background-color: #0078d7;
        }
        .container {
            max-width: 800px;
            margin: 20px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        label {
            font-weight: bold;
            margin-bottom: 5px;
        }
        input, select, button {
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #0078d7;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        button:hover {
            background-color: #005bb5;
        }
        .form-group {
            display: flex;
            flex-direction: column;
        }
        .form-actions {
            text-align: center;
        }
    </style>
</head>
<body>
     <header>Car Dealership Management - Sales Statistics</header>
    <nav>
        <a href="Index.jsp">Home</a>
        <a href="CarSale.jsp">Car Sale</a>
        <a href="Service.jsp">Service</a>
        <a href="SaleStatistics.jsp">Sales Statistics</a>
    </nav>
    <div class="container">
        <h2>Sales Statistics</h2>
        <form action="SaleStatisticsServlet" method="post">
            <label for="year">Filter by Year:</label>
            <input type="number" id="year" name="year" placeholder="Enter Year">
            <button type="submit">Filter</button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>Year of Sale</th>
                    <th>Make</th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Profit ($)</th>
                    <th>Number of Cars Sold</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Map<String, Object>> stats = (List<Map<String, Object>>) request.getAttribute("salesStats");
                    if (stats != null && !stats.isEmpty()) {
                        for (Map<String, Object> stat : stats) {
                %>
                <tr>
                    <td><%= stat.get("year_of_sale") %></td>
                    <td><%= stat.get("make") %></td>
                    <td><%= stat.get("model") %></td>
                    <td><%= stat.get("year") %></td>
                    <td><%= stat.get("profit") %></td>
                    <td><%= stat.get("number_of_cars_sold") %></td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="6">No sales data available</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
