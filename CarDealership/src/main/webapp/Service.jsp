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
        input, button, select {
            padding: 8px;
            margin: 5px 0;
            width: 100%;
            max-width: 400px;
        }
        button {
            cursor: pointer;
        }
    </style>
    <script>
        function toggleSection(sectionId) {
            const section = document.getElementById(sectionId);
            section.classList.toggle('active');
        }

        function fetchTimeSlots(dateInput) {
            const date = dateInput.value;
            const timeSlotSelect = document.getElementById('timeSlotId');

            if (!date) {
                timeSlotSelect.innerHTML = '<option value="">Select a date first</option>';
                return;
            }

            fetch(`GetTimeSlotsServlet?date=${date}`)
                .then(response => response.json())
                .then(data => {
                    timeSlotSelect.innerHTML = '';
                    if (data.length === 0) {
                        timeSlotSelect.innerHTML = '<option value="">No available time slots</option>';
                    } else {
                        data.forEach(slot => {
                            const option = document.createElement('option');
                            option.value = slot.timeSlotId;
                            option.textContent = `${slot.startTime} - ${slot.endTime}`;
                            timeSlotSelect.appendChild(option);
                        });
                    }
                })
                .catch(err => {
                    timeSlotSelect.innerHTML = '<option value="">Error loading time slots</option>';
                    console.error(err);
                });
        }

        function addPartField() {
            const container = document.getElementById('partsContainer');
            const newField = document.createElement('div');
            newField.innerHTML = `
                <label for="partId">Part ID:</label>
                <input type="text" id="partId" name="partIds" required placeholder="Enter Part ID">
                <button type="button" onclick="removePartField(this)">Remove</button>
            `;
            container.appendChild(newField);
        }

        function removePartField(button) {
            const container = document.getElementById('partsContainer');
            container.removeChild(button.parentElement);
        }
    </script>
</head>
<body>
    <header>Car Dealership Management - Service Management</header>
    <nav>
        <a href="Index.jsp">Home</a>
        <a href="CarSale.jsp">Car Sale</a>
        <a href="Service.jsp">Service</a>
        <a href="SaleStatistics.jsp">Sales Statistics</a>
    </nav>
    <div class="container">
        <h2>Select an Option</h2>

        <!--schedule service section -->
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
                        <label for="date">Select Date:</label>
                        <input type="date" id="date" name="date" onchange="fetchTimeSlots(this)" required>

                        <label for="timeSlotId">Time Slot:</label>
                        <select id="timeSlotId" name="timeSlotId" required>
                            <option value="">Select a date first</option>
                        </select>

                        <label for="packageId">Package ID:</label>
                        <input type="text" id="packageId" name="packageId" required>
                    </fieldset>
                    <button type="submit">Schedule Service</button>
                </form>
            </div>
        </div>

        <!-- render service section -->
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

                        <label for="taskId">Task ID:</label>
                        <input type="text" id="taskId" name="taskId" required>

                        <label for="laborCost">Labor Cost:</label>
                        <input type="number" id="laborCost" name="laborCost" step="0.01" required>

                        <label for="time">Time Taken (HH:MM):</label>
                        <input type="text" id="time" name="time" required>

                        <div id="partsContainer">
                            <label for="partId">Part ID:</label>
                            <input type="text" id="partId" name="partIds" required>
                        </div>
                        <button type="button" onclick="addPartField()">Add Another Part</button>
                    </fieldset>
                    <button type="submit">Render Service</button>
                </form>
            </div>
        </div>

        <!-- view invoice section -->
        <div class="section">
            <div class="section-header" onclick="toggleSection('invoiceSection')">
                View Invoice
            </div>
            <div id="invoiceSection" class="section-body">
                <form action="Invoice.jsp" method="get">
                    <fieldset>
                        <legend>View Invoice</legend>
                        <label for="invoiceAppointmentId">Appointment ID:</label>
                        <input type="text" id="invoiceAppointmentId" name="appointmentId" required>
                    </fieldset>
                    <button type="submit">View Invoice</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
