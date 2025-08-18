<%--
    Document : lesson_form
    Created on : Aug 15, 2025, 5:01:28 PM
    Author : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>${param.lessonId != null ? "Edit" : "Add"} Lesson</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    </head>
    <body class="p-4">
        <h3>${param.lessonId != null ? "Edit" : "Add"} Lesson</h3>
        <form id="lessonForm" action="${param.lessonId != null ? 'editLesson' : 'addLesson'}" method="post" target="hidden_iframe">
            <input type="hidden" name="courseId" value="${param.courseId}" />
            <c:if test="${param.lessonId != null}">
                <input type="hidden" name="lessonId" value="${param.lessonId}" />
            </c:if>
            <div class="form-group">
                <label for="title">Lesson Title</label>
                <input type="text" name="title" class="form-control" required
                       value="${lesson != null ? lesson.title : ''}" />
            </div>
            <div class="form-group">
                <label for="videoUrl">Video URL</label>
                <input type="text" name="videoUrl" class="form-control"
                       value="${lesson != null ? lesson.videoUrl : ''}" />
            </div>
            <div class="form-group">
                <label for="content">Content</label>
                <textarea name="content" class="form-control" rows="4">${lesson != null ? lesson.content : ''}</textarea>
            </div>
            <button type="submit" class="btn btn-primary mt-2" onclick="onSubmit()">Save</button>
        </form>
        <!-- iframe ẩn để tránh reload toàn trang -->
        <iframe name="hidden_iframe" style="display:none;" onload="onSubmitted()"></iframe>
        <script>
            var submitted = false;
            function onSubmit() {
                submitted = true;
            }
            function onSubmitted() {
                if (submitted) {
                    window.parent.location.reload();
                }
            }
        </script>
    </body>
</html>