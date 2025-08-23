<%--
    Document : manageLessonSeller
    Created on : Aug 15, 2025, 4:21:01 PM
    Author : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <title>Manage Lessons</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .alert {
                margin-bottom: 20px;
            }
            .btn-icon {
                padding: 6px 10px;
            }
            .btn-icon i {
                font-size: 16px;
            }
        </style>
    </head>
    <body class="container mt-5">
        <h2>Manage Lessons for Course ID: ${courseId}</h2>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <a href="courseDetail?courseId=${courseId}" class="btn btn-secondary mb-3">Back to Course Detail</a>

        <!-- Search form -->
        <form method="get" action="manageLessonSeller" class="mb-3">
            <input type="hidden" name="courseId" value="${courseId}" />
            <div class="row g-2">
                <div class="col-md-4">
                    <input type="text" name="title" class="form-control" placeholder="Search by title..." value="${param.title}" />
                </div>
                <div class="col-md-3">
                    <input type="date" name="createdDate" class="form-control" value="${param.createdDate}" />
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
                <div class="col-md-2">
                    <a href="manageLessonSeller?courseId=${courseId}" class="btn btn-secondary w-100">Reset</a>
                </div>
            </div>
        </form>

        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Title</th>
                    <th>Video URL</th>
                    <th>Content</th>
                    <th>Created</th>
                    <th>Updated</th>
                    <th>Delete</th>
                    <th>Edit</th>
                    <th>Quiz</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="lesson" items="${lessons}">
                    <tr>
                        <td>${lesson.title}</td>
                        <td><a href="${lesson.videoUrl}" target="_blank">Video</a></td>
                        <td>${lesson.content}</td>
                        <td><fmt:formatDate value="${lesson.createdAt}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${lesson.updatedAt}" pattern="yyyy-MM-dd"/></td>



                        <td>
                            <form action="deleteLesson" method="post"
                                  onsubmit="return confirm('Are you sure you want to delete this lesson?');">
                                <input type="hidden" name="lessonId" value="${lesson.lessonId}" />
                                <input type="hidden" name="courseId" value="${courseId}" />
                                <button type="submit" class="btn btn-danger btn-sm btn-icon" title="Delete">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </form>
                        </td>


                        <td>
                            <button class="btn btn-warning btn-sm btn-icon"
                                    onclick="openEditLesson('${lesson.lessonId}', '${courseId}')"
                                    title="Edit">
                                <i class="fa-solid fa-pen-to-square"></i>
                            </button>
                        </td>


                        <td>
                            <a href="manageQuizSeller?lessonId=${lesson.lessonId}"
                               class="btn btn-info btn-sm btn-icon" title="Manage Quiz">
                                <i class="fa-solid fa-circle-question"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>


        <jsp:include page="pagination.jsp">
            <jsp:param name="currentPage" value="${currentPage}" />
            <jsp:param name="totalPages" value="${totalPages}" />
            <jsp:param name="baseUrl" value="${baseUrl}" />
        </jsp:include>


        <button class="btn btn-success" onclick="openLessonForm('${param.courseId}')">
            <i class="fa-solid fa-plus"></i> Add Lesson
        </button>

        <!-- Modal -->
        <div class="modal fade" id="lessonFormModal" tabindex="-1" aria-labelledby="lessonFormModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="lessonFormModalLabel">Add Lesson</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body p-0" style="height: 500px;">
                        <iframe id="lessonFormFrame" src="" frameborder="0" style="width:100%; height:100%;"></iframe>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            function openLessonForm(courseId) {
                var url = 'lesson_form.jsp?courseId=' + courseId;
                document.getElementById('lessonFormFrame').src = url;
                document.getElementById('lessonFormModalLabel').innerText = 'Add Lesson';
                var modal = new bootstrap.Modal(document.getElementById('lessonFormModal'));
                modal.show();
            }
            function openEditLesson(lessonId, courseId) {
                var url = 'editLesson?lessonId=' + lessonId + '&courseId=' + courseId;
                document.getElementById('lessonFormFrame').src = url;
                document.getElementById('lessonFormModalLabel').innerText = 'Edit Lesson';
                var modal = new bootstrap.Modal(document.getElementById('lessonFormModal'));
                modal.show();
            }
        </script>
    </body>
</html>
