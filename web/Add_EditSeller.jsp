
<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page import="dal.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Blog Management | Seller Dashboard</title>
    <meta name="description" content="Create or update a blog for the online learning platform">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
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
        #thumbPreviewWrap {
            position: relative;
            display: inline-block;
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
                                    <a href="index.jsp"><img src="${pageContext.request.contextPath}/assets/img/logo/logo.png" alt=""></a>
                                </div>
                            </div>
                            <div class="col-xl-10 col-lg-10">
                                <div class="menu-wrapper d-flex align-items-center justify-content-end">
                                    <div class="main-menu d-none d-lg-block">
                                        <nav>
                                            <ul id="navigation">
                                                <li><a href="listBlogsInstructor">Back to Blog Management</a></li>
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
        </header>
        <main>
            <section class="form-area section-padding40">
                <div class="container">
                    <div class="form-container">
                        <%
                            String action = request.getParameter("action");
                            String blogId = request.getParameter("blogId");
                            String headerText = "";
                            String btnText = "";
                            String formAction = "";
                            Blog blog = null;
                            BlogDAO blogDAO = new BlogDAO();
                            if ("update".equals(action) && blogId != null) {
                                headerText = "Update Blog";
                                btnText = "Update Blog";
                                formAction = "editBlog";
                                try {
                                    blog = blogDAO.getBlogById(Long.parseLong(blogId));
                                    if (blog == null) {
                                        request.setAttribute("errorMessage", "Blog not found.");
                                    }
                                } catch (NumberFormatException e) {
                                    request.setAttribute("errorMessage", "Invalid blog ID.");
                                }
                            } else {
                                headerText = "Create New Blog";
                                btnText = "Create Blog";
                                formAction = "createBlog";
                            }
                            // Use request attribute if available (set by servlet)
                            if (request.getAttribute("blog") != null) {
                                blog = (Blog) request.getAttribute("blog");
                            }
                            String titleValue = blog != null ? blog.getTitle() : "";
                            String contentValue = blog != null ? blog.getContent() : "";
                            String thumbnailUrlValue = blog != null ? (blog.getThumbnailUrl() != null ? blog.getThumbnailUrl() : "") : "";
                            // Retrieve error messages from session
                            String titleError = (String) session.getAttribute("titleError");
                            String contentError = (String) session.getAttribute("contentError");
                            String thumbnailError = (String) session.getAttribute("thumbnailError");
                            String duplicateMessage = (String) session.getAttribute("duplicateMessage");
                            String errorMessage = (String) session.getAttribute("errorMessage");
                            // Clear session attributes
                            session.removeAttribute("titleError");
                            session.removeAttribute("contentError");
                            session.removeAttribute("thumbnailError");
                            session.removeAttribute("duplicateMessage");
                            session.removeAttribute("errorMessage");
                        %>
                        <h2><%= headerText %></h2>
                        <% if (duplicateMessage != null) { %>
                            <div class="error-message"><%= duplicateMessage %></div>
                        <% } %>
                        <% if (errorMessage != null) { %>
                            <div class="error-message"><%= errorMessage %></div>
                        <% } %>
                        <form id="blogForm" action="<%= formAction %>" method="POST" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="<%= action != null ? action : "create" %>">
                            <% if ("update".equals(action) && blogId != null) { %>
                                <input type="hidden" name="blogId" value="<%= blogId %>">
                                <input type="hidden" name="existingThumbnail" value="<%= thumbnailUrlValue %>">
                            <% } %>
                            <div class="form-group">
                                <label for="title">Title</label>
                                <input type="text" class="form-control" name="title" id="title" value="<%= titleValue %>">
                                <% if (titleError != null) { %>
                                    <div class="error-message"><%= titleError %></div>
                                <% } %>
                            </div>
                            <div class="form-group">
                                <label for="content">Content</label>
                                <textarea class="form-control" name="content" id="content" rows="5"><%= contentValue %></textarea>
                                <% if (contentError != null) { %>
                                    <div class="error-message"><%= contentError %></div>
                                <% } %>
                            </div>
                            <div class="form-group">
                                <label for="thumbnailInput">Thumbnail</label>
                                <input type="file" class="form-control mb-2" id="thumbnailInput" name="thumbnail" accept=".jpg,.jpeg,.png,.gif">
                                <div id="thumbPreviewWrap" style="margin-top:12px; display:<%= (thumbnailUrlValue != null && !thumbnailUrlValue.isEmpty()) ? "block" : "none" %>;">
                                    <button type="button" class="remove-thumb" id="btnRemoveThumb">&times;</button>
                                    <img id="thumbPreview" src="<%= thumbnailUrlValue %>" alt="Thumbnail preview" style="max-width: 100%; border:1px solid #e9ecef; border-radius:6px;">
                                </div>
                                <% if (thumbnailError != null) { %>
                                    <div class="error-message"><%= thumbnailError %></div>
                                <% } %>
                            </div>
                            <div class="btn-group">
                                <button type="submit" class="btn btn-primary"><%= btnText %></button>
                                <a href="listBlogsInstructor" class="btn btn-secondary">Cancel</a>
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
                                <a href="index.jsp"><img src="${pageContext.request.contextPath}/assets/img/logo/logo2_footer.png" alt=""></a>
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
        <script src="${pageContext.request.contextPath}/assets/js/vendor/modernizr-3.5.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/vendor/jquery-1.12.4.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
        <script>
            const fileInput = document.getElementById('thumbnailInput');
            const previewImg = document.getElementById('thumbPreview');
            const previewWrap = document.getElementById('thumbPreviewWrap');
            const removeBtn = document.getElementById('btnRemoveThumb');
            const existingThumbnail = "<%= thumbnailUrlValue %>";

            fileInput.addEventListener('change', function (event) {
                const file = event.target.files[0];
                if (!file) return;
                const reader = new FileReader();
                reader.onload = function (e) {
                    previewImg.src = e.target.result;
                    previewWrap.style.display = "block";
                };
                reader.readAsDataURL(file);
            });

            removeBtn?.addEventListener('click', function () {
                previewImg.src = "";
                previewWrap.style.display = "none";
                fileInput.value = "";
            });

            $(document).ready(function () {
                $("#blogForm").validate({
                    rules: {
                        title: { required: true },
                        content: { required: true }
                    },
                    messages: {
                        title: "Please enter a title.",
                        content: "Please enter content."
                    },
                    errorPlacement: function (error, element) {
                        error.appendTo(element.closest(".form-group"));
                    },
                    submitHandler: function (form) {
                        form.submit();
                    }
                });
            });
        </script>
    </body>
</html>
