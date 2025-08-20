<%--
    Document : lesson_form
    Created on : Aug 15, 2025, 5:01:28 PM
    Author : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${lesson != null ? "Edit Lesson" : "Add Lesson"}</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <style>
        #videoPreviewContainer {
            position: relative;
            display: inline-block;
            margin-top: 10px;
        }
        #removePreviewBtn {
            position: absolute;
            top: -10px;
            right: -10px;
            background: red;
            color: white;
            border: none;
            border-radius: 50%;
            width: 25px;
            height: 25px;
            cursor: pointer;
        }
        .alert {
            margin-bottom: 20px;
        }
    </style>
</head>
<body class="p-4">
    <h3>${lesson != null ? "Edit Lesson" : "Add Lesson"}</h3>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form id="lessonForm" action="${lesson != null ? 'editLesson' : 'addLesson'}" method="post" target="hidden_iframe" enctype="multipart/form-data">
        <input type="hidden" name="courseId" value="${courseId != null ? courseId : param.courseId}" />
        <c:if test="${lesson != null}">
            <input type="hidden" name="lessonId" value="${lesson.lessonId}" />
        </c:if>
        <div class="form-group mb-3">
            <label for="title">Lesson Title</label>
            <input type="text" name="title" class="form-control" value="${lesson != null ? lesson.title : requestScope.title}" />
        </div>
        <div class="form-group mb-3">
            <label for="videoUrl">Video URL</label>
            <input type="text" name="videoUrl" class="form-control" value="${lesson != null ? lesson.videoUrl : requestScope.videoUrl}" placeholder="https://example.com/video" onchange="previewVideoUrl(this.value)" />
        </div>
        <div class="form-group mb-3">
            <label for="videoFile">Select video from the device</label>
            <input type="file" name="videoFile" class="form-control" accept="video/mp4,video/webm,video/ogg" onchange="previewVideoFile(this)" />
        </div>
        <div id="videoPreviewContainer" style="display:${(lesson != null && lesson.videoUrl != null && !lesson.videoUrl.isEmpty()) || (requestScope.videoUrl != null && !requestScope.videoUrl.isEmpty()) ? 'inline-block' : 'none'}">
            <button type="button" id="removePreviewBtn" onclick="removeVideoPreview()">&times;</button>
            <video id="videoPreview" width="320" height="180" controls src="${lesson != null ? lesson.videoUrl : requestScope.videoUrl}"></video>
        </div>
        <div class="form-group mb-3">
            <label for="content">Content (optional)</label>
            <textarea name="content" class="form-control" rows="4">${lesson != null ? lesson.content : requestScope.content}</textarea>
        </div>
        <button type="submit" class="btn btn-primary mt-2" onclick="onSubmit()">Save</button>
        <button type="button" class="btn btn-secondary mt-2" onclick="window.parent.document.getElementById('lessonFormModal').querySelector('.btn-close').click();">Cancel</button>
    </form>
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
        function previewVideoUrl(url) {
            if (url) {
                document.getElementById('videoPreview').src = url;
                document.getElementById('videoPreviewContainer').style.display = 'inline-block';
            }
        }
        function previewVideoFile(input) {
            const file = input.files[0];
            if (file) {
                const url = URL.createObjectURL(file);
                document.getElementById('videoPreview').src = url;
                document.getElementById('videoPreviewContainer').style.display = 'inline-block';
            }
        }
        function removeVideoPreview() {
            document.getElementById('videoPreview').src = '';
            document.getElementById('videoPreviewContainer').style.display = 'none';
            document.querySelector('input[name="videoUrl"]').value = '';
            document.querySelector('input[name="videoFile"]').value = '';
        }
    </script>
</body>
</html>