<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Overview</title>
    <link rel="stylesheet" href="style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
            width: 200px;
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
        .stats-container {
            display: flex;
            gap: 20px;
            margin-bottom: 30px;
            flex-wrap: wrap;
        }
        .stat-card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            flex: 1;
            min-width: 200px;
            text-align: center;
            transition: transform 0.3s ease;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .stat-card h3 {
            margin: 0;
            font-size: 18px;
            color: #555;
        }
        .stat-card p {
            font-size: 24px;
            font-weight: bold;
            margin: 10px 0 0;
            color: #3498db;
        }
        .chart-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        .chart-container canvas {
            max-width: 100%;
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
            .stats-container {
                flex-direction: column;
            }
            .stat-card {
                min-width: 100%;
            }
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
                        <li class="active"  data-section="overview">
                            <a href="overviewadmin">Overview</a>
                        </li>
                        <li data-section="courses">
                        <a href="admintopic">List Topic</a>
                        </li>
                        <li data-section="users">
                            <a href="manageuser">Manage Users</a>
                        </li>
                        <li data-section="slider">
                            <a href="manageslider">Manage Slider</a>
                        </li>
                        <li data-section="settings">
                            <a href="${pageContext.request.contextPath}/logout">Logout</a>
                        </li>
                    </ul>
                </nav>
        </aside>
        <main class="main-content">
            <header class="main-header">
                <h1>Admin Overview</h1>
            </header>
            <!-- Statistics Cards -->
            <div class="stats-container">
                <div class="stat-card">
                    <h3>Total Users</h3>
                    <p>${totalUsers}</p>
                </div>
                <div class="stat-card">
                    <h3>Total Admins</h3>
                    <p>${totalAdmins}</p>
                </div>
                <div class="stat-card">
                    <h3>Total Instructor</h3>
                    <p>${totalInstructors}</p>
                </div>
                <div class="stat-card">
                    <h3>Total Customers</h3>
                    <p>${totalCustomers}</p>
                </div>
            </div>
            <!-- User Growth Chart -->
            <div class="chart-container">
                <h3>User Growth (Last 30 Days)</h3>
                <canvas id="userGrowthChart"></canvas>
            </div>
        </main>
    </div>
    <script>
        // User Growth Chart
        const userGrowthData = {
            labels: [<c:forEach var="entry" items="${userGrowthData}" varStatus="loop">'${entry.key}'<c:if test="${!loop.last}">,</c:if></c:forEach>],
            datasets: [{
                label: 'New Users',
                data: [<c:forEach var="entry" items="${userGrowthData}" varStatus="loop">${entry.value}<c:if test="${!loop.last}">,</c:if></c:forEach>],
                borderColor: '#3498db',
                backgroundColor: 'rgba(52, 152, 219, 0.2)',
                fill: true,
                tension: 0.4
            }]
        };
        new Chart(document.getElementById('userGrowthChart'), {
            type: 'line',
            data: userGrowthData,
            options: {
                responsive: true,
                scales: {
                    x: { title: { display: true, text: 'Date' } },
                    y: { title: { display: true, text: 'Number of Users' }, beginAtZero: true }
                }
            }
        });
    </script>
</body>
</html>