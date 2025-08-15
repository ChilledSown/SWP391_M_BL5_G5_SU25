<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Topic Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
        }
        .container {
            max-width: 1000px;
            margin: 30px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        h1, h2 {
            color: #2c3e50;
        }
        .topic-details {
            margin-bottom: 30px;
        }
        .topic-details img {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
            margin-bottom: 15px;
        }
        .topic-details p {
            margin: 10px 0;
        }
        .course-table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border-radius: 5px;
            overflow: hidden;
        }
        .course-table th, .course-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .course-table th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        .course-table tr:hover {
            background-color: #f5f5f5;
        }
        .course-table img {
            max-width: 100px;
            height: auto;
            border-radius: 4px;
        }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #7f8c8d;
        }
        .back-btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
        }
        .back-btn:hover {
            background-color: #2980b9;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Topic Details</h1>
        <c:if test="${empty topic}">
            <div class="error">Topic not found</div>
        </c:if>
        <c:if test="${not empty topic}">
            <div class="topic-details">
                <h2><c:out value="${topic.name}" /></h2>
                <c:if test="${not empty topic.thumbnail_Url}">
                    <img src="<c:out value='${topic.thumbnail_Url}' />" alt="Topic Thumbnail">
                </c:if>
                <p><strong>ID:</strong> <c:out value="${topic.topic_id}" /></p>
                <p><strong>Description:</strong> <c:out value="${topic.description}" /></p>
            </div>
            <h2>Courses in this Topic</h2>
            <table class="course-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Thumbnail</th>
                        <th>Created At</th>
                        <th>Updated At</th>
                        <th>Average Rating</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty courses}">
                            <c:forEach var="course" items="${courses}">
                                <tr>
                                    <td><c:out value="${course.course_id}" /></td>
                                    <td><c:out value="${course.title}" /></td>
                                    <td><c:out value="${course.description}" /></td>
                                    <td><fmt:formatNumber value="${course.price}" type="currency" currencySymbol="$" /></td>
                                    <td>
                                        <c:if test="${not empty course.thumbnail_url}">
                                            <img src="<c:out value='${course.thumbnail_url}' />" alt="Course Thumbnail">
                                        </c:if>
                                    </td>
                                    <td><fmt:formatDate value="${course.created_at}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                                    <td><fmt:formatDate value="${course.updated_at}" pattern="dd-MM-yyyy HH:mm:ss" /></td>
                                    <td><c:out value="${course.averageRating}" default="N/A" /></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="no-data">No courses found for this topic</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </c:if>
        <a href="managecourse" class="back-btn">Back to Manage Courses</a>
    </div>
</body>
</html>
