<%--
    Document : manageQuizInstructor
    Created on : Aug 15, 2025, 4:21:29 PM
    Author : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Manage Quizzes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        .btn-icon {
            padding: 6px 10px;
        }
        .btn-icon i {
            font-size: 16px;
        }
        .alert {
            margin-bottom: 20px;
        }
    </style>
</head>
<body class="container mt-5">
    <h2>Quizzes for Lesson</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <!-- File Import Form -->
    <form method="post" action="createQuizSeller" enctype="multipart/form-data" class="mb-3">
        <input type="hidden" name="lessonId" value="${lessonId}" />
        <input type="hidden" name="courseId" value="${courseId}" />
        <div class="row g-2">
            <div class="col-md-4">
                <label for="importFile" class="form-label">Import Quizzes from File (.txt)</label>
                <input type="file" class="form-control" id="importFile" name="importFile" accept=".txt" />
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100 mt-4">Import</button>
            </div>
        </div>
    </form>
    <!-- Search Form -->
    <form method="get" action="manageQuizInstructor" class="mb-3">
        <a href="createQuizSeller?lessonId=${lessonId}&courseId=${courseId}" class="btn btn-success mb-3">
            <i class="fa-solid fa-plus"></i> Add New Quiz
        </a>
        <input type="hidden" name="lessonId" value="${lessonId}" />
        <input type="hidden" name="courseId" value="${courseId}" />
        <div class="row g-2">
            <div class="col-md-4">
                <input type="text" name="question" class="form-control" placeholder="Search by question..." value="${param.question}" />
            </div>

            From
            <div class="col-md-3">
                <input type="date" name="startDate" class="form-control" placeholder="From date" value="${param.startDate}" />
            </div>
            To 
            <div class="col-md-3">
                <input type="date" name="endDate" class="form-control" placeholder="To date" value="${param.endDate}" />
            </div>
            <div class="col-md-1">
                <button type="submit" class="btn btn-primary w-100">Search</button>
            </div>
            <div class="col-md-1">
                <a href="manageQuizInstructor?lessonId=${lessonId}&courseId=${courseId}" class="btn btn-secondary w-100">Reset</a>
            </div>
        </div>
    </form>
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
                        <a href="editQuizSeller?quizId=${quiz.quizId}&lessonId=${lessonId}&courseId=${courseId}"
                           class="btn btn-sm btn-warning btn-icon" title="Edit">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </a>
                        <a href="deleteQuizSeller?quizId=${quiz.quizId}&lessonId=${lessonId}&courseId=${courseId}"
                           class="btn btn-sm btn-danger btn-icon"
                           onclick="return confirm('Are you sure you want to delete this quiz?');"
                           title="Delete">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                        <a href="quizDetailSeller?quizId=${quiz.quizId}&lessonId=${lessonId}&courseId=${courseId}"
                           class="btn btn-sm btn-info btn-icon" title="Detail">
                            <i class="fa-solid fa-file-lines"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty quizzes}">
                <tr>
                    <td colspan="4" class="text-center">No quizzes found.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
    <jsp:include page="pagination.jsp">
        <jsp:param name="currentPage" value="${currentPage}" />
        <jsp:param name="totalPages" value="${totalPages}" />
        <jsp:param name="baseUrl" value="${baseUrl}" />
    </jsp:include>
    <a href="manageLessonInstructor?courseId=${courseId}" class="btn btn-secondary mb-3">
        <i class="fa-solid fa-arrow-left"></i> Back to Lessons
    </a>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>