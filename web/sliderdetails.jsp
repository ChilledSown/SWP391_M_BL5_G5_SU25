<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Slider Details</title>
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
            padding: 12px 15px;
            border-bottom: 1px solid #ddd;
        }
        .back-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-bottom: 20px;
            transition: background-color 0.3s ease;
        }
        .back-btn:hover {
            background-color: #2980b9;
        }
        .slider-image {
            max-width: 300px;
            height: auto;
            border-radius: 5px;
            margin-top: 5px;
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
            top: -58px;
            left: 30px;
            transition: color 0.3s ease;
        }
        .edit-profile-btn:hover {
            color: #2980b9;
        }
        @media (max-width: 768px) {
            .content-section {
                padding: 15px;
            }
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
            <div class="profile-section">
                <img src="${empty sessionScope.user.avataUrl ? 'assets/img/default-avatar.png' : sessionScope.user.avataUrl}" alt="Avatar" class="profile-avatar">
                <div class="profile-name">
                    <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName != null ? sessionScope.user.middleName : ''} ${sessionScope.user.lastName}"/>
                </div>
                <a href="${pageContext.request.contextPath}/profile"><button class="edit-profile-btn" title="Edit Profile"><i class="fas fa-edit"></i></button></a>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li data-section="overview"><a href="admin">Overview</a></li>
                    <li data-section="courses"><a href="admintopic">List Topic</a></li>
                    <li data-section="users"><a href="manageuser">Manage Users</a></li>
                    <li data-section="slider" class="active"><a href="manageslider">Manage Slider</a></li>
                    <li data-section="settings"><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </nav>
        </aside>
        <main class="main-content">
            <header class="main-header">
                <h1>Welcome to Slider Details</h1>
            </header>
            <div class="content-section">
                <a href="manageslider" class="back-btn">Back to Slider List</a>
                <h2>Slider Details</h2>
                <c:choose>
                    <c:when test="${not empty slider}">
                        <p><strong>ID:</strong> <c:out value="${slider.slider_id}" /></p>
                        <p><strong>Title:</strong> <c:out value="${slider.title}" /></p>
                        <p><strong>Image:</strong><br>
                           <img src="${slider.image_url.startsWith('http') ? slider.image_url : pageContext.request.contextPath.concat(slider.image_url)}?t=<%= System.currentTimeMillis() %>" alt="Slider Image" class="slider-image" onerror="this.src='${pageContext.request.contextPath}/assets/img/default-slider.png'" />
                        </p>
                        <p><strong>Created At:</strong> <fmt:formatDate value="${slider.created_at}" pattern="dd/MM/yyyy" /></p>
                        <p><strong>Updated At:</strong> <fmt:formatDate value="${slider.updated_at}" pattern="dd/MM/yyyy" /></p>
                    </c:when>
                    <c:otherwise>
                        <div style="color:red; text-align:center; padding: 20px;">Slider not found</div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>
</body>
</html>