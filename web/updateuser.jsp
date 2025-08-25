<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update User</title>
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
                width: 200px; /* Cố định độ rộng cột menu thành 200px */
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
            .form-container {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                max-width: 600px;
                margin: 0 auto;
                overflow: hidden; /* Ngăn tràn nội dung */
            }
            .form-container label {
                display: block;
                margin-bottom: 5px;
                font-weight: 500;
            }
            .form-container input,
            .form-container select {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ddd;
                border-radius: 4px;
                box-sizing: border-box;
                font-size: 16px; /* Đảm bảo phông chữ đồng nhất */
            }
            .form-container button {
                background-color: #2ecc71;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                font-size: 16px;
                transition: background-color 0.3s ease;
            }
            .form-container button:hover {
                background-color: #27ae60;
            }
            .form-container .back-btn {
                background-color: #3498db;
                margin-left: 10px;
            }
            .form-container .back-btn:hover {
                background-color: #2980b9;
            }
            .error-message {
                color: red;
                margin-bottom: 15px;
                text-align: center;
                padding: 10px;
                background-color: #ffebee;
                border-radius: 4px;
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
                .form-container {
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
                    <h1>Update User</h1>
                </header>
                <div>
                    <c:if test="${not empty error}">
                        <div class="error-message">${error}</div>
                    </c:if>
                </div>
                <div class="form-container">
                    <form action="manageuser" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="userId" value="${user.user_id}">

                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>

                        <label for="middleName">Middle Name</label>
                        <input type="text" id="middleName" name="middleName" value="${user.middleName}" required>

                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>

                        <label for="avataUrl">Avatar </label>
                        <input type="file" id="avataUrl" name="avataUrl" value="${user.avataUrl}">

                        <label for="phone">Phone</label>
                        <input type="text" id="phone" name="phone" value="${user.phone}" required>

                        <label for="address">Address</label>
                        <input type="text" id="address" name="address" value="${user.address}" required>

                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="${user.email}" required>

                        <label for="password">Password (leave blank to keep unchanged)</label>
                        <input type="password" id="password" name="password" placeholder="Enter new password">

                        <label for="role">Role</label>
                        <select id="role" name="role" required>
                            <option value="admin" <c:if test="${user.role == 'admin'}">selected</c:if>>Admin</option>
                            <option value="seller" <c:if test="${user.role == 'seller'}">selected</c:if>>Seller</option>
                            <option value="customer" <c:if test="${user.role == 'customer'}">selected</c:if>>Customer</option>
                        </select>

                        <label for="accountStatus">Account Status</label>
                        <select id="accountStatus" name="accountStatus" required>
                            <option value="active" <c:if test="${user.accountStatus == 'active'}">selected</c:if>>Active</option>
                            <option value="inactive" <c:if test="${user.accountStatus == 'inactive'}">selected</c:if>>Inactive</option>
                            <option value="suspended" <c:if test="${user.accountStatus == 'suspended'}">selected</c:if>>Suspended</option>
                        </select>

                        <button type="submit">Update User</button>
                        <a href="manageuser"><button type="button" class="back-btn">Back</button></a>
                    </form>
                </div>
            </main>
        </div>
    </body>
</html>