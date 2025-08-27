<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Sliders</title>
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
                margin-right: 5px;
                text-decoration: none;
            }
            .edit-btn:hover {
                background-color: #27ae60;
            }
            .delete-btn {
                background-color: #dc3545; /* Changed to red */
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
                margin-right: 5px;
                text-decoration: none;
            }
            .delete-btn:hover {
                background-color: #c82333; /* Darker red on hover */
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
            .slider-image {
                max-width: 100px;
                height: auto;
                border-radius: 5px;
                margin-top: 5px;
                cursor: pointer;
            }
            /* Modal styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 1000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.8);
                justify-content: center;
                align-items: center;
            }
            .modal-content {
                max-width: 90%;
                max-height: 90%;
                border-radius: 5px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            }
            .close-btn {
                position: absolute;
                top: 20px;
                right: 30px;
                color: white;
                font-size: 30px;
                cursor: pointer;
                transition: color 0.3s ease;
            }
            .close-btn:hover {
                color: #ccc;
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
                .modal-content {
                    max-width: 95%;
                    max-height: 95%;
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
                        <li data-section="overview">
                            <a href="overviewadmin">Overview</a>
                        </li>
                        <li data-section="courses">
                        <a href="admintopic">List Topic</a>
                        </li>
                        <li data-section="users">
                            <a href="manageuser">Manage Users</a>
                        </li>
                        <li class="active" data-section="slider">
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
                    <h1>Welcome to Manage Sliders</h1>
                </header>
                <div style="color:green">${message}</div>
                <div style="color:red; text-align:center; padding: 20px;">${error}</div>
                <div class="header-controls">
                    <div class="search-section">
                        <form action="manageslider" method="get">
                            <input type="text" name="query" value="${searchQuery}" placeholder="enter slider title...">
                            <button type="submit" class="search-btn" title="Search"><i class="fas fa-search"></i></button>
                            <a href="manageslider"><button type="button" class="reset-btn" title="Reset"><i class="fas fa-rotate"></i></button></a>
                        </form>
                    </div>
                    <a href="manageslider?action=create"><button class="create-btn" title="Create Slider"><i class="fas fa-plus"></i></button></a>
                </div>
                <c:choose>
                    <c:when test="${empty sliders}">
                        <div style="color:red; text-align:center; padding: 20px;">No records found</div>
                    </c:when>
                    <c:otherwise>
                        <table class="user-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Image</th>
                                    <th>Created At</th>
                                    <th>Updated At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="slider" items="${sliders}">
                                    <tr>
                                        <td><c:out value="${slider.slider_id}" /></td>
                                        <td><c:out value="${slider.title}" /></td>
                                        <td>
                                            <img src="${slider.image_url.startsWith('http') ? slider.image_url : pageContext.request.contextPath.concat(slider.image_url)}?t=<%= System.currentTimeMillis() %>" alt="Slider Image" class="slider-image" onclick="openModal(this.src)" onerror="this.src='${pageContext.request.contextPath}/assets/img/default-slider.png'" />
                                        </td>
                                        <td><fmt:formatDate value="${slider.created_at}" pattern="dd/MM/yyyy" /></td>
                                        <td><fmt:formatDate value="${slider.updated_at}" pattern="dd/MM/yyyy" /></td>
                                        <td>
                                            <a href="manageslider?action=edit&sliderId=${slider.slider_id}" style="text-decoration: none;">
                                                <button class="edit-btn" title="Edit Slider"><i class="fas fa-edit"></i></button>
                                            </a>
                                            <a href="manageslider?action=delete&sliderId=${slider.slider_id}" style="text-decoration: none;" onclick="return confirm('Are you sure you want to delete this slider?')">
                                                <button class="delete-btn" title="Delete Slider"><i class="fas fa-trash"></i></button>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="manageslider?page=${currentPage - 1}&query=${searchQuery}">Previous</a>
                    </c:if>
                    <c:if test="${currentPage <= 1}">
                        <a class="disabled">Previous</a>
                    </c:if>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="manageslider?page=${i}&query=${searchQuery}" <c:if test="${currentPage == i}">class="active"</c:if>>${i}</a>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <a href="manageslider?page=${currentPage + 1}&query=${searchQuery}">Next</a>
                    </c:if>
                    <c:if test="${currentPage >= totalPages}">
                        <a class="disabled">Next</a>
                    </c:if>
                </div>
                <div id="imageModal" class="modal">
                    <span class="close-btn" onclick="closeModal()">&times;</span>
                    <img id="modalImage" class="modal-content">
                </div>
            </main>
        </div>
        <script>
            function openModal(imageSrc) {
                const modal = document.getElementById('imageModal');
                const modalImg = document.getElementById('modalImage');
                modal.style.display = 'flex';
                modalImg.src = imageSrc;
            }

            function closeModal() {
                const modal = document.getElementById('imageModal');
                modal.style.display = 'none';
            }

            window.onclick = function(event) {
                const modal = document.getElementById('imageModal');
                if (event.target === modal) {
                    closeModal();
                }
            }
        </script>
    </body>
</html>