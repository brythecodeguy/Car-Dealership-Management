<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Invoice</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Service Invoice</h1>

    <h2>Tasks Performed</h2>
    <%
        List<Map<String, Object>> taskList = (List<Map<String, Object>>) request.getAttribute("taskList");
        if (taskList != null && !taskList.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Task Name</th>
            <th>Labor Cost</th>
            <th>Time Taken</th>
        </tr>
        <% for (Map<String, Object> task : taskList) { %>
        <tr>
            <td><%= task.get("name") %></td>
            <td>$<%= task.get("labor_cost") %></td>
            <td><%= task.get("time") %></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
        <p>No tasks performed for this appointment.</p>
    <% } %>

    <h2>Parts Replaced</h2>
    <%
        List<Map<String, Object>> partList = (List<Map<String, Object>>) request.getAttribute("partList");
        if (partList != null && !partList.isEmpty()) {
    %>
    <table>
        <tr>
            <th>Part Name</th>
            <th>Cost</th>
        </tr>
        <% for (Map<String, Object> part : partList) { %>
        <tr>
            <td><%= part.get("name") %></td>
            <td>$<%= part.get("cost") %></td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
        <p>No parts replaced for this appointment.</p>
    <% } %>

    <h2>Total Costs</h2>
    <table>
        <tr>
            <th>Labor Cost</th>
            <td>$<%= request.getAttribute("totalLaborCost") %></td>
        </tr>
        <tr>
            <th>Parts Cost</th>
            <td>$<%= request.getAttribute("totalPartsCost") %></td>
        </tr>
        <tr>
            <th>Grand Total</th>
            <td><strong>$<%= request.getAttribute("grandTotal") %></strong></td>
        </tr>
    </table>
</body>
</html>