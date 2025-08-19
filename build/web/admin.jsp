<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Course Management</title>
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
            .content-section {
                display: none;
                opacity: 0;
                transform: translateY(20px);
                transition: opacity 0.5s ease-in-out, transform 0.5s ease-in-out;
            }
            .content-section.active {
                display: block;
                opacity: 1;
                transform: translateY(0);
            }
            .stats-grid {
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 20px;
            }
            .stat-card {
                background-color: white;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.05);
                text-align: center;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .stat-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 6px 12px rgba(0,0,0,0.1);
            }
            .stat-card h3 {
                margin-top: 0;
                font-size: 18px;
                color: #7f8c8d;
            }
            .stat-card p {
                font-size: 36px;
                font-weight: bold;
                color: #3498db;
                margin: 0;
            }
            .course-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                background-color: white;
                padding: 15px 20px;
                margin-bottom: 10px;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .course-item:hover {
                transform: translateX(5px);
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
            .course-name {
                font-weight: 500;
            }
            .course-actions button {
                border: none;
                padding: 8px 12px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                color: white;
                transition: background-color 0.3s ease;
            }
            .edit-btn {
                background-color: #2ecc71;
            }
            .edit-btn:hover {
                background-color: #27ae60;
            }
            .delete-btn {
                background-color: #e74c3c;
                margin-left: 5px;
            }
            .delete-btn:hover {
                background-color: #c0392b;
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
                    <img src="${empty sessionScope.user.avataUrl ? 'assets/img/default-avatar.png' : sessionScope.user.avataUrl}" alt="Avatar" class="profile-avatar">
                    <div class="profile-name">
                        <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName} ${sessionScope.user.lastName}"/>
                    </div>
                     <a href="${pageContext.request.contextPath}/profile"><button class="edit-profile-btn" title="Edit Profile"><i class="fas fa-edit"></i></button></a>
                </div>
                <nav class="sidebar-nav">
                    <ul>
                        <li class="active" data-section="overview">
                            <a href="overview">Overview</a>
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
                <div style="color:green">${message}</div>
            </main>
        </div>
    </body>
</html>