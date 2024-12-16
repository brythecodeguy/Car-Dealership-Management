<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bill</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; }
        .container { max-width: 600px; margin: 20px auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }
        h2 { text-align: center; color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #0078d7; color: white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Bill Details</h2>
        <table>
            <tr><th>Customer Name</th><td>${billData.customerName}</td></tr>
            <tr><th>Phone</th><td>${billData.phone}</td></tr>
            <tr><th>Email</th><td>${billData.email}</td></tr>
            <tr><th>Address</th><td>${billData.address}</td></tr>
            <tr><th>Purchase Date</th><td>${billData.purchaseDate}</td></tr>
            <tr><th>Sale Price</th><td>${billData.salePrice}</td></tr>
            <tr><th>Car Details</th><td>${billData.carDetails}</td></tr>
            <tr><th>Interior</th><td>${billData.interior}</td></tr>
            <tr><th>Odometer</th><td>${billData.odometer}</td></tr>
            <tr><th>Color</th><td>${billData.color}</td></tr>
        </table>
    </div>
</body>
</html>
