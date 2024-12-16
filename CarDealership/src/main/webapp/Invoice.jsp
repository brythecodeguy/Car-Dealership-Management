<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Invoice</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f9f9f9;
        }
        h1, h2 {
            color: #0078d7;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
            max-width: 600px;
            margin: auto;
        }
        .highlight {
            font-weight: bold;
            color: #005bb5;
        }
        p {
            margin: 10px 0;
        }
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Invoice</h1>
    <% Map<String, Object> invoiceData = (Map<String, Object>) request.getAttribute("invoiceData"); %>

    <h2>Details</h2>
    <p><strong>Appointment ID:</strong> <%= invoiceData.get("appointmentId") %></p>
    <p><strong>Drop Off:</strong> <%= invoiceData.get("dropOff") %></p>
    <p><strong>Pick Up:</strong> <%= invoiceData.get("pickUp") %></p>

    <h2>Tasks Performed</h2>
    <p><%= invoiceData.get("tasksPerformed") %></p>

    <h2>Parts Replaced</h2>
    <p><%= invoiceData.get("partsReplaced") %></p>

    <h2>Cost Breakdown</h2>
    <p><strong>Total Labor Cost:</strong> $<%= invoiceData.get("totalLaborCost") %></p>
    <p><strong>Total Part Cost:</strong> $<%= invoiceData.get("totalPartCost") %></p>
    <p><strong>Total Invoice Cost:</strong> $<%= invoiceData.get("totalInvoiceCost") %></p>
    </div>
</body>
</html>
