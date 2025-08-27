<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="dal.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Content Management | Seller Dashboard</title>
    <meta name="description" content="Create or update a course for the online learning platform">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        <% TopicDAO topicDAO = new TopicDAO();
           List<Topic> topics = topicDAO.getAllTopics(); %>
        .form-container {
            max-width: 700px;
            margin: 20px auto;
            padding: 30px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: 1px solid #e9ecef;
        }
        .form-group label {
            font-weight: 600;
            color: #343a40;
        }
        .form-control {
            border-color: #ced4da;
            border-radius: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
            border-color: #5a6268;
        }
        .btn-group {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        h2 {
            color: #007bff;
            margin-bottom: 20px;
        }
        #thumbPreviewWrap {
            position: relative;
            display: inline-block;
            margin-top: 12px;
        }
        .remove-thumb {
            position: absolute;
            top: 5px;
            right: 5px;
            background: rgba(0,0,0,0.6);
            color: #fff;
            border: none;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            text-align: center;
            line-height: 22px;
            cursor: pointer;
            font-weight: bold;
        }
        .remove-thumb:hover {
            background: rgba(255,0,0,0.8);
        }
        .error-message {
            color: #dc3545;
            font-size: 0.875rem;
            margin-top: 5px;
        }
        .success-message {
            color: #28a745;
            font-size: 0.875rem;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<header>
    <div class="header-area header-transparent">
        <div class="main-header">
            <div class="header-bottom header-sticky">
                <div class="container-fluid">
                    <div class="row align-items-center">
                        <div class="col-xl-2 col-lg-2">
                            <div class="logo">
                                <a href="index.jsp"><img src="assets/img/logo/logo.png" alt=""></a>
                            </div>
                        </div>
                        <div class="col-xl-10 col-lg-10">
                            <div class="menu-wrapper d-flex align-items-center justify-content-end">
                                <div class="main-menu d-none d-lg-block">
                                    <nav>
                                        <ul id="navigation">
                                            <li><a href="seller.jsp">Back to Dashboard</a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="mobile_menu d-block d-lg-none"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<main>
    <section class="form-area section-padding40">
        <div class="container">
            <div class="form-container">
                <%
                    String type = request.getParameter("type");
                    String action = request.getParameter("action");
                    String courseId = request.getParameter("courseId");
                    String headerText = "";
                    String btnText = "";
                    String formAction = "";
                    Course course = null;
                    if ("course".equals(type)) {
                        if ("update".equals(action) && courseId != null) {
                            headerText = "Update Course";
                            btnText = "Update Course";
                            formAction = "updateCourse";
                            CourseDAO courseDAO = new CourseDAO();
                            try {
                                course = courseDAO.getCourseById(Long.parseLong(courseId));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        } else {
                            headerText = "Create New Course";
                            btnText = "Create Course";
                            formAction = "createCourse";
                        }
                    }
                    String titleValue = course != null ? course.getTitle() : "";
                    String descriptionValue = course != null ? course.getDescription() : "";
                    String priceValue = course != null ? String.valueOf(course.getPrice()) : "";
                    String thumbnailUrlValue = course != null ? course.getThumbnail_url() : "";
                    String topicIdValue = course != null ? String.valueOf(course.getTopic_id()) : "";
                    // Retrieve newTopicId from session to preselect new topic
                    Long newTopicId = (Long) session.getAttribute("newTopicId");
                    if (newTopicId != null) {
                        topicIdValue = String.valueOf(newTopicId);
                        session.removeAttribute("newTopicId"); // Clear after use
                    }
                    // Retrieve error messages from session
                    String titleError = (String) session.getAttribute("titleError");
                    String descriptionError = (String) session.getAttribute("descriptionError");
                    String priceError = (String) session.getAttribute("priceError");
                    String thumbnailError = (String) session.getAttribute("thumbnailError");
                    String topicError = (String) session.getAttribute("topicError");
                    String duplicateMessage = (String) session.getAttribute("duplicateMessage");
                    String topicAddError = (String) session.getAttribute("topicAddError");
                    String topicAddDebug = (String) session.getAttribute("topicAddDebug");
                    // Clear session attributes
                    session.removeAttribute("titleError");
                    session.removeAttribute("descriptionError");
                    session.removeAttribute("priceError");
                    session.removeAttribute("thumbnailError");
                    session.removeAttribute("topicError");
                    session.removeAttribute("duplicateMessage");
                    session.removeAttribute("topicAddError");
                    session.removeAttribute("topicAddDebug");
                %>
                <h2><%= headerText %></h2>
                <% if (duplicateMessage != null) { %>
                <div class="error-message"><%= duplicateMessage %></div>
                <% } %>
                <% if (topicAddError != null) { %>
                <div class="error-message"><%= topicAddError %></div>
                <% } %>
                <% if (topicAddDebug != null) { %>
                <div class="success-message"><%= topicAddDebug %></div>
                <% } %>
                <form action="<%= formAction %>" method="POST" enctype="multipart/form-data">
                    <input type="hidden" name="type" value="course">
                    <% if ("update".equals(action) && courseId != null) { %>
                    <input type="hidden" name="courseId" value="<%= courseId %>">
                    <% } %>
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" class="form-control" name="title" value="<%= titleValue %>">
                        <% if (titleError != null) { %>
                        <div class="error-message"><%= titleError %></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" name="description" rows="3"><%= descriptionValue %></textarea>
                        <% if (descriptionError != null) { %>
                        <div class="error-message"><%= descriptionError %></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="price">Price ($)</label>
                        <input type="number" class="form-control" name="price"  value="<%= priceValue %>">

                        <% if (priceError != null) { %>
                        <div class="error-message"><%= priceError %></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="thumbnail">Thumbnail</label>
                        <input type="file" class="form-control mb-2" id="thumbnailInput" name="thumbnail" accept=".jpg,.jpeg,.png,.gif">
                        <div id="thumbPreviewWrap" style="display:${not empty thumbnailUrlValue ? 'block' : 'none'}; margin-top: 12px;">
                            <button type="button" class="remove-thumb" id="btnRemoveThumb">&times;</button>
                            <img id="thumbPreview" src="${pageContext.request.contextPath}/<%= thumbnailUrlValue %>" alt="Thumbnail preview" style="max-width: 100%; border: 1px solid #e9ecef; border-radius: 6px;">
                        </div>
                        <input type="hidden" name="thumbnail_url" id="thumbnail_url" value="<%= thumbnailUrlValue != null && !thumbnailUrlValue.isEmpty() ? thumbnailUrlValue : "" %>">
                        <% if (thumbnailError != null) { %>
                        <div class="error-message"><%= thumbnailError %></div>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <label for="topic_id" class="d-flex justify-content-between align-items-center">
                            <span>Topic</span>
                            <a href="add_topic.jsp?type=<%= type != null ? type : "course" %><%= action != null ? "&action=" + action : "" %><%= courseId != null ? "&courseId=" + courseId : "" %>" class="btn btn-link">+ Add New Topic</a>
                        </label>
                        <select class="form-control" id="topic_id" name="topic_id">
                            <option value="">Select Topic</option>
                            <% for (Topic t : topics) { %>
                            <option value="<%= t.getTopic_id() %>" <%= String.valueOf(t.getTopic_id()).equals(topicIdValue) ? "selected" : "" %>><%= t.getName() %></option>
                            <% } %>
                        </select>
                        <% if (topicError != null) { %>
                        <div class="error-message"><%= topicError %></div>
                        <% } %>
                    </div>
                    <div class="btn-group">
                        <button type="submit" class="btn btn-primary"><%= btnText %></button>
                        <a href="listCourses" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </section>
</main>
<footer>
    <div class="footer-area footer-padding">
        <div class="container">
            <div class="row justify-content-between">
                <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                    <div class="footer-logo mb-25">
                        <a href="index.jsp"><img src="assets/img/logo/logo2_footer.png" alt=""></a>
                    </div>
                    <p>The automated process starts as soon as your clothes go into the machine.</p>
                    <div class="footer-social">
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="https://bit.ly/sai4ull"><i class="fab fa-facebook-f"></i></a>
                        <a href="#"><i class="fab fa-pinterest-p"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer-bottom-area">
        <div class="container">
            <div class="footer-border">
                <div class="row d-flex align-items-center">
                    <div class="col-xl-12">
                        <div class="footer-copy-right text-center">
                            <p>Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>
<script src="./assets/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const fileInput = document.getElementById('thumbnailInput');
    const hiddenInput = document.getElementById('thumbnail_url');
    const previewImg = document.getElementById('thumbPreview');
    const previewWrap = document.getElementById('thumbPreviewWrap');
    const removeBtn = document.getElementById('btnRemoveThumb');

    // Load ảnh cũ khi trang được tải
    window.onload = function() {
        const initialThumbnailUrl = "<%= thumbnailUrlValue %>";
        if (initialThumbnailUrl && initialThumbnailUrl.trim() !== "") {
            previewImg.src = "${pageContext.request.contextPath}/" + initialThumbnailUrl;
            previewWrap.style.display = "block";
        } else {
            previewWrap.style.display = "none";
        }
    };

    fileInput.addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function (e) {
            previewImg.src = e.target.result;
            previewWrap.style.display = "block";
            hiddenInput.value = "file"; // Đánh dấu rằng đã chọn file mới
        };
        reader.readAsDataURL(file);
    });

    removeBtn?.addEventListener('click', function () {
        previewImg.src = "";
        previewWrap.style.display = "none";
        fileInput.value = "";
        hiddenInput.value = "null"; // Đánh dấu xóa ảnh
    });
</script>
</body>
</html>