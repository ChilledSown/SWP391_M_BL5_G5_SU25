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
        <h2>Quizzes for Lesson ID: ${lessonId}</h2>
        <a href="addQuizForm.jsp?lessonId=${lessonId}" class="btn btn-success mb-3">Add New Quiz</a>

        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Question</th>
                    <th>Correct Answer</th>
                    <th>Created At</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="quiz" items="${quizzes}">
                    <tr>
                        <td>${quiz.question}</td>
                        <td>${quiz.correctAnswer}</td>
                        <td>${quiz.createdAt}</td>
                        <td>
                            <a href="editQuiz?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-warning">Edit</a>

                            <a href="deleteQuiz?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-danger">Delete</a>
                                 <a href="quizDetail?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                       class="btn btn-sm btn-info">Detail</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="manageLesson?courseId=${param.courseId}" class="btn btn-secondary">Back to Lessons</a>

    </body>
</html>
