<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Sale</title>
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
    <header>Car Dealership Management - Car Sale</header>
    <nav>
        <a href="Index.jsp">Home</a>
        <a href="CarSale.jsp">Car Sale</a>
        <a href="Service.jsp">Service</a>
        <a href="SaleStatistics.jsp">Sales Statistics</a>
    </nav>
    <div class="container">
        <h2>Sell a Car</h2>
        <form action="CarSaleServlet" method="post">
            <!-- Customer Information -->
            <fieldset>
                <legend>Customer Information</legend>
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" placeholder="Enter First Name" required>
                </div>
                <div class="form-group">
                    <label for="middleInitial">Middle Initial:</label>
                    <input type="text" id="middleInitial" name="middleInitial" maxlength="1" placeholder="Enter Middle Initial">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" placeholder="Enter Last Name" required>
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number:</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Enter Phone Number" required>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" placeholder="Enter Email Address" required>
                </div>
                <div class="form-group">
    				<label for="aptNumber">Apartment Number (Optional):</label>
    				<input type="text" id="aptNumber" name="aptNumber" placeholder="Enter Apartment Number">
				</div>
				<div class="form-group">
    				<label for="streetNumber">Street Address:</label>
   					 <input type="text" id="streetNumber" name="streetNumber" placeholder="Enter Street Address" required>
				</div>
                <div class="form-group">
                    <label for="city">City:</label>
                    <input type="text" id="city" name="city" placeholder="Enter City" required>
                </div>
            </fieldset>

            <!-- Vehicle Information -->
            <fieldset>
                <legend>Vehicle Information</legend>
                <div class="form-group">
                    <label for="carId">Car ID:</label>
                    <input type="text" id="carId" name="carId" placeholder="Enter Car ID" required>
                </div>
                <div class="form-group">
                    <label for="salePrice">Sale Price ($):</label>
                    <input type="number" id="salePrice" name="salePrice" placeholder="Enter Sale Price" step="0.01" required>
                </div>
                <div class="form-group">
                    <label for="purchaseDate">Purchase Date:</label>
                    <input type="date" id="purchaseDate" name="purchaseDate" required>
                </div>
            </fieldset>

            <!-- Actions -->
            <div class="form-actions">
                <button type="submit">Submit Sale</button>
                 <button type="button" onclick="location.href='GenerateBillServlet?carId=' + document.getElementById('carId').value;">View Bill</button>
            </div>
        </form>
    </div>
</body>
</html>