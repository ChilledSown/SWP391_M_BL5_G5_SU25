<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Add New Topic | Seller Dashboard</title>
    <meta name="description" content="Add a new topic for the online learning platform">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
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
                    String topicAddError = (String) session.getAttribute("topicAddError");
                    String topicAddDebug = (String) session.getAttribute("topicAddDebug");
                    session.removeAttribute("topicAddError");
                    session.removeAttribute("topicAddDebug");
                %>
                <h2>Add New Topic</h2>
                <% if (topicAddError != null) { %>
                <div class="error-message"><%= topicAddError %></div>
                <% } %>
                <% if (topicAddDebug != null) { %>
                <div class="success-message"><%= topicAddDebug %></div>
                <% } %>
                <form action="addTopicInstructor" method="POST" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="topicName">Topic Name <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="topicName" name="name" placeholder="e.g. HTML & CSS">
                    </div>
                    <div class="form-group">
                        <label for="topicDescription">Description</label>
                        <textarea class="form-control" id="topicDescription" name="description" rows="3" placeholder="(Optional) Short description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="topicThumbnail">Thumbnail</label>
                        <input type="file" class="form-control mb-2" id="topicThumbnail" name="thumbnail" accept="image/*">
                        <div id="thumbPreviewWrap" style="display: none;">
                            <button type="button" class="remove-thumb" id="btnRemoveThumb">&times;</button>
                            <img id="thumbPreview" src="#" alt="Thumbnail preview" style="max-width: 100%; border: 1px solid #e9ecef; border-radius: 6px;">
                        </div>
                        <input type="hidden" name="thumbnail_url" id="thumbnail_url" value="">
                    </div>
                    <!-- Hidden inputs to maintain context for redirect -->
                    <input type="hidden" name="type" value="<%= type != null ? type : "" %>">
                    <input type="hidden" name="action" value="<%= action != null ? action : "" %>">
                    <input type="hidden" name="courseId" value="<%= courseId != null ? courseId : "" %>">
                    <div class="btn-group">
                        <button type="submit" class="btn btn-primary">Save Topic</button>
                        <a href="blog_course_form.jsp?type=<%= type != null ? type : "course" %><%= action != null ? "&action=" + action : "" %><%= courseId != null ? "&courseId=" + courseId : "" %>" class="btn btn-secondary">Cancel</a>
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
    const fileInput = document.getElementById('topicThumbnail');
    const hiddenInput = document.getElementById('thumbnail_url');
    const previewImg = document.getElementById('thumbPreview');
    const previewWrap = document.getElementById('thumbPreviewWrap');
    const removeBtn = document.getElementById('btnRemoveThumb');

    fileInput.addEventListener('change', function (event) {
        const file = event.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function (e) {
            previewImg.src = e.target.result;
            previewWrap.style.display = 'block';
            hiddenInput.value = 'file'; // Indicate a new file is selected
        };
        reader.readAsDataURL(file);
    });

    removeBtn?.addEventListener('click', function () {
        previewImg.src = '';
        previewWrap.style.display = 'none';
        fileInput.value = '';
        hiddenInput.value = 'null'; // Indicate image is cleared
    });
</script>
</body>
</html>