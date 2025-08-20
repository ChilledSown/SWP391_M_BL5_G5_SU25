<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Details</title>
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
            width: 200px; /* Cố định độ rộng giống updateuser.jsp */
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            position: relative;
        }
        .sidebar-header {
            text-align: center;
            margin-bottom: 30px;
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
        .sidebar-nav ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar-nav li {
            margin-bottom: 10px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
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
        }
        .main-header {
            margin-bottom: 20px;
        }
        .details-container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .details-field {
            margin-bottom: 15px;
        }
        .details-field label {
            font-weight: 500;
            display: block;
        }
        .details-field span {
            padding: 8px;
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 4px;
            display: block;
        }
        .back-btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .back-btn:hover {
            background-color: #2980b9;
        }
        @media (max-width: 768px) {
            .sidebar {
                width: 100px; /* Thu nhỏ sidebar trên mobile */
            }
            .sidebar-nav a {
                font-size: 14px;
                padding: 10px 15px;
            }
            .profile-avatar {
                width: 60px;
                height: 60px;
            }
            .profile-name {
                font-size: 14px;
            }
            .edit-profile-btn {
                top: -40px;
                left: 20px;
                font-size: 14px;
            }
            .details-container {
                padding: 15px;
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
                    <li data-section="overview"><a href="admin">Overview</a></li>
                    <li data-section="courses"><a href="managecourse">Manage Courses</a></li>
                    <li data-section="users" class="active"><a href="manageuser">Manage Users</a></li>
                    <li data-section="settings"><a href="login">Logout</a></li>
                </ul>
            </nav>
        </aside>

        <main class="main-content">
            <header class="main-header">
                <h1>User Details</h1>
            </header>
            <div class="details-container">
                <div class="details-field">
                    <label>ID</label>
                    <span>${user.user_id}</span>
                </div>
                <div class="details-field">
                    <label>Full Name</label>
                    <span>${user.firstName} ${user.middleName} ${user.lastName}</span>
                </div>
                <div class="details-field">
                    <label>Phone</label>
                    <span>${user.phone}</span>
                </div>
                <div class="details-field">
                    <label>Address</label>
                    <span>${user.address}</span>
                </div>
                <div class="details-field">
                    <label>Email</label>
                    <span>${user.email}</span>
                </div>
                <div class="details-field">
                    <label>Avatar URL</label>
                    <span>${user.avataUrl}</span>
                </div>
                <div class="details-field">
                    <label>Role</label>
                    <span>${user.role}</span>
                </div>
                <div class="details-field">
                    <label>Account Status</label>
                    <span>${user.accountStatus}</span>
                </div>
                <div class="details-field">
                    <label>Created At</label>
                    <span><fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy HH:mm" /></span>
                </div>
                <div class="details-field">
                    <label>Updated At</label>
                    <span><fmt:formatDate value="${user.updatedAt}" pattern="dd/MM/yyyy HH:mm" /></span>
                </div>
                <a href="manageuser" class="back-btn">Back to Manage Users</a>
            </div>
        </main>
    </div>
</body>
</html>