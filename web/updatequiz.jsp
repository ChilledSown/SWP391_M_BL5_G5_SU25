<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty quiz ? 'Add Quiz' : 'Update Quiz'}</title>
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
        .form-group input:invalid, .form-group textarea:invalid {
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
        <h2>${empty quiz ? 'Add Quiz' : 'Update Quiz'}</h2>
        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>
        <form action="managequiz" method="post">
            <input type="hidden" name="action" value="${empty quiz ? 'add' : 'update'}">
            <c:if test="${not empty quiz}">
                <input type="hidden" name="quizId" value="${quiz.quiz_id}">
            </c:if>
            <input type="hidden" name="topicId" value="${topicId}">
            <input type="hidden" name="courseId" value="${courseId}">
            <input type="hidden" name="lessonId" value="${lessonId}">
            <div class="form-group">
                <label for="question">Question</label>
                <textarea id="question" name="question" rows="3" required>${quiz.question}</textarea>
            </div>
            <div class="form-group">
                <label for="answerOptions">Answer Options</label>
                <textarea id="answerOptions" name="answerOptions" rows="5">${quiz.answer_options}</textarea>
            </div>
            <div class="form-group">
                <label for="correctAnswer">Correct Answer</label>
                <input type="text" id="correctAnswer" name="correctAnswer" value="${quiz.correct_answer}" required>
            </div>
            <div class="form-group">
                <button type="submit" class="submit-btn">${empty quiz ? 'Add Quiz' : 'Update Quiz'}</button>
                <a href="managequiz?topicId=${topicId}&courseId=${courseId}&lessonId=${lessonId}"><button type="button" class="cancel-btn">Cancel</button></a>
            </div>
        </form>
    </div>
</body>
</html>