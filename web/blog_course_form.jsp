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
        <%
            TopicDAO topicDAO = new TopicDAO();
            List<Topic> topics = topicDAO.getAllTopics();
        %>
        .form-container {
            max-width: 700px;
            margin: 20px auto;
            padding: 30px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border: 1px solid #e9ecef;
        }
        .form-group label { font-weight: 600; color: #343a40; }
        .form-control { border-color: #ced4da; border-radius: 5px; }
        .btn-primary { background-color: #007bff; border-color: #007bff; }
        .btn-primary:hover { background-color: #0056b3; border-color: #0056b3; }
        .btn-secondary { background-color: #6c757d; border-color: #6c757d; }
        .btn-secondary:hover { background-color: #5a6268; border-color: #5a6268; }
        .btn-group { margin-top: 20px; display: flex; gap: 10px; }
        h2 { color: #007bff; margin-bottom: 20px; }
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
                %>
                <h2><%= headerText %></h2>
                
                <form action="<%= formAction %>" method="POST">
                    <input type="hidden" name="type" value="course">
                    <% if ("update".equals(action) && courseId != null) { %>
                        <input type="hidden" name="courseId" value="<%= courseId %>">
                    <% } %>
                    <div class="form-group">
                        <label for="title">Title</label>
                        <input type="text" class="form-control" name="title" value="<%= titleValue %>" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea class="form-control" name="description" rows="3" required><%= descriptionValue %></textarea>
                    </div>
                    <div class="form-group">
                        <label for="price">Price ($)</label>
                        <input type="number" class="form-control" name="price" min="0" value="<%= priceValue %>" required>
                    </div>
                    <div class="form-group">
                        <label for="thumbnail_url">Thumbnail URL</label>
                        <input type="text" class="form-control" name="thumbnail_url" value="<%= thumbnailUrlValue %>">
                    </div>
                    <div class="form-group">
                        <label for="topic_id">Topic</label>
                        <select class="form-control" name="topic_id" required>
                            <option value="">Select Topic</option>
                            <% for (Topic t : topics) { %>
                                <option value="<%= t.getTopic_id() %>" <%= String.valueOf(t.getTopic_id()).equals(topicIdValue) ? "selected" : "" %>>
                                    <%= t.getName() %>
                                </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="btn-group">
                        <button type="submit" class="btn btn-primary"><%= btnText %></button>
                        <a href="listCousera" class="btn btn-secondary">Cancel</a>
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
                <!-- Additional columns omitted for brevity -->
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
</body>
</html>
