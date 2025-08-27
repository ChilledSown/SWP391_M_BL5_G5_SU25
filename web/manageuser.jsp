<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Users</title>
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
            .header-controls {
                display: flex;
                justify-content: space-between;
                align-items: center;
                gap: 20px;
                margin-bottom: 20px;
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
                padding: 10px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                margin-bottom: 20px;
                transition: background-color 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 40px;
                height: 40px;
            }
            .create-btn:hover {
                background-color: #2980b9;
            }
            .search-section {
                display: flex;
                gap: 10px;
                align-items: center;
                flex: 1;
            }
            .search-section input[type="text"] {
                padding: 10px 12px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 300px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .search-section input[type="text"]:focus {
                border-color: #3498db;
                box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
                outline: none;
            }
            .role-section {
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .role-section label {
                font-size: 16px;
                font-weight: 500;
                color: #333;
            }
            .role-section select {
                padding: 10px 12px;
                font-size: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
                width: 150px;
                background-color: #fff;
                cursor: pointer;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .role-section select:focus {
                border-color: #3498db;
                box-shadow: 0 0 5px rgba(52, 152, 219, 0.3);
                outline: none;
            }
            .role-section select:hover {
                border-color: #3498db;
            }
            .search-btn {
                background-color: #2ecc71;
                color: white;
                border: none;
                padding: 10px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 40px;
                height: 40px;
            }
            .search-btn:hover {
                background-color: #27ae60;
            }
            .reset-btn {
                background-color: #e67e22;
                color: white;
                border: none;
                padding: 10px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                transition: background-color 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 40px;
                height: 40px;
            }
            .reset-btn:hover {
                background-color: #d35400;
            }
            .edit-btn {
                background-color: #d35400;
                border: none;
                padding: 8px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                color: white;
                transition: background-color 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 32px;
                height: 32px;
            }
            .edit-btn:hover {
                background-color: #27ae60;
            }
            .delete-btn {
                background-color: red;
                border: none;
                padding: 8px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                color: white;
                margin-left: 5px;
                transition: background-color 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 32px;
                height: 32px;
            }
            .delete-btn:hover {
                background-color: #c0392b;
            }
            .view-btn {
                background-color: #8e44ad;
                border: none;
                padding: 8px;
                border-radius: 4px;
                cursor: pointer;
                font-size: 14px;
                color: white;
                margin-left: 5px;
                transition: background-color 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 32px;
                height: 32px;
            }
            .view-btn:hover {
                background-color: #9b59b6;
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
                .header-controls {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .search-section {
                    width: 100%;
                }
                .search-section input[type="text"] {
                    width: 100%;
                }
                .role-section {
                    width: 100%;
                    justify-content: flex-start;
                }
                .role-section select {
                    width: 100%;
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
                        <li  data-section="overview">
                            <a href="overviewadmin">Overview</a>
                        </li>
                        <li data-section="courses">
                        <a href="admintopic">List Topic</a>
                        </li>
                        <li class="active" data-section="users">
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
                    <h1>Welcome to Manage Users</h1>
                </header>
                <div style="color:green">${message}</div>
                <!-- Search and Role Section -->
                <div class="header-controls">
                    <div class="search-section">
                        <form action="manageuser" method="get">
                            <input type="hidden" name="role" value="${selectedRole}">
                            <input type="text" name="searchQuery" value="${searchQuery}" placeholder="enter user name...">
                            <button type="submit" class="search-btn" title="Search"><i class="fas fa-search"></i></button>
                            <a href="manageuser?role=${selectedRole}"><button type="button" class="reset-btn" title="Reset"><i class="fas fa-rotate"></i></button></a>
                        </form>
                    </div>
                    <div class="role-section">
                        <label for="role">Select Role:</label>
                        <form action="manageuser" method="get">
                            <input type="hidden" name="searchQuery" value="${searchQuery}">
                            <select name="role" id="role" onchange="this.form.submit()">
                                <option value="all" <c:if test="${selectedRole == 'all'}">selected</c:if>>all role</option>
                                <option value="admin" <c:if test="${selectedRole == 'admin'}">selected</c:if>>admin</option>
                                <option value="instructor" <c:if test="${selectedRole == 'instructor'}">selected</c:if>>instructor</option>
                                <option value="customer" <c:if test="${selectedRole == 'customer'}">selected</c:if>>customer</option>
                                </select>
                            </form>
                        </div>
                    </div>
                    <a href="manageuser?action=add"><button class="create-btn" title="Create User"><i class="fas fa-user-plus"></i></button></a>
                    <!-- User Table -->
                <c:choose>
                    <c:when test="${empty users}">
                        <div style="color:red; text-align:center; padding: 20px;">No records found</div>
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
                                    <th>Created At</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="user" items="${users}">
                                    <tr>
                                        <td>${user.user_id}</td>
                                        <td>${user.firstName} ${user.middleName} ${user.lastName}</td>
                                        <td>${user.phone}</td>
                                        <td>${user.address}</td>
                                        <td>${user.email}</td>
                                        <td><fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                        <td>${user.accountStatus}</td>
                                        <td>
                                            <form action="detailuser" method="get" style="display:inline;">
                                                <input type="hidden" name="userId" value="${user.user_id}">
                                                <button type="submit" class="view-btn" title="View Details"><i class="fas fa-eye"></i></button>
                                            </form>
                                            <form action="manageuser" method="get" style="display:inline;">
                                                <input type="hidden" name="action" value="edit">
                                                <input type="hidden" name="userId" value="${user.user_id}">
                                                <button type="submit" class="edit-btn" title="Edit User"><i class="fas fa-edit"></i></button>
                                            </form>
                                            <form action="manageuser" method="post" style="display:inline;">
                                                <input type="hidden" name="action" value="delete">
                                                <input type="hidden" name="userId" value="${user.user_id}">
                                                <button type="submit" class="delete-btn" title="Delete User" onclick="return confirm('Are you sure you want to delete this user?')"><i class="fas fa-trash"></i></button>
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
                        <a href="manageuser?page=${currentPage - 1}&searchQuery=${searchQuery}&role=${selectedRole}">Previous</a>
                    </c:if>
                    <c:if test="${currentPage <= 1}">
                        <a class="disabled">Previous</a>
                    </c:if>

                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="manageuser?page=${i}&searchQuery=${searchQuery}&role=${selectedRole}" <c:if test="${currentPage == i}">class="active"</c:if>>${i}</a>
                    </c:forEach>

                    <c:if test="${currentPage < totalPages}">
                        <a href="manageuser?page=${currentPage + 1}&searchQuery=${searchQuery}&role=${selectedRole}">Next</a>
                    </c:if>
                    <c:if test="${currentPage >= totalPages}">
                        <a class="disabled">Next</a>
                    </c:if>
                </div>
            </main>
        </div>
    </body>
</html>