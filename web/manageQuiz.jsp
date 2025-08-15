<%-- 
    Document   : manageQuiz
    Created on : Aug 15, 2025, 4:21:29 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Quizzes</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
</head>
<body class="container mt-5">
    <h2>Quizzes for Course: ${course.title}</h2>
    <a href="addQuizForm.jsp?courseId=${course.course_id}" class="btn btn-success mb-3">Add New Quiz</a>

    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Lesson</th>
                <th>Question</th>
                <th>Correct Answer</th>
                <th>Created At</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="quiz" items="${quizzes}">
            <tr>
                <td>${quiz.lesson_title}</td>
                <td>${quiz.question}</td>
                <td>${quiz.correct_answer}</td>
                <td>${quiz.created_at}</td>
                <td>
                    <a href="editQuiz?quizId=${quiz.quiz_id}" class="btn btn-sm btn-warning">Edit</a>
                    <a href="deleteQuiz?quizId=${quiz.quiz_id}" class="btn btn-sm btn-danger"
                       onclick="return confirm('Delete this quiz?')">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="courseDetail?courseId=${course.course_id}" class="btn btn-secondary">Back to Course</a>
</body>
</html>
