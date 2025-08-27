<%-- 
    Document : quiz_detail
    Created on : Aug 19, 2025, 12:53:15 AM
    Author : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                <c:forEach var="option" items="${quiz.answerOptions.split(';')}" varStatus="status">
                    <c:if test="${not empty option.trim()}">
                        <div>${status.index == 0 ? 'A' : status.index == 1 ? 'B' : status.index == 2 ? 'C' : 'D'}. ${option}</div>
                    </c:if>
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
            <td><fmt:formatDate value="${quiz.createdAt}" pattern="yyyy-MM-dd"/></td>
        </tr>
        <tr>
            <th>Updated At</th>
            <td><fmt:formatDate value="${quiz.updatedAt}" pattern="yyyy-MM-dd"/></td>
        </tr>
    </table>
    <a href="manageQuizInstructor?lessonId=${lessonId}&courseId=${courseId}" class="btn btn-secondary">Back</a>
</body>
</html>