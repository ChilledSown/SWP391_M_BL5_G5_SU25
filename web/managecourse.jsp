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
        <style>
            /* style.css */
            /* Google Fonts */
            body {
                font-family: 'Roboto', sans-serif;
                margin: 0;
                background-color: #f4f7f9;
                color: #333;
            }

            /* Dashboard Layout */
            .dashboard-container {
                display: flex;
                min-height: 100vh;
            }

            /* Sidebar */
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

            /* Main Content */
            .main-content {
                flex-grow: 1;
                padding: 30px;
                transition: margin-left 0.3s ease-in-out;
            }

            .main-header {
                margin-bottom: 20px;
            }

            /* Content Sections */
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

            /* Stat Cards (Overview) */
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

            /* Course List (Manage Courses) */
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
                        <li data-section="overview">
                            <a href="overview">Overview</a>
                        </li>
                        <li class="active" data-section="courses">
                            <a href="managecourse">Manage Courses</a>
                        </li>
                        <li data-section="users">
                            <a href="manageuser">Manage Users</a>
                        </li>               
                        <li data-section="settings">
                            <a href="login">Logout</a>
                        </li>
                    </ul>
                </nav>  
            </aside>

            <main class="main-content">
                <header class="main-header">
                    <h1>Welcome, <c:out value="${sessionScope.user.fullName}" />!</h1>
                </header>
                <div style="color:green">${message}</div>                         
                
                
                
            </main>
        </div>     
    </body>
</html>