<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Course Detail</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <style>
        .course-detail-card {
            max-width: 700px;
            margin: auto;
            margin-top: 40px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            padding: 30px;
            border-radius: 8px;
        }
        .course-thumbnail {
            max-width: 200px;
            height: auto;
            border-radius: 4px;
            margin-top: 10px;
        }
        .course-label {
            font-weight: bold;
            color: #007bff;
        }
    </style>
</head>
<body class="container">

    <div class="course-detail-card bg-white">
        <h2 class="text-center mb-4">Course Detail</h2>
        <c:if test="${not empty course}">
            <div class="mb-3">
                <span class="course-label">Title:</span> ${course.title}
            </div>
            <div class="mb-3">
                <span class="course-label">Description:</span>
                <p>${course.description}</p>
            </div>
            <div class="mb-3">
                <span class="course-label">Price:</span> $${course.price}
            </div>
            <div class="mb-3">
                <span class="course-label">Created At:</span> ${course.created_at}
            </div>
            <div class="mb-3">
                <span class="course-label">Updated At:</span> ${course.updated_at}
            </div>
            <div class="mb-3">
                <span class="course-label">Thumbnail:</span><br>
                <img src="${course.thumbnail_url}" class="course-thumbnail" alt="Thumbnail" />
            </div>
        </c:if>

        <div class="d-flex justify-content-between mt-4">
            <a href="listCousera" class="btn btn-secondary">Back</a>
            <a href="manageLesson?courseId=${course.course_id}" class="btn btn-outline-primary">
                Manage Lessons
            </a>
        </div>
    </div>

    <!-- Bootstrap JS (optional if needed for modal, etc.) -->
    <script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>
