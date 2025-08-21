<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Detail</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
            padding: 30px;
        }
        h1 {
            color: #3498db;
        }
        .detail-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            max-width: 600px;
            margin: 0 auto;
        }
        .detail-item {
            margin-bottom: 15px;
        }
        .detail-item strong {
            display: inline-block;
            width: 150px;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 8px 16px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .back-link:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="detail-container">
        <h1>Course Detail</h1>
        <div class="detail-item">
            <strong>ID:</strong> <c:out value="${course.course_id}" />
        </div>
        <div class="detail-item">
            <strong>Title:</strong> <c:out value="${course.title}" />
        </div>
        <div class="detail-item">
            <strong>Description:</strong> <c:out value="${course.description != null ? course.description : 'N/A'}" />
        </div>
        <div class="detail-item">
            <strong>Price:</strong> <c:out value="${course.price}" />
        </div>
        <div class="detail-item">
            <strong>Thumbnail URL:</strong> <c:out value="${course.thumbnail_url != null ? course.thumbnail_url : 'N/A'}" />
        </div>
        <div class="detail-item">
            <strong>Created At:</strong> <fmt:formatDate value="${course.created_at}" pattern="dd/MM/yyyy" />
        </div>
        <div class="detail-item">
            <strong>Updated At:</strong> <fmt:formatDate value="${course.updated_at}" pattern="dd/MM/yyyy" />
        </div>
        <div class="detail-item">
            <strong>Topic ID:</strong> <c:out value="${course.topic_id}" />
        </div>
        <div class="detail-item">
            <strong>Average Rating:</strong> <fmt:formatNumber value="${course.averageRating}" maxFractionDigits="2" />
        </div>
        <a href="admincourse?topicId=${topicId}" class="back-link">Back to Courses</a>
    </div>
</body>
</html>