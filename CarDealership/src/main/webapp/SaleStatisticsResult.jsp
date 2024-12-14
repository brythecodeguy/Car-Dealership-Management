<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sales Statistics Result</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            text-align: left;
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #0078d7;
            color: white;
        }
    </style>
</head>
<body>
    <header>Sales Statistics Result</header>
    <nav>
        <a href="Index.jsp">Home</a>
        <a href="CarSale.jsp">Car Sale</a>
        <a href="Service.jsp">Service</a>
        <a href="SaleStatistics.jsp">Sales Statistics</a>
    </nav>
    <div class="container">
        <h2>Results</h2>
        <table>
            <thead>
                <tr>
                    <th>Make</th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Cars Sold</th>
                    <th>Profit ($)</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Map<String, Object>> stats = (List<Map<String, Object>>) request.getAttribute("stats");
                    if (stats != null && !stats.isEmpty()) {
                        for (Map<String, Object> record : stats) { 
                %>
                <tr>
                    <td><%= record.get("make") %></td>
                    <td><%= record.get("model") %></td>
                    <td><%= record.get("year") %></td>
                    <td><%= record.get("cars_sold") %></td>
                    <td>$<%= String.format("%.2f", record.get("profit")) %></td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="5">No results found for the given period.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
