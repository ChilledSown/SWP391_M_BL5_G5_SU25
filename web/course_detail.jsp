<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>Course Detail</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-icons/1.10.5/font/bootstrap-icons.min.css">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Segoe UI', sans-serif;
            }
            .course-detail-card {
                max-width: 800px;
                margin: 60px auto;
                background-color: #fff;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                padding: 40px;
                border-radius: 10px;
            }
            .course-thumbnail {
                max-width: 220px;
                height: auto;
                border-radius: 6px;
                border: 1px solid #ccc;
            }
            .course-label {
                font-weight: 600;
                color: #495057;
                width: 150px;
                vertical-align: top;
            }
            .table td {
                padding: 10px;
                vertical-align: middle;
            }
        </style>
    </head>
    <body>

        <div class="course-detail-card">
            <h2 class="text-center mb-4 text-primary">Course Detail</h2>

            <c:if test="${not empty course}">
                <table class="table table-borderless">
                    <tr>
                        <td class="course-label">Title:</td>
                        <td>${course.title}</td>
                    </tr>
                    <tr>
                        <td class="course-label">Description:</td>
                        <td>${course.description}</td>
                    </tr>
                    <tr>
                        <td class="course-label">Price:</td>
                        <td>$${course.price}</td>
                    </tr>
                    <tr>
                        <td class="course-label">Created At:</td>
                        <td><fmt:formatDate value="${course.created_at}" pattern="yyyy-MM-dd"/></td>
                    </tr>
                    <tr>
                        <td class="course-label">Updated At:</td>
                        <td><fmt:formatDate value="${course.updated_at}" pattern="yyyy-MM-dd"/></td>
                    </tr>

                    <tr>
                        <td class="course-label">Thumbnail:</td>
                        <td><img src="${course.thumbnail_url}" class="course-thumbnail" alt="Thumbnail" /></td>
                    </tr>
                </table>
            </c:if>

            <div class="d-flex justify-content-between mt-4">
                <a href="listCourses" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left"></i> Back
                </a>
                <a href="manageLessonInstructor?courseId=${course.course_id}" class="btn btn-primary">
                    <i class="bi bi-journal-code"></i> Manage Lessons
                </a>
            </div>
        </div>

        <script src="assets/js/bootstrap.bundle.min.js"></script>
    </body>
</html>