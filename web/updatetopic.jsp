<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Topic</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <style>
        body { font-family: 'Roboto', sans-serif; margin: 20px; }
        .form-container { max-width: 600px; margin: 0 auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .form-container h2 { text-align: center; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; }
        .form-group input, .form-group textarea { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .form-group button { padding: 10px 20px; background-color: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .form-group button:hover { background-color: #2980b9; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Update Topic</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="managecourse" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="topicId" value="${topic.topic_id}">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" value="${topic.name}" required>
            </div>
            <div class="form-group">
                <label for="thumbnailUrl">Thumbnail URL</label>
                <input type="text" id="thumbnailUrl" name="thumbnailUrl" value="${topic.thumbnail_Url}">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="5">${topic.description}</textarea>
            </div>
            <div class="form-group">
                <button type="submit">Update Topic</button>
                <a href="managecourse"><button type="button">Cancel</button></a>
            </div>
        </form>
    </div>
</body>
</html>
