<%@ page import="java.util.List, java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Service Management</title>
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
            padding: 5px 20px;
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
        }
        .section {
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            overflow: hidden;
        }
        .section-header {
            background-color: #0078d7;
            color: white;
            padding: 10px;
            cursor: pointer;
            text-align: center;
            font-size: 18px;
        }
        .section-body {
            display: none;
            padding: 15px;
        }
        .section-body.active {
            display: block;
        }
        fieldset {
            margin-bottom: 15px;
            border: 1px solid #ddd;
            padding: 10px;
            border-radius: 5px;
        }
        legend {
            font-weight: bold;
            font-size: 1.1em;
        }
        label {
            display: block;
            margin: 5px 0;
        }
        input, select, button {
            padding: 8px;
            margin: 5px 0;
            width: 100%;
            max-width: 400px;
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
    </style>
    <script>
        function toggleSection(sectionId) {
            const section = document.getElementById(sectionId);
            section.classList.toggle('active');
        }
    </script>
</head>
<body>
    <header>Service Management</header>
    <nav>
        <a href="Index.jsp">Home</a>
        <a href="CarSale.jsp">Car Sale</a>
        <a href="Service.jsp">Service</a>
        <a href="SaleStatistics.jsp">Sales Statistics</a>
    </nav>
    <div class="container">
        <h2>Service Management</h2>

        <!-- Schedule Service Section -->
        <div class="section">
            <div class="section-header" onclick="toggleSection('scheduleSection')">
                Schedule a Service
            </div>
            <div id="scheduleSection" class="section-body">
                <form action="ScheduleServiceServlet" method="post">
                    <fieldset>
                        <legend>Customer and Vehicle Information</legend>
                        <label for="customerId">Customer ID:</label>
                        <input type="text" id="customerId" name="customerId" required>

                        <label for="carId">Car ID:</label>
                        <input type="text" id="carId" name="carId" required>
                    </fieldset>
                    <fieldset>
                        <legend>Service Details</legend>
    <label for="appointmentDate">Appointment Date:</label>
    <input type="date" id="appointmentDate" name="appointmentDate" required>

    <label for="startTime">Start Time:</label>
    <select id="startTime" name="startTime" required>
        <option value="09:00:00">9:00 AM</option>
        <option value="10:00:00">10:00 AM</option>
        <option value="11:00:00">11:00 AM</option>
        <option value="13:00:00">1:00 PM</option>
        <option value="14:00:00">2:00 PM</option>
        <option value="15:00:00">3:00 PM</option>
   					 </select>
    					<label for="endTime">End Time:</label>
    					<select id="endTime" name="endTime" required>
        					<option value="10:00:00">10:00 AM</option>
        					<option value="11:00:00">11:00 AM</option>
        					<option value="12:00:00">12:00 PM</option>
        					<option value="14:00:00">2:00 PM</option>
        					<option value="15:00:00">3:00 PM</option>
        					<option value="16:00:00">4:00 PM</option>
						</select>
                        <label for="package">Package Name:</label>
                        <select id="package" name="package" required>
                            <option value="1-Year Service">1-Year Service</option>
                            <option value="2-Year Comprehensive Check">2-Year Comprehensive Check</option>
                            <option value="3-Year Premium Service">3-Year Premium Service</option>
                            <option value="5-Year Extended Care">5-Year Extended Care</option>
                            <option value="7-Year High-Mileage Package">7-Year High-Mileage Package</option>
                        </select>
                    </fieldset>
                    <button type="submit">Schedule Service</button>
                </form>
            </div>
        </div>

        <!-- Render Service Section -->
  	<div class="section">
    <div class="section-header" onclick="toggleSection('renderSection')">
        Render a Service
    </div>
    <div id="renderSection" class="section-body">
        <form action="RenderServiceServlet" method="post">
            <fieldset>
    <legend>Render Service</legend>
    <label for="appointmentId">Appointment ID:</label>
    <input type="text" id="appointmentId" name="appointmentId" required>

    <div id="tasksContainer">
        <label for="taskIds">Task ID:</label>
        <input type="text" id="taskIds" name="taskIds" placeholder="Enter Task ID" required>
    </div>
    <button type="button" onclick="addTaskField()">Add Another Task</button>

    <div id="partsContainer">
        <label for="partIds">Part ID:</label>
        <input type="text" id="partIds" name="partIds" placeholder="Enter Part ID" required>
    </div>
    <button type="button" onclick="addPartField()">Add Another Part</button>

    <label for="laborCost">Labor Cost ($):</label>
    <input type="number" id="laborCost" name="laborCost" placeholder="Enter Labor Cost" required>

    <label for="dropOff">Drop Off (yyyy-MM-dd HH:mm:ss):</label>
    <input type="text" id="dropOff" name="dropOff" placeholder="Enter Drop Off Time" required>

    <label for="pickUp">Pick Up (yyyy-MM-dd HH:mm:ss):</label>
    <input type="text" id="pickUp" name="pickUp" placeholder="Enter Pick Up Time" required>

    <button type="submit">Render Service</button>
</fieldset>

        </form>

        <!-- Task Reference Table -->
        <h3>Task Reference Table</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Task ID</th>
                    <th>Name</th>
                    <th>Estimated Time</th>
                    <th>Labor Cost</th>
                    <th>Task Type</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>1</td><td>Engine Diagnostic</td><td>0:30:00</td><td>$50</td><td>Test</td></tr>
                <tr><td>2</td><td>Spark Plug Replacement</td><td>0:40:00</td><td>$70</td><td>Part</td></tr>
                <tr><td>3</td><td>Oil Filter Replacement</td><td>0:20:00</td><td>$25</td><td>Part</td></tr>
                <tr><td>4</td><td>Emission Test</td><td>0:20:00</td><td>$40</td><td>Test</td></tr>
                <tr><td>5</td><td>Tire Pressure and Tread Depth</td><td>0:05:00</td><td>$15</td><td>Test</td></tr>
                <tr><td>6</td><td>Tire Replacement</td><td>0:30:00</td><td>$50</td><td>Part</td></tr>
                <tr><td>7</td><td>Alignment</td><td>1:00:00</td><td>$50</td><td>Part</td></tr>
                <tr><td>8</td><td>Brake Pad Inspection</td><td>0:30:00</td><td>$30</td><td>Test</td></tr>
                <tr><td>9</td><td>Brake Pad Replacement</td><td>0:45:00</td><td>$80</td><td>Part</td></tr>
                <tr><td>10</td><td>Battery Health Check</td><td>0:15:00</td><td>$20</td><td>Test</td></tr>
                <tr><td>11</td><td>Battery Replacement</td><td>0:20:00</td><td>$30</td><td>Part</td></tr>
                <tr><td>12</td><td>Cooling System Pressure Test</td><td>0:30:00</td><td>$50</td><td>Test</td></tr>
                <tr><td>13</td><td>Radiator Replacement</td><td>3:00:00</td><td>$300</td><td>Part</td></tr>
                <tr><td>14</td><td>Water Pump Replacement</td><td>3:00:00</td><td>$300</td><td>Part</td></tr>
                <tr><td>15</td><td>Electrical System Test</td><td>0:30:00</td><td>$40</td><td>Test</td></tr>
                <tr><td>16</td><td>Alternator Replacement</td><td>1:30:00</td><td>$200</td><td>Part</td></tr>
                <tr><td>17</td><td>Drive Belt Inspection</td><td>0:15:00</td><td>$20</td><td>Test</td></tr>
                <tr><td>18</td><td>Timing Belt Replacement</td><td>4:00:00</td><td>$400</td><td>Part</td></tr>
            </tbody>
        </table>

        <!-- Part Reference Table -->
        <h3>Part Reference Table</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>Part ID</th>
                    <th>Name</th>
                    <th>Cost</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>1</td><td>Spark Plug (V8)</td><td>$25</td></tr>
                <tr><td>2</td><td>Spark Plug (V6)</td><td>$20</td></tr>
                <tr><td>3</td><td>Spark Plug (V4)</td><td>$20</td></tr>
                <tr><td>4</td><td>Oil Pan</td><td>$80</td></tr>
                <tr><td>5</td><td>All-Terrain Tire</td><td>$120</td></tr>
                <tr><td>6</td><td>Highway Tires</td><td>$100</td></tr>
                <tr><td>7</td><td>T350 Van Dually Tires</td><td>$200</td></tr>
                <tr><td>8</td><td>All-Season Tires</td><td>$100</td></tr>
                <tr><td>9</td><td>Alignment</td><td>$0</td></tr>
                <tr><td>10</td><td>Brake Pads</td><td>$80</td></tr>
                <tr><td>11</td><td>Battery</td><td>$60</td></tr>
                <tr><td>12</td><td>Radiator</td><td>$300</td></tr>
                <tr><td>13</td><td>Radiator Cap</td><td>$5</td></tr>
                <tr><td>14</td><td>Alternator Belt</td><td>$20</td></tr>
                <tr><td>15</td><td>Alternator</td><td>$150</td></tr>
                <tr><td>16</td><td>Timing Belt</td><td>$200</td></tr>
                <tr><td>17</td><td>Timing Belt Tensioner</td><td>$30</td></tr>
                <tr><td>18</td><td>Battery Module</td><td>$500</td></tr>
            </tbody>
        </table>
    </div>
</div>

<script>
    function addTaskField() {
        const container = document.getElementById('tasksContainer');
        const input = document.createElement('input');
        input.type = 'text';
        input.name = 'taskIds';
        input.placeholder = 'Enter Task ID';
        input.required = true;
        container.appendChild(input);
    }

    function addPartField() {
        const container = document.getElementById('partsContainer');
        const input = document.createElement('input');
        input.type = 'text';
        input.name = 'partIds';
        input.placeholder = 'Enter Part ID';
        input.required = true;
        container.appendChild(input);
    }
</script>

        <!-- View Invoice Section -->
        <div class="section">
    		<div class="section-header" onclick="toggleSection('invoiceSection')">
        		View Invoice
    		</div>
    		<div id="invoiceSection" class="section-body">
        		<form action="InvoiceServlet" method="get">
            		<fieldset>
                		<legend>View Invoice</legend>
                		<label for="AppointmentId">Appointment ID:</label>
                		<input type="text" id="AppointmentId" name="appointmentId" required>
            		</fieldset>
            		<button type="submit">View Invoice</button>
        		</form>
    		</div>
		</div>
    </div>
</body>
</html>
