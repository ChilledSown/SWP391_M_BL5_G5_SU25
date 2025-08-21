<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lesson Detail</title>
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
        <h1>Lesson Detail</h1>
        <div class="detail-item">
            <strong>ID:</strong> <c:out value="${lesson.lessonId}" />
        </div>
        <div class="detail-item">
            <strong>Title:</strong> <c:out value="${lesson.title}" />
        </div>
        <div class="detail-item">
            <strong>Video URL:</strong> <c:out value="${lesson.videoUrl != null ? lesson.videoUrl : 'N/A'}" />
        </div>
        <div class="detail-item">
            <strong>Content:</strong> <c:out value="${lesson.content != null ? lesson.content : 'N/A'}" />
        </div>
        <div class="detail-item">
            <strong>Created At:</strong> <fmt:formatDate value="${lesson.createdAt}" pattern="dd/MM/yyyy" />
        </div>
        <div class="detail-item">
            <strong>Updated At:</strong> <fmt:formatDate value="${lesson.updatedAt}" pattern="dd/MM/yyyy" />
        </div>
        <div class="detail-item">
            <strong>Course ID:</strong> <c:out value="${lesson.courseId}" />
        </div>
        <a href="adminlesson?courseId=${courseId}&topicId=${topicId}" class="back-link">Back to Lessons</a>
    </div>
</body>
</html>