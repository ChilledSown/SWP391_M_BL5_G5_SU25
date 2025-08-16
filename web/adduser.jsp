<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>
    <link rel="stylesheet" href="style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
        }
        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
        }
        .sidebar-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .sidebar-nav ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar-nav li {
            margin-bottom: 10px;
            border-radius: 5px;
        }
        .sidebar-nav li:hover {
            background-color: #34495e;
        }
        .sidebar-nav li.active {
            background-color: #3498db;
        }
        .sidebar-nav a {
            display: block;
            padding: 15px 20px;
            color: white;
            text-decoration: none;
            font-size: 16px;
        }
        .main-content {
            flex-grow: 1;
            padding: 30px;
        }
        .main-header {
            margin-bottom: 20px;
        }
        .form-container {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            max-width: 600px;
            margin: 0 auto;
        }
        .form-container label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-container input, .form-container select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-container button {
            background-color: #2ecc71;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .form-container button:hover {
            background-color: #27ae60;
        }
        .form-container .back-btn {
            background-color: #3498db;
            margin-left: 10px;
        }
        .form-container .back-btn:hover {
            background-color: #2980b9;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>Admin Dashboard</h3>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li data-section="overview"><a href="overview">Overview</a></li>
                    <li data-section="courses"><a href="managecourse">Manage Courses</a></li>
                    <li data-section="users" class="active"><a href="manageuser">Manage Users</a></li>
                    <li data-section="settings"><a href="login">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <main class="main-content">
            <header class="main-header">
                <h1>Add User</h1>
            </header>
            <div>
                <c:if test="${not empty error}">
                    <div class="error-message">${error}</div>
                </c:if>
            </div>
            <div class="form-container">
                <form action="manageuser" method="post">
                    <input type="hidden" name="action" value="add">

                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>

                    <label for="middleName">Middle Name</label>
                    <input type="text" id="middleName" name="middleName" value="${user.middleName}" required>

                    <label for="lastName">Last Name</label>
                    <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>

                    <label for="avataUrl">Avatar URL</label>
                    <input type="text" id="avataUrl" name="avataUrl" value="${user.avataUrl}">

                    <label for="phone">Phone</label>
                    <input type="text" id="phone" name="phone" value="${user.phone}" required>

                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${user.address}" required>

                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${user.email}" required>

                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>

                    <label for="role">Role</label>
                    <select id="role" name="role" required>
                        <option value="admin" <c:if test="${user.role == 'admin'}">selected</c:if>>Admin</option>
                        <option value="seller" <c:if test="${user.role == 'seller'}">selected</c:if>>Seller</option>
                        <option value="customer" <c:if test="${user.role == 'customer'}">selected</c:if>>Customer</option>
                    </select>

                    <label for="accountStatus">Account Status</label>
                    <select id="accountStatus" name="accountStatus" required>
                        <option value="active" <c:if test="${user.accountStatus == 'active'}">selected</c:if>>Active</option>
                        <option value="inactive" <c:if test="${user.accountStatus == 'inactive'}">selected</c:if>>Inactive</option>
                        <option value="suspended" <c:if test="${user.accountStatus == 'suspended'}">selected</c:if>>Suspended</option>
                    </select>

                    <button type="submit">Add User</button>
                    <a href="manageuser"><button type="button" class="back-btn">Back</button></a>
                </form>
            </div>
        </main>
    </div>
</body>
</html>