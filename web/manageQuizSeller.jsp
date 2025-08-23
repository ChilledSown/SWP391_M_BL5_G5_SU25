<%-- 
    Document   : manageQuiz
    Created on : Aug 15, 2025, 4:21:29 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Manage Quizzes</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css" />

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .btn-icon {
                padding: 6px 10px;
            }
            .btn-icon i {
                font-size: 16px;
            }
        </style>
    </head>
    <body class="container mt-5">
        <h2>Quizzes for Lesson ID: ${lessonId}</h2>

        <a href="quiz_form.jsp?lessonId=${lessonId}" class="btn btn-success mb-3">
            <i class="fa-solid fa-plus"></i> Add New Quiz
        </a>

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
                        <td><fmt:formatDate value="${quiz.createdAt}" pattern="yyyy-MM-dd"/></td>

                        <td>

                            <a href="editQuizSeller?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-warning btn-icon" title="Edit">
                                <i class="fa-solid fa-pen-to-square"></i>
                            </a>


                            <a href="deleteQuizSeller?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-danger btn-icon"
                               onclick="return confirm('Are you sure you want to delete this quiz?');"
                               title="Delete">
                                <i class="fa-solid fa-trash"></i>
                            </a>


                            <a href="quizDetailSeller?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-info btn-icon" title="Detail">
                                <i class="fa-solid fa-file-lines"></i>
                            </a>

                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a href="manageLessonSeller?courseId=${param.courseId}" class="btn btn-secondary">
            <i class="fa-solid fa-arrow-left"></i> Back to Lessons
        </a>

    </body>
</html>
