<%-- 
    Document   : course_detail
    Created on : Aug 15, 2025, 4:02:20 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Course Detail</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    </head>
    <body class="container mt-5">
        <h2>Course Detail</h2>
        <hr>

        <c:if test="${not empty course}">
            <p><strong>Title:</strong> ${course.title}</p>
            <p><strong>Description:</strong> ${course.description}</p>
            <p><strong>Price:</strong> $${course.price}</p>
            <p><strong>Created At:</strong> ${course.created_at}</p>
            <p><strong>Updated At:</strong> ${course.updated_at}</p>
            <p><strong>Thumbnail:</strong> <br><img src="${course.thumbnail_url}" width="300" /></p>

        </c:if>

        <a href="listCousera" class="btn btn-secondary mt-3">Back</a>

        <div class="modal-footer">
            <a href="manageLesson?courseId=${course.course_id}" class="btn btn-outline-primary">
                Manage Lessons
            </a>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>

    </body>

</html>
=======

