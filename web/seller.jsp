<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dal.TopicDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Topic" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.Course" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Manage Courses | Seller Dashboard</title>
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
            .form-group {
                margin-bottom: 20px;
            }
            .form-control {
                border-radius: 6px;
                border: 1px solid #ced4da;
                padding: 10px;
                transition: border-color 0.3s ease;
            }
            .form-control:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
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
            }
            @media (max-width: 767px) {
                .form-inline .form-group {
                    margin-bottom: 15px;
                    width: 100%;
                }
                .form-control {
                    width: 100%;
                }
                .btn-primary {
                    width: 100%;
                    text-align: center;
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
                                                    <li><a href="index.jsp">Home</a></li>
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
                                <li class="nav-item"><a href="DashBoardSeller.jsp" class="nav-link">Overview</a></li>
                                <li class="nav-item"><a href="listCousera" class="nav-link active">Courses</a></li>
                                <li class="nav-item"><a href="instructorvideoquiz" class="nav-link">Video Quiz</a></li>
                                <li class="nav-item"><a href="listBlogsSeller" class="nav-link">Blogs</a></li>
                                <li class="nav-item"><a href="balance" class="nav-link">Balance</a></li>
                                <li class="nav-item"><a href="reviews.jsp" class="nav-link">Reviews</a></li>
                            </ul>
                        </div>
                        <!-- Main Content -->
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Course Management</h2>
                            <p>Manage your courses here.</p>
                            <%
                                TopicDAO topicDAO = new TopicDAO();
                                List<Topic> topics = topicDAO.getAllTopics();
                                Map<Long, String> topicMap = new HashMap<>();
                                for (Topic t : topics) {
                                    topicMap.put(t.getTopic_id(), t.getName());
                                }
                                request.setAttribute("topicMap", topicMap);
                            %>
                            <form action="listCousera" method="get" class="mb-4">
                                <div class="row">
                                    <div class="col-md-4 col-sm-12 form-group">
                                        <label for="title" class="mr-2">Search Courses</label>
                                        <input type="text" class="form-control" name="title" id="title" value="${param.title}" placeholder="Enter course title">
                                    </div>
                                    <div class="col-md-4 col-sm-12 form-group">
                                        <label for="createdDate" class="mr-2">Date:</label>
                                        <input type="date" class="form-control" name="createdDate" id="createdDate" value="${param.createdDate}">
                                    </div>
                                    <div class="col-md-4 col-sm-12 form-group">
                                        <label for="topicId" class="mr-2">Topic:</label>
                                        <select name="topicId" id="topicId" class="form-control">
                                            <option value="">All Topics</option>
                                            <% for (Topic t : topics) {%>
                                            <option value="<%= t.getTopic_id()%>" <%= (t.getTopic_id() + "").equals(request.getParameter("topicId")) ? "selected" : ""%>>
                                                <%= t.getName()%>
                                            </option>
                                            <% }%>
                                        </select>
                                    </div>
                                    <div class="col-12 form-group mt-3">
                                        <button type="submit" class="btn btn-primary">Search</button>
                                    </div>
                                </div>
                                <input type="hidden" name="page" value="1" />
                            </form>
                            <a href="blog_course_form.jsp?type=course&action=create" class="btn btn-primary mb-3">Create New Course</a>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Image</th>
                                        <th>Title</th>
                                        <th>Price</th>
                                        <th>Create_At</th>
                                        <th>Topic</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="course" items="${courses}">
                                        <tr>
                                            <td class="text-center">
                                                <img src="${course.thumbnail_url}" alt="Thumbnail" style="width: 80px; border-radius: 8px;">
                                            </td>
                                            <td>${course.title}</td>
                                            <td>${course.price}</td>
                                            <td><fmt:formatDate value="${course.created_at}" pattern="yyyy-MM-dd" /></td>
                                            <td>${topicMap[course.topic_id]}</td>
                                            <td>
                                                <a href="blog_course_form.jsp?type=course&action=update&courseId=${course.course_id}" class="btn-action" title="Edit">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="deleteCourse?courseId=${course.course_id}" class="btn-action" title="Delete" onclick="return confirm('Are you sure?');">
                                                    <i class="fas fa-trash-alt"></i>
                                                </a>
                                                <a href="courseDetail?courseId=${course.course_id}" class="btn-action" title="View">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty courses}">
                                        <tr>
                                            <td colspan="6" class="text-center">No courses found.</td>
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
    </body>
</html>