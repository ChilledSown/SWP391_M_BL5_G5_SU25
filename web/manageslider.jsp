<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Dashboard - Manage Slider</title>
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

            /* Slider Table */
            .slider-table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                margin-top: 20px;
            }

            .slider-table th, .slider-table td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #e0e0e0;
            }

            .slider-table th {
                background-color: #3498db;
                color: white;
                font-weight: 500;
            }

            .slider-table tr:hover {
                background-color: #f9f9f9;
            }

            .slider-actions button {
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
                        <li data-section="courses">
                            <a href="managetopic">Manage Topic</a>
                        </li>
                        <li data-section="users">
                            <a href="manageuser">Manage Users</a>
                        </li>
                        <li data-section="slider" class="active">
                            <a href="manageslider">Manage Slider</a>
                        </li>
                        <li data-section="settings">
                            <a href="login">Logout</a>
                        </li>
                    </ul>
                </nav>  
            </aside>

            <main class="main-content">
                <header class="main-header">
                    <h1>Manage Slider</h1>
                </header>
                <div class="content-section active">
                    <table class="slider-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Title</th>
                                <th>Image</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                         
                            <tr>
                                <td colspan="5">Slider data will be populated here.</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>     
    </body>
</html>