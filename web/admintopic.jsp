<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Topic Management</title>
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
            justify-content: flex-start;
            align-items: center;
            gap: 20px;
            margin-bottom: 20px;
        }
        .topic-table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border-radius: 5px;
            overflow: hidden;
        }
        .topic-table th, .topic-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .topic-table th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        .topic-table tr:hover {
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
        .no-data {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
        }
        .topic-image-container {
            width: 100px;
            height: 60px;
            display: inline-block;
            overflow: hidden;
            border-radius: 5px;
            vertical-align: middle;
            margin-top: 5px;
        }
        .topic-image {
            max-width: 100px;
            height: auto;
            border-radius: 5px;
            margin-top: 5px;
            cursor: pointer; /* Indicate clickable image */
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
            background-color: rgba(0, 0, 0, 0.7); /* Semi-transparent background */
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            background-color: white;
            border-radius: 5px;
            padding: 20px;
            max-width: 90%;
            max-height: 90%;
            display: flex;
            flex-direction: column;
            align-items: center;
            position: relative;
        }
        .modal-image {
            max-width: 100%;
            max-height: 80vh;
            object-fit: contain; /* Ensure image fits without distortion */
        }
        .close-btn {
            position: absolute;
            top: 10px;
            right: 15px;
            font-size: 24px;
            color: #333;
            cursor: pointer;
            background: none;
            border: none;
        }
        .close-btn:hover {
            color: #3498db;
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
            .topic-image-container {
                width: 80px;
                height: 48px;
            }
            .topic-image {
                max-width: 80px;
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
                    <li data-section="courses" class="active">
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
                <h1>Welcome to Topics List</h1>
            </header>
            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <div class="header-controls">
                <div class="search-section">
                    <form action="admintopic" method="get">
                        <input type="text" name="query" placeholder="Search topics by name..." value="${searchQuery}">
                        <button type="submit" class="search-btn" title="Search"><i class="fas fa-search"></i></button>
                        <a href="admintopic"><button type="button" class="reset-btn" title="Reset"><i class="fas fa-rotate"></i></button></a>
                    </form>
                </div>
            </div>
            <!-- Modal for displaying full-size image -->
            <div id="imageModal" class="modal">
                <div class="modal-content">
                    <button class="close-btn" onclick="closeModal()">&times;</button>
                    <img id="modalImage" class="modal-image" src="" alt="Full-size Thumbnail">
                </div>
            </div>
            <c:choose>
                <c:when test="${empty topics}">
                    <div class="no-data">No topics found</div>
                </c:when>
                <c:otherwise>
                    <table class="topic-table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Thumbnail</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="topic" items="${topics}">
                                <tr>
                                    <td><c:out value="${topic.topic_id}" /></td>
                                    <td><a href="admincourse?topicId=${topic.topic_id}" style="color: #3498db; text-decoration: none;">${topic.name}</a></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty topic.thumbnail_url and topic.thumbnail_url != ''}">
                                                <span class="topic-image-container">
                                                    <img src="${topic.thumbnail_url.startsWith('http') ? topic.thumbnail_url : pageContext.request.contextPath.concat(topic.thumbnail_url)}" 
                                                         alt="Topic Thumbnail" 
                                                         class="topic-image" 
                                                         onclick="openModal(this.src)"
                                                         onerror="this.parentNode.innerHTML='N/A'" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                N/A
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td><c:out value="${topic.description != null ? topic.description : 'N/A'}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="admintopic?page=${currentPage - 1}&query=${searchQuery}">Previous</a>
                </c:if>
                <c:if test="${currentPage <= 1}">
                    <a class="disabled">Previous</a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="admintopic?page=${i}&query=${searchQuery}" <c:if test="${currentPage == i}">class="active"</c:if>>${i}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="admintopic?page=${currentPage + 1}&query=${searchQuery}">Next</a>
                </c:if>
                <c:if test="${currentPage >= totalPages}">
                    <a class="disabled">Next</a>
                </c:if>
            </div>
        </main>
    </div>
    <script>
        function openModal(src) {
            var modal = document.getElementById('imageModal');
            var modalImg = document.getElementById('modalImage');
            modal.style.display = 'flex';
            modalImg.src = src;
        }

        function closeModal() {
            var modal = document.getElementById('imageModal');
            modal.style.display = 'none';
        }

        // Close modal when clicking outside the image
        window.onclick = function(event) {
            var modal = document.getElementById('imageModal');
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        }
    </script>
</body>
</html>