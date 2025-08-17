<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Topic</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
        }
        .form-container {
            max-width: 600px;
            margin: 30px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-container h2 {
            text-align: center;
            color: #2c3e50;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }
        .form-group textarea {
            resize: vertical;
        }
        .form-group input:invalid {
            border-color: #e74c3c;
        }
        .form-group button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            color: white;
            transition: background-color 0.3s ease;
        }
        .form-group .submit-btn {
            background-color: #3498db;
        }
        .form-group .submit-btn:hover {
            background-color: #2980b9;
        }
        .form-group .cancel-btn {
            background-color: #7f8c8d;
            margin-left: 10px;
        }
        .form-group .cancel-btn:hover {
            background-color: #6c7a89;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Topic</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <c:if test="${empty topic}">
            <div class="error">Topic not found</div>
            <div class="form-group">
                <a href="managetopic"><button type="button" class="cancel-btn">Back to Topics</button></a>
            </div>
        </c:if>
        <c:if test="${not empty topic}">
            <form action="managetopic" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="topicId" value="${topic.topic_id}">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" value="${topic.name}" required>
                </div>
                <div class="form-group">
                    <label for="thumbnailUrl">Thumbnail URL</label>
                    <input type="text" id="thumbnailUrl" name="thumbnailUrl" value="${topic.thumbnail_url}">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="5">${topic.description}</textarea>
                </div>
                <div class="form-group">
                    <button type="submit" class="submit-btn">Update Topic</button>
                    <a href="managetopic"><button type="button" class="cancel-btn">Cancel</button></a>
                </div>
            </form>
        </c:if>
    </div>
</body>
</html>