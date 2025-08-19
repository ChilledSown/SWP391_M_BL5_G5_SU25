<%-- 
    Document   : quiz_detail
    Created on : Aug 19, 2025, 12:53:15 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Quiz Detail</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    </head>
    <body class="container mt-5">
        <h3>Quiz Detail</h3>

        <table class="table table-bordered">
            <tr>
                <th>Question</th>
                <td>${quiz.question}</td>
            </tr>
            <tr>
                <th>Answer Options</th>
                <td>
            <c:forEach var="option" items="${quiz.answerOptions.split(';')}">
                <div>${option}</div>
            </c:forEach>
        </td>
    </tr>
    <tr>
        <th>Correct Answer</th>
        <td>${quiz.correctAnswer}</td>
    </tr>
    <tr>
        <th>Explanation</th>
        <td>${quiz.explanation}</td>
    </tr>
    <tr>
        <th>Created At</th>
        <td>${quiz.createdAt}</td>
    </tr>
    <tr>
        <th>Updated At</th>
        <td>${quiz.updatedAt}</td>
    </tr>
</table>

<a href="manageQuiz?lessonId=${lessonId}" class="btn btn-secondary">Back</a>
</body>
</html>
