<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Slider Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
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
        }
        .sidebar-nav a:hover {
            color: #ecf0f1;
        }
        .main-content {
            flex-grow: 1;
            padding: 30px;
        }
        .main-header {
            margin-bottom: 20px;
        }
        .content-section {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }
        .content-section h2 {
            margin-top: 0;
        }
        .content-section p {
            margin: 10px 0;
        }
        .back-button {
            display: inline-block;
            padding: 8px 16px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .back-button:hover {
            background-color: #2980b9;
        }
        .success-message {
            color: green;
            text-align: center;
            margin-bottom: 20px;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <c:if test="${empty sessionScope.user}">
        <c:redirect url="login"/>
    </c:if>
    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>Admin Dashboard</h3>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li data-section="overview"><a href="overview">Overview</a></li>
                    <li data-section="courses"><a href="admintopic">Manage Topic</a></li>
                    <li data-section="users"><a href="manageuser">Manage Users</a></li>
                    <li data-section="slider" class="active"><a href="manageslider">Manage Slider</a></li>
                    <li data-section="settings"><a href="login">Logout</a></li>
                </ul>
            </nav>
        </aside>
        <main class="main-content">
            <header class="main-header">
                <h1>Welcome, <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName != null ? sessionScope.user.middleName : ''} ${sessionScope.user.lastName}" />!</h1>
            </header>
            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <div class="content-section">
                <a href="manageslider" class="back-button">Back to Slider List</a>
                <h2>Slider Details</h2>
                <c:choose>
                    <c:when test="${not empty slider}">
                        <p><strong>ID:</strong> <c:out value="${slider.slider_id}" /></p>
                        <p><strong>Title:</strong> <c:out value="${slider.title}" /></p>
                        <p><strong>Image URL:</strong> <c:out value="${slider.image_url}" /></p>
                        <p><strong>Created At:</strong> <fmt:formatDate value="${slider.created_at}" pattern="yyyy-MM-dd" /></p>
                        <p><strong>Updated At:</strong> <fmt:formatDate value="${slider.updated_at}" pattern="yyyy-MM-dd" /></p>
                    </c:when>
                    <c:otherwise>
                        <p class="error-message">Slider not found.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</body>
</html>