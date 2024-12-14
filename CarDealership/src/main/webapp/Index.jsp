<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Dealership Management</title>
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
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }
        .welcome {
            text-align: center;
            margin-bottom: 30px;
        }
        .welcome h1 {
            color: #333;
            font-size: 32px;
            margin-bottom: 10px;
        }
        .welcome p {
            color: #555;
            font-size: 18px;
        }
        .dashboard {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }
        .card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            text-align: center;
            flex: 1 1 calc(30% - 20px);
            min-width: 250px;
        }
        .card h3 {
            color: #0078d7;
            margin-bottom: 10px;
        }
        .card p {
            color: #555;
            font-size: 16px;
        }
        .card a {
            display: inline-block;
            margin-top: 10px;
            padding: 8px 15px;
            background-color: #0078d7;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .card a:hover {
            background-color: #005bb5;
        }
        footer {
            text-align: center;
            padding: 20px;
            background-color: #0078d7;
            color: white;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <header>Car Dealership Management System</header>
    <nav>
        <a href="Index.jsp">Home</a>
        <a href="CarSale.jsp">Car Sale</a>
        <a href="Service.jsp">Service</a>
        <a href="SaleStatistics.jsp">Sales Statistics</a>
    </nav>
    <div class="container">
        <div class="welcome">
            <h1>Welcome to Car Dealership Management</h1>
            <p>Manage car sales, services, and statistics in one place.</p>
        </div>
        <div class="dashboard">
            <div class="card">
                <h3>Upcoming Appointments</h3>
                <p>Track and manage service appointments.</p>
                <a href="Service.jsp">View Appointments</a>
            </div>
            <div class="card">
                <h3>Car Sales</h3>
                <p>Record and manage car sales easily.</p>
                <a href="CarSale.jsp">Manage Sales</a>
            </div>
            <div class="card">
                <h3>Sales Statistics</h3>
                <p>Analyze revenue and performance data.</p>
                <a href="SaleStatistics.jsp">View Statistics</a>
            </div>
        </div>
    </div>
    <footer>
        &copy; 2024 Car Dealership Management. All rights reserved.
    </footer>
</body>
</html>
