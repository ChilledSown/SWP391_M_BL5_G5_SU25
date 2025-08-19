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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    </head>
    <body class="container mt-5">
        <h2>Quizzes for Lesson ID: ${lessonId}</h2>

     
        <a href="quiz_form.jsp?lessonId=${lessonId}" class="btn btn-success mb-3">
            Add New Quiz
        </a>

        <table class="table table-bordered">
            <thead class="thead-dark">
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
                            <!-- Edit -->
                            <a href="editQuiz?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-warning" title="Edit">
                                <i class="fas fa-edit"></i>
                            </a>

                            <!-- Delete -->
                            <a href="deleteQuiz?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-danger" title="Delete"
                               onclick="return confirm('Are you sure you want to delete this quiz?');">
                                <i class="fas fa-trash-alt"></i>
                            </a>

                            <!-- Detail -->
                            <a href="quizDetail?quizId=${quiz.quizId}&lessonId=${lessonId}" 
                               class="btn btn-sm btn-info" title="Detail">
                                <i class="fas fa-eye"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Back button -->
        <a href="manageLesson?courseId=${param.courseId}" class="btn btn-secondary">
            <i class="fas fa-arrow-left"></i> Back to Lessons
        </a>
    </body>
</html>
