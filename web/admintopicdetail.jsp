<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Topic Detail</title>
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
        .thumbnail {
            max-width: 100%;
            height: auto;
            border-radius: 4px;
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
        <h1>Topic Detail</h1>
        <div class="detail-item">
            <strong>ID:</strong> <c:out value="${topic.topic_id}" />
        </div>
        <div class="detail-item">
            <strong>Name:</strong> <c:out value="${topic.name}" />
        </div>
        <div class="detail-item">
            <strong>Thumbnail:</strong>
            <c:choose>
                <c:when test="${not empty topic.thumbnail_url}">
                    <img src="${topic.thumbnail_url}" alt="Thumbnail" class="thumbnail">
                </c:when>
                <c:otherwise>
                    N/A
                </c:otherwise>
            </c:choose>
        </div>
        <div class="detail-item">
            <strong>Description:</strong> <c:out value="${topic.description != null ? topic.description : 'N/A'}" />
        </div>
        <a href="admintopic" class="back-link">Back to Topics</a>
    </div>
</body>
</html>