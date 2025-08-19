<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link rel="stylesheet" href="style.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
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
                transition: width 0.3s ease-in-out;
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
                transition: background-color 0.3s ease;
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
                transition: color 0.3s ease;
            }
            .sidebar-nav a:hover {
                color: #ecf0f1;
            }
            .main-content {
                flex-grow: 1;
                padding: 30px;
                transition: margin-left 0.3s ease-in-out;
            }
            .main-header {
                margin-bottom: 20px;
            }
            .profile-form {
                background-color: white;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                max-width: 600px;
                margin: 0 auto;
            }
            .profile-form h2 {
                text-align: center;
                margin-bottom: 20px;
            }
            .form-group {
                margin-bottom: 20px;
            }
            .form-group label {
                display: block;
                font-weight: 500;
                margin-bottom: 5px;
            }
            .form-group input,
            .form-group select {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 16px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .form-group input:focus,
            .form-group select:focus {
                border-color: #3498db;
                box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
                outline: none;
            }
            .form-group input[type="file"] {
                padding: 3px;
            }
            .avatar-preview {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                display: block;
                margin: 0 auto 10px;
                border: 2px solid #3498db;
            }
            .form-actions {
                text-align: center;
            }
            .save-btn {
                background-color: #2ecc71;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }
            .save-btn:hover {
                background-color: #27ae60;
            }
            .error-message {
                color: #ff4d4d;
                background-color: #ffe6e6;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
                text-align: center;
            }
            .success-message {
                color: #2ecc71;
                background-color: #e6ffe6;
                padding: 10px;
                border-radius: 5px;
                margin-bottom: 20px;
                text-align: center;
            }
            .profile-section {
                text-align: center;
                margin-bottom: 30px;
            }
            .profile-avatar {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #3498db;
            }
            .profile-name {
                margin: 10px 0;
                font-size: 18px;
                font-weight: 500;
            }
             .edit-profile-btn {
                background-color: transparent;
                border: none;
                color: #3498db;
                font-size: 16px;
                cursor: pointer;
                position: relative;
                top: -50px; 
                left: 30px; 
                transition: color 0.3s ease;
            }
            .edit-profile-btn:hover {
                color: #2980b9;
            }
        </style>
    </head>
    <body>
        <div class="dashboard-container"> 
            <aside class="sidebar">
                <div class="sidebar-header">
                    <h3>Admin Dashboard</h3>
                </div>
                <div class="profile-section">
                    <img src="${empty sessionScope.user.avataUrl ? 'assets/img/author.png' : sessionScope.user.avataUrl}" alt="Avatar" class="profile-avatar">
                    <div class="profile-name">
                        <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName} ${sessionScope.user.lastName}"/>
                    </div>
                     <a href="${pageContext.request.contextPath}/profile"><button class="edit-profile-btn" title="Edit Profile"><i class="fas fa-edit"></i></button></a>
                </div>
                <nav class="sidebar-nav">
                    <ul>
                        <li data-section="overview">
                            <a href="admin">Overview</a>
                        </li>
                        <li data-section="courses">
                            <a href="managetopic">Manage Topic</a>
                        </li>
                        <li data-section="users">
                            <a href="manageuser">Manage Users</a>
                        </li>
                        <li data-section="settings">
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </li>
                    </ul>
                </nav>
            </aside>
            <main class="main-content">
                <header class="main-header">
                    <h1>Welcome, to User Profile</h1>
                </header>
                <div class="profile-form">
                    <h2>User Profile</h2>
                    <c:if test="${not empty error}">
                        <div class="error-message">${error}</div>
                    </c:if>
                    <c:if test="${not empty message}">
                        <div class="success-message">${message}</div>
                    </c:if>
                    <form action="profile" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="userId" value="${user.user_id}">
                        <div class="form-group">
                            <label for="avatar">Profile Picture</label>
                            <img src="${empty user.avataUrl ? 'assets/img/default-avatar.png' : user.avataUrl}" alt="Avatar" class="avatar-preview">
                            <input type="file" id="avatar" name="avatar" accept="image/*">
                        </div>
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
                        </div>
                        <div class="form-group">
                            <label for="middleName">Middle Name</label>
                            <input type="text" id="middleName" name="middleName" value="${user.middleName}" required>
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="text" id="phone" name="phone" value="${user.phone}" required>
                        </div>
                        <div class="form-group">
                            <label for="address">Address</label>
                            <input type="text" id="address" name="address" value="${user.address}" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" value="${user.email}" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" placeholder="Leave blank if no change">
                        </div>
                        <!-- Display role and accountStatus as readonly -->
                        <div class="form-group">
                            <label for="role">Role</label>
                            <input type="text" id="role" name="role" value="${user.role}" readonly>
                        </div>
                        <div class="form-group">
                            <label for="accountStatus">Account Status</label>
                            <input type="text" id="accountStatus" name="accountStatus" value="${user.accountStatus}" readonly>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="save-btn">Save Changes</button>
                        </div>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>