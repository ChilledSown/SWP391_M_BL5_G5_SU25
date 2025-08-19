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
        </style>
    </head>
    <body class="p-4">
        <h3>${param.lessonId != null ? "Edit" : "Add"} Lesson</h3>
        <form id="lessonForm" action="${param.lessonId != null ? 'editLesson' : 'addLesson'}"
              method="post" target="hidden_iframe" enctype="multipart/form-data">

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
                       value="${lesson != null ? lesson.videoUrl : ''}" onchange="previewVideoUrl(this.value)" />
            </div>

            <div class="form-group">
                <label for="videoFile">Select video from the device</label>
                <input type="file" name="videoFile" class="form-control" accept="video/mp4,video/webm,video/ogg" onchange="previewVideoFile(this)" />
            </div>

            <div id="videoPreviewContainer" style="display:none">
                <button type="button" id="removePreviewBtn" onclick="removeVideoPreview()">&times;</button>
                <video id="videoPreview" width="320" height="180" controls></video>
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
