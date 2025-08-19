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
        .view-btn {
            background-color: #3498db;
        }
        .view-btn:hover {
            background-color: #2980b9;
        }
        .search-bar {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
            align-items: center;
        }
        .search-bar input[type="text"] {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            width: 300px;
        }
        .search-bar button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            color: white;
        }
        .search-bar button[type="submit"] {
            background-color: #3498db;
        }
        .search-bar button[type="submit"]:hover {
            background-color: #2980b9;
        }
        .search-bar button[type="button"] {
            background-color: #7f8c8d;
        }
        .search-bar button[type="button"]:hover {
            background-color: #6c7a89;
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
        .no-data {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
        }
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 10px;
        }
        .pagination a {
            padding: 8px 12px;
            text-decoration: none;
            border: 1px solid #ddd;
            border-radius: 4px;
            color: #333;
        }
        .pagination a.active {
            background-color: #3498db;
            color: white;
            border-color: #3498db;
        }
        .pagination a.disabled {
            color: #ccc;
            cursor: not-allowed;
        }
        .pagination a:hover:not(.disabled) {
            background-color: #f5f5f5;
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
                    <li data-section="overview">
                        <a href="overview">Overview</a>
                    </li>
                    <li class="active" data-section="courses">
                        <a href="managetopic">Manage Topic</a>
                    </li>
                    <li data-section="users">
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
                <h1>Welcome, <c:out value="${sessionScope.user.firstName} 
                ${sessionScope.user.middleName != null ? sessionScope.user.middleName : ''} ${sessionScope.user.lastName}" />!</h1>
            </header>
            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <div class="search-bar">
                <form action="managetopic" method="get">
                    <input type="text" name="query" placeholder="Search topics by name..." value="${searchQuery}">
                    <button type="submit">Search</button>
                </form>
                <a href="managetopic"><button type="button">Reset</button></a>
            </div>
            <table class="topic-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Thumbnail URL</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty topics}">
                            <c:forEach var="topic" items="${topics}">
                                <tr>
                                    <td><c:out value="${topic.topic_id}" /></td>
                                    <td><c:out value="${topic.name}" /></td>
                                    <td><c:out value="${topic.thumbnail_url}" /></td>
                                    <td><c:out value="${topic.description != null ? topic.description : 'N/A'}" /></td>
                                    <td>
                                        <form action="managecourse" method="get" style="display:inline;">
                                            <input type="hidden" name="topicId" value="${topic.topic_id}">
                                            <button type="submit" class="view-btn">View</button>
                                        </form>
                                        <form action="managetopic" method="get" style="display:inline;">
                                            <input type="hidden" name="action" value="edit">
                                            <input type="hidden" name="topicId" value="${topic.topic_id}">
                                            <button type="submit" class="edit-btn">Edit</button>
                                        </form>
                                        <form action="managetopic" method="post" style="display:inline;">
                                            <input type="hidden" name="action" value="delete">
                                            <input type="hidden" name="topicId" value="${topic.topic_id}">
                                            <button type="submit" class="delete-btn" onclick="return confirm('Are you sure you want to delete this topic?')">Delete</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="5" class="no-data">No topics found</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="managetopic?page=${currentPage - 1}&query=${searchQuery}">Previous</a>
                </c:if>
                <c:if test="${currentPage <= 1}">
                    <a class="disabled">Previous</a>
                </c:if>
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <a href="managetopic?page=${i}&query=${searchQuery}" <c:if test="${currentPage == i}">class="active"</c:if>>${i}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="managetopic?page=${currentPage + 1}&query=${searchQuery}">Next</a>
                </c:if>
                <c:if test="${currentPage >= totalPages}">
                    <a class="disabled">Next</a>
                </c:if>
            </div>
        </main>
    </div>     
</body>
</html>