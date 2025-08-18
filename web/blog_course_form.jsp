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
                        <h2><%= headerText%></h2>

                        <form action="<%= formAction%>" method="POST" enctype="multipart/form-data">

                            <input type="hidden" name="type" value="course">
                            <% if ("update".equals(action) && courseId != null) {%>
                            <input type="hidden" name="courseId" value="<%= courseId%>">
                            <% }%>
                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" class="form-control" name="title" value="<%= titleValue%>" required>
                            </div>
                            <div class="form-group">
                                <label for="description">Description</label>
                                <textarea class="form-control" name="description" rows="3" required><%= descriptionValue%></textarea>
                            </div>
                            <div class="form-group">
                                <label for="price">Price ($)</label>
                                <input type="number" class="form-control" name="price" min="0" value="<%= priceValue%>" required>
                            </div>
                            <div class="form-group">
                                <label for="thumbnail_url">Thumbnail</label>
                                <div class="input-group">
                                    <input type="file" class="form-control" id="thumbnailInput" name="thumbnail" accept=".jpg,.jpeg,.png,.gif" <%= course != null ? "" : "required"%> >
                                    <!-- Thêm dòng này: -->
                                    <input type="hidden" name="thumbnail_url" value="<%= thumbnailUrlValue%>">
                                </div>

                                <small class="form-text text-muted">Bạn có thể dán URL hoặc chọn một ảnh từ máy.</small>

                                <div id="thumbPreviewWrap" style="margin-top:12px; display:<%= (thumbnailUrlValue != null && !thumbnailUrlValue.isEmpty()) ? "block" : "none"%>;">
                                    <img id="thumbPreview" src="<%= thumbnailUrlValue%>" 
                                         alt="Thumbnail preview" style="max-width: 100%; border:1px solid #e9ecef; border-radius:6px;">
                                </div>

                                <!-- Nếu user upload file, mình tạm để base64 vào hidden này để backend xử lý sau -->
                                <input type="hidden" id="thumbnail_base64" name="thumbnail_base64" value="">
                            </div>


                            <div class="form-group">
                                <label for="topic_id" class="d-flex justify-content-between align-items-center">
                                    <span>Topic</span>
                                    <button type="button" class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#addTopicModal">
                                        + Add new
                                    </button>
                                </label>

                                <select class="form-control" id="topic_id" name="topic_id" required>
                                    <option value="">Select Topic</option>
                                    <% for (Topic t : topics) {%>
                                    <option value="<%= t.getTopic_id()%>" <%= String.valueOf(t.getTopic_id()).equals(topicIdValue) ? "selected" : ""%>>
                                        <%= t.getName()%>
                                    </option>
                                    <% }%>
                                </select>

                                <!-- Hidden fields để backend biết có “topic mới” cần tạo -->
                                <input type="hidden" id="is_new_topic" name="is_new_topic" value="false">
                                <input type="hidden" id="new_topic_name" name="new_topic_name" value="">
                                <input type="hidden" id="new_topic_desc" name="new_topic_desc" value="">
                            </div>

                            <!-- Modal Add Topic -->
                            <div class="modal fade" id="addTopicModal" tabindex="-1" aria-labelledby="addTopicModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="addTopicModalLabel">Add New Topic</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="mb-3">
                                                <label class="form-label">Topic Name <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" id="inpNewTopicName" placeholder="e.g. HTML & CSS">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Description</label>
                                                <textarea class="form-control" id="inpNewTopicDesc" rows="3" placeholder="(Optional) Short description"></textarea>
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                                            <button type="button" class="btn btn-primary" id="btnSaveNewTopic">Save Topic</button>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="btn-group">
                                <button type="submit" class="btn btn-primary"><%= btnText%></button>
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
        <script>
                                        document.getElementById('thumbnailInput').addEventListener('change', function (event) {
                                            const file = event.target.files[0];
                                            if (!file)
                                                return;

                                            const reader = new FileReader();
                                            reader.onload = function (e) {
                                                const preview = document.getElementById('thumbPreview');
                                                const previewWrap = document.getElementById('thumbPreviewWrap');

                                                preview.src = e.target.result;
                                                preview.style.display = 'block';
                                                previewWrap.style.display = 'block';
                                            };
                                            reader.readAsDataURL(file);
                                        });
        </script>

    </body>

</html>

