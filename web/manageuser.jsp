<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Management Users</title>
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

            .user-table {
                width: 100%;
                border-collapse: collapse;
                background-color: white;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                border-radius: 5px;
                overflow: hidden;
            }

            .user-table th, .user-table td {
                padding: 12px 15px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            .user-table th {
                background-color: #f8f9fa;
                font-weight: bold;
            }

            .user-table tr:hover {
                background-color: #f5f5f5;
            }

            .pagination {
                margin-top: 20px;
                text-align: center;
            }

            .pagination a {
                padding: 8px 16px;
                text-decoration: none;
                color: #3498db;
                border: 1px solid #ddd;
                margin: 0 4px;
                border-radius: 4px;
                transition: background-color 0.3s;
            }

            .pagination a:hover {
                background-color: #3498db;
                color: white;
            }

            .pagination .active {
                background-color: #3498db;
                color: white;
            }

            .pagination .disabled {
                color: #ccc;
                pointer-events: none;
            }

            .create-btn {
                background-color: #3498db;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .create-btn:hover {
                background-color: #2980b9;
            }
            /* Search Section */
            .search-section {
                margin-bottom: 20px;
                display: flex;
                gap: 10px;
                align-items: center;
            }

            .search-section input[type="text"] {
                padding: 8px 12px;
                font-size: 16px;
                border: 1px solid #ddd;
                border-radius: 4px;
                width: 300px;
            }

            .search-section button {
                background-color: #2ecc71;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }

            .search-section button:hover {
                background-color: #27ae60;
            }
            .reset-btn {
                background-color: #e67e22;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }

            .reset-btn:hover {
                background-color: #d35400;
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
                            <a href="managecourse">Manage Courses</a>
                        </li>
                        <li class="active" data-section="users">
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
                <!-- Search Section -->
                <div class="search-section">
                    <form action="manageuser" method="get">
                        <input type="text" name="searchQuery" value="${searchQuery}" placeholder="Enter user name...">                
                        <button type="submit">Apply</button>
                        <a href="manageuser"><button type="button" class="reset-btn">Reset</button></a>
                    </form>
                </div>
                <a href="manageuser?action=add"><button class="create-btn">Create User</button></a>
                <!-- User Table -->
                <c:choose>
                    <c:when test="${empty users}">
                        <div style="color:red; text-align:center; padding: 20px;">Not found record</div>
                    </c:when>
                    <c:otherwise>
                        <table class="user-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Phone</th>
                                    <th>Address</th>
                                    <th>Email</th>
                                    <th>Role</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.user_id}</td>
                                        <td>${user.fullName}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.address}</td>
                                        <td>${user.email}</td>
                                        <td>${user.role}</td>
                                        <td>
                                            <!-- Edit form (simple inline, or use modal for better UX) -->
                                            <form action="manageuser" method="get" style="display:inline;">
                                                <input type="hidden" name="action" value="edit">
                                                <input type="hidden" name="userId" value="${user.user_id}">
                                                <button type="submit" class="edit-btn">Edit</button>
                                            </form>
                                            <form action="manageuser" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="userId" value="${user.user_id}">
                                                <button type="submit" class="delete-btn" onclick="return confirm('Are you sure to delete?')">Delete</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>

                <!-- Pagination -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="manageuser?page=${currentPage - 1}&searchQuery=${searchQuery}">Previous</a>
                    </c:if>
                    <c:if test="${currentPage <= 1}">
                        <a class="disabled">Previous</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="manageuser?page=${i}&searchQuery=${searchQuery}" <c:if test="${currentPage == i}">class="active"</c:if>>${i}</a>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="manageuser?page=${currentPage + 1}&searchQuery=${searchQuery}">Next</a>
                    </c:if>
                    <c:if test="${currentPage >= totalPages}">
                        <a class="disabled">Next</a>
                    </c:if>
                </div>
            </main>           
        </div>     
    </body>
</html>