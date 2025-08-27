<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Manage Blogs | Instructor Dashboard</title>
        <meta name="description" content="Seller dashboard for managing blogs, courses, balance, and reviews">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="manifest" href="site.webmanifest">
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico">
        <!-- CSS here -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/slicknav.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flaticon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/progressbar_barfiller.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/gijgo.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animated-headline.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/magnific-popup.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fontawesome-all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/themify-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/slick.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/nice-select.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <style>
            body {
                background: linear-gradient(120deg, #7F7FD5, #E86ED0);
                background-size: cover;
                background-repeat: no-repeat;
                background-attachment: fixed;
                font-family: 'Roboto', sans-serif;
            }
            .sidebar {
                background: #ffffff;
                padding: 20px;
                min-height: 100vh;
                box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }
            .sidebar .nav-link {
                color: #343a40;
                padding: 12px 20px;
                border-radius: 6px;
                margin-bottom: 10px;
                transition: all 0.3s ease;
                font-weight: 500;
            }
            .sidebar .nav-link:hover,
            .sidebar .nav-link.active {
                background-color: #007bff;
                color: #ffffff;
                text-decoration: none;
                transform: translateX(5px);
            }
            .content {
                background: #ffffff;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            
            /* Search & Filter Styles - Similar to Course Management */
            .search-filter-section {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                border-radius: 12px;
                padding: 25px;
                margin-bottom: 25px;
                box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
            }
            
            .search-filter-title {
                color: #ffffff;
                font-weight: 600;
                margin-bottom: 20px;
                font-size: 1.2rem;
                display: flex;
                align-items: center;
            }
            
            .search-filter-title i {
                margin-right: 10px;
                font-size: 1.3rem;
            }
            
            .filter-group {
                background: rgba(255, 255, 255, 0.95);
                border-radius: 8px;
                padding: 20px;
                backdrop-filter: blur(10px);
            }
            
            .filter-row {
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                align-items: end;
            }
            
            .filter-item {
                flex: 1;
                min-width: 200px;
            }
            
            .filter-item.search-item {
                flex: 2;
                min-width: 300px;
            }
            
            .filter-item.action-item {
                flex: 0 0 auto;
            }
            
            .filter-label {
                font-weight: 600;
                color: #495057;
                margin-bottom: 5px;
                font-size: 0.9rem;
            }
            
            .form-control, .form-select {
                border: 2px solid #e9ecef;
                border-radius: 8px;
                padding: 10px 12px;
                font-size: 0.95rem;
                transition: all 0.3s ease;
            }
            
            .form-control:focus, .form-select:focus {
                border-color: #007bff;
                box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
            }
            
            .search-input {
                position: relative;
            }
            
            .search-input i {
                position: absolute;
                left: 12px;
                top: 50%;
                transform: translateY(-50%);
                color: #6c757d;
                z-index: 5;
            }
            
            .search-input .form-control {
                padding-left: 40px;
            }
            
            .btn-search {
                background: linear-gradient(45deg, #007bff, #0056b3);
                border: none;
                border-radius: 8px;
                padding: 10px 25px;
                color: white;
                font-weight: 600;
                transition: all 0.3s ease;
                box-shadow: 0 4px 15px rgba(0, 123, 255, 0.3);
            }
            
            .btn-search:hover {
                transform: translateY(-2px);
                box-shadow: 0 6px 20px rgba(0, 123, 255, 0.4);
            }
            
            .btn-reset {
                background: #6c757d;
                border: none;
                border-radius: 8px;
                padding: 10px 20px;
                color: white;
                font-weight: 500;
                transition: all 0.3s ease;
                margin-left: 10px;
            }
            
            .btn-reset:hover {
                background: #545b62;
                transform: translateY(-1px);
            }
            
            .table {
                background: #ffffff;
                border-radius: 8px;
                overflow: hidden;
            }
            .table th {
                background-color: #007bff;
                color: #ffffff;
                font-weight: 600;
                padding: 15px;
            }
            .table td {
                vertical-align: middle;
                padding: 15px;
                color: #343a40;
            }
            .table tbody tr:hover {
                background-color: #f1f3f5;
            }
            .btn-primary {
                background-color: #007bff;
                border-color: #007bff;
                padding: 10px 20px;
                border-radius: 6px;
                transition: all 0.3s ease;
            }
            .btn-primary:hover {
                background-color: #0056b3;
                border-color: #0056b3;
                transform: translateY(-2px);
            }
            .btn-action {
                background-color: #ff8243;
                color: white;
                border: none;
                border-radius: 8px;
                padding: 6px 8px;
                margin: 2px;
                font-size: 14px;
                width: 32px;
                height: 32px;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                transition: background-color 0.2s ease-in-out;
            }
            .btn-action:hover {
                background-color: #e67030;
            }
            .btn-action i {
                font-size: 16px;
            }
            #navigation a {
                color: #343a40 !important;
                font-weight: 500;
                transition: color 0.3s ease;
            }
            #navigation a:hover {
                color: #007bff !important;
            }
            .content h2 {
                color: #007bff;
                font-weight: 700;
                margin-bottom: 20px;
            }
            .content h3 {
                color: #343a40;
                font-weight: 600;
                margin-bottom: 15px;
            }
            .content p {
                color: #495057;
                font-size: 1.1rem;
            }
            .footer-wrappper {
                background: #343a40;
            }
            .footer-tittle h4, .footer-pera p {
                color: #ffffff;
            }
            .footer-social a {
                color: #ffffff;
                margin-right: 15px;
                transition: color 0.3s ease;
            }
            .footer-social a:hover {
                color: #007bff;
            }
            
            @media (max-width: 991px) {
                .sidebar {
                    min-height: auto;
                    margin-bottom: 20px;
                }
                .sidebar .nav-link {
                    padding: 10px;
                }
                .content {
                    padding: 20px;
                }
                .filter-row {
                    flex-direction: column;
                }
                .filter-item, .filter-item.search-item {
                    flex: 1;
                    min-width: 100%;
                }
            }
            
            @media (max-width: 767px) {
                .search-filter-section {
                    padding: 20px 15px;
                }
                .filter-group {
                    padding: 15px;
                }
                .btn-search, .btn-reset {
                    width: 100%;
                    margin: 5px 0;
                }
            }
        </style>
    </head>
    <body>
        <!-- Preloader Start -->
        <div id="preloader-active">
            <div class="preloader d-flex align-items-center justify-content-center">
                <div class="preloader-inner position-relative">
                    <div class="preloader-circle"></div>
                    <div class="preloader-img pere-text">
                        <img src="${pageContext.request.contextPath}/assets/img/logo/loder.png" alt="Preloader">
                    </div>
                </div>
            </div>
        </div>
        <!-- Preloader End -->
        <header>
            <div class="header-area header-transparent">
                <div class="main-header">
                    <div class="header-bottom header-sticky">
                        <div class="container-fluid">
                            <div class="row align-items-center">
                                <div class="col-xl-2 col-lg-2">
                                    <div class="logo">
                                        <a href="index.jsp"><img src="${pageContext.request.contextPath}/assets/img/logo/logo.png" alt="Logo"></a>
                                    </div>
                                </div>
                                <div class="col-xl-10 col-lg-10">
                                    <div class="menu-wrapper d-flex align-items-center justify-content-end">
                                        <div class="main-menu d-none d-lg-block">
                                            <nav>
                                                <ul id="navigation">
                                                  
                                                    <li><a href="DashBoardSeller.jsp">Dashboard</a></li>
                                                    <li><a href="logout">Logout</a></li>
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
            <section class="dashboard-area section-padding40">
                <div class="container-fluid">
                    <div class="row">
                        <!-- Sidebar -->
                        <div class="col-lg-3 col-md-4 sidebar">
                            <ul class="nav flex-column" id="sidebarNav">
                                <li class="nav-item"><a href="DashBoard" class="nav-link">Overview</a></li>
                                <li class="nav-item"><a href="listCourses" class="nav-link">Courses</a></li>
                                <li class="nav-item"><a href="listBlogsInstructor" class="nav-link active">Blogs</a></li>
                                <li class="nav-item"><a href="balance" class="nav-link">Order</a></li>
                                <li class="nav-item"><a href="listReviews" class="nav-link">Reviews</a></li>
                            </ul>
                        </div>
                        <!-- Main Content -->
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Blog Management</h2>
                            <p>Manage your blogs here.</p>
                            
                            <!-- Improved Search & Filter Section -->
                            <div class="search-filter-section">
                                <div class="search-filter-title">
                                    <i class="fas fa-search"></i>
                                    Search & Filter Blogs
                                </div>
                                <div class="filter-group">
                                    <form method="get" action="listBlogsInstructor">
                                        <div class="filter-row">
                                            <div class="filter-item search-item">
                                                <div class="filter-label">Search Blog Title</div>
                                                <div class="search-input">
                                                    <i class="fas fa-search"></i>
                                                    <input type="text" name="title" class="form-control" 
                                                           placeholder="Enter blog title..." value="${param.title}">
                                                </div>
                                            </div>
                                            
                                            <div class="filter-item">
                                                <div class="filter-label">Created Date</div>
                                                <input type="date" name="createdDate" class="form-control" value="${param.createdDate}">
                                            </div>
                                            
                                            <div class="filter-item action-item">
                                                <div class="filter-label">&nbsp;</div>
                                                <button type="submit" class="btn btn-search">
                                                    <i class="fas fa-search me-2"></i>Search
                                                </button>
                                                <button type="button" class="btn btn-reset" onclick="resetFilters()">
                                                    <i class="fas fa-undo"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            
                            <div class="d-flex justify-content-between align-items-center mb-3">
                                <a href="Add_EditSeller.jsp" class="btn btn-primary">
                                    <i class="fas fa-plus me-2"></i>Create New Blog
                                </a>
                            </div>
                            
                            <!-- Blog Table -->
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Thumbnail</th>
                                        <th>Title</th>
                                        <th>Created At</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="blog" items="${blogs}">
                                        <tr>
                                            <td class="text-center">
                                                <img src="${blog.thumbnailUrl}" alt="Thumbnail" style="width: 80px; border-radius: 8px;">
                                            </td>
                                            <td>${blog.title}</td>
                                            <td>
                                                <fmt:parseDate value="${blog.createdAt}" pattern="yyyy-MM-dd" var="parsedDate" />
                                                <fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" />
                                            </td>
                                            <td>
                                                <a href="Add_EditSeller.jsp?action=update&blogId=${blog.blogId}" class="btn-action" title="Edit">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="deleteBlog?blogId=${blog.blogId}" class="btn-action" title="Delete" onclick="return confirm('Are you sure you want to delete this blog?');">
                                                    <i class="fas fa-trash-alt"></i>
                                                </a>
                                                <a href="blogDetailSeller?blogId=${blog.blogId}" class="btn-action" title="Detail">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty blogs}">
                                        <tr>
                                            <td colspan="4" class="text-center">No blogs found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                            <jsp:include page="pagination.jsp" />
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <footer>
            <div class="footer-wrappper footer-bg">
                <div class="footer-area footer-padding">
                    <div class="container">
                        <div class="row justify-content-between">
                            <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                                <div class="single-footer-caption mb-50">
                                    <div class="footer-logo mb-25">
                                        <a href="index.jsp"><img src="${pageContext.request.contextPath}/assets/img/logo/logo2_footer.png" alt="Footer Logo"></a>
                                    </div>
                                    <div class="footer-tittle">
                                        <div class="footer-pera">
                                            <p>The automated process starts as soon as your clothes go into the machine.</p>
                                        </div>
                                    </div>
                                    <div class="footer-social">
                                        <a href="#"><i class="fab fa-twitter"></i></a>
                                        <a href="https://bit.ly/sai4ull"><i class="fab fa-facebook-f"></i></a>
                                        <a href="#"><i class="fab fa-pinterest-p"></i></a>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-2 col-lg-3 col-md-4 col-sm-5">
                                <div class="single-footer-caption mb-50">
                                    <div class="footer-tittle">
                                        <h4>Our solutions</h4>
                                        <ul>
                                            <li><a href="#">Design & creatives</a></li>
                                            <li><a href="#">Telecommunication</a></li>
                                            <li><a href="#">Restaurant</a></li>
                                            <li><a href="#">Programing</a></li>
                                            <li><a href="#">Architecture</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-2 col-lg-4 col-md-4 col-sm-6">
                                <div class="single-footer-caption mb-50">
                                    <div class="footer-tittle">
                                        <h4>Support</h4>
                                        <ul>
                                            <li><a href="#">Design & creatives</a></li>
                                            <li><a href="#">Telecommunication</a></li>
                                            <li><a href="#">Restaurant</a></li>
                                            <li><a href="#">Programing</a></li>
                                            <li><a href="#">Architecture</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-2 col-lg-3 col-md-4 col-sm-6">
                                <div class="single-footer-caption mb-50">
                                    <div class="footer-tittle">
                                        <h4>Company</h4>
                                        <ul>
                                            <li><a href="#">Design & creatives</a></li>
                                            <li><a href="#">Telecommunication</a></li>
                                            <li><a href="#">Restaurant</a></li>
                                            <li><a href="#">Programing</a></li>
                                            <li><a href="#">Architecture</a></li>
                                        </ul>
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
                                            <p>Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </footer>
        <div id="back-top">
            <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
        </div>
        <!-- JS here -->
        <script src="${pageContext.request.contextPath}/assets/js/vendor/modernizr-3.5.0.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/vendor/jquery-1.12.4.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/popper.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.slicknav.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/slick.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/animated.headline.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.magnific-popup.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/gijgo.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.nice-select.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.sticky.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.barfiller.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.counterup.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/waypoints.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.countdown.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/hover-direction-snake.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/contact.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.form.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.validate.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/mail-script.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/jquery.ajaxchimp.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/plugins.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
        
        <script>
            function resetFilters() {
                // Reset form fields
                document.querySelector('input[name="title"]').value = '';
                document.querySelector('input[name="createdDate"]').value = '';
                
                // Submit form to clear filters
                document.querySelector('form').submit();
            }
        </script>
    </body>
</html>