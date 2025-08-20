<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Edit Slider</title>
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
        .content-section form label {
            display: block;
            margin-bottom: 10px;
            font-weight: bold;
        }
        .content-section form input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .content-section form input[readonly] {
            background-color: #f8f9fa;
            cursor: not-allowed;
        }
        .content-section form button {
            padding: 8px 16px;
            background-color: #2ecc71;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .content-section form button:hover {
            background-color: #27ae60;
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
                <h2>Edit Slider</h2>
                <c:choose>
                    <c:when test="${not empty slider}">
                        <form action="manageslider" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="sliderId" value="${slider.slider_id}">
                            <label>ID:
                                <input type="text" value="${slider.slider_id}" readonly>
                            </label>
                            <label>Title:
                                <input type="text" name="title" value="${slider.title}" required>
                            </label>
                            <label>Image URL:
                                <input type="text" name="imageUrl" value="${slider.image_url}" required>
                            </label>
                            <label>Created At:
                                <input type="text" value="<fmt:formatDate value="${slider.created_at}" pattern="yyyy-MM-dd" />" readonly>
                            </label>
                            <label>Updated At:
                                <input type="text" value="<fmt:formatDate value="${slider.updated_at}" pattern="yyyy-MM-dd" />" readonly>
                            </label>
                            <button type="submit">Save</button>
                        </form>
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