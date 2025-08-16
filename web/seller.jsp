<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dal.TopicDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Topic" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Seller Dashboard | Online Learning</title>
        <meta name="description" content="Seller dashboard for managing blogs and courses">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="manifest" href="site.webmanifest">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <!-- CSS here -->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/owl.carousel.min.css">
        <link rel="stylesheet" href="assets/css/slicknav.css">
        <link rel="stylesheet" href="assets/css/flaticon.css">
        <link rel="stylesheet" href="assets/css/progressbar_barfiller.css">
        <link rel="stylesheet" href="assets/css/gijgo.css">
        <link rel="stylesheet" href="assets/css/animate.min.css">
        <link rel="stylesheet" href="assets/css/animated-headline.css">
        <link rel="stylesheet" href="assets/css/magnific-popup.css">
        <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/css/themify-icons.css">
        <link rel="stylesheet" href="assets/css/slick.css">
        <link rel="stylesheet" href="assets/css/nice-select.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <style>
            .sidebar {
                background: #f8f9fa;
                padding: 20px;
                min-height: 100vh;
            }
            .sidebar .nav-link {
                color: #343a40; /* Dark gray for visibility */
                padding: 10px 15px;
                border-radius: 5px;
                transition: all 0.3s ease;
            }
            .sidebar .nav-link:hover,
            .sidebar .nav-link.active {
                background-color: #007bff; /* Blue background for active/hover */
                color: #ffffff; /* White text for contrast */
                text-decoration: none;
            }
            .management-section {
                margin-bottom: 40px;
                display: none; /* Hide all sections by default */
            }
            .table th, .table td {
                vertical-align: middle;
            }
            /* Header navigation text color */
            #navigation a {
                color: #343a40 !important; /* Dark gray for header links */
            }
            /* Main content headings */
            .content h2, .content h3 {
                color: #007bff; /* Blue for headings */
            }
            .content p {
                color: #495057; /* Darker gray for welcome text */
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
                        <img src="assets/img/logo/loder.png" alt="">
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
                                <!-- Logo -->
                                <div class="col-xl-2 col-lg-2">
                                    <div class="logo">
                                        <a href="index.jsp"><img src="assets/img/logo/logo.png" alt=""></a>
                                    </div>
                                </div>
                                <div class="col-xl-10 col-lg-10">
                                    <div class="menu-wrapper d-flex align-items-center justify-content-end">
                                        <!-- Main-menu -->
                                        <div class="main-menu d-none d-lg-block">
                                            <nav>
                                                <ul id="navigation">
                                                    <li><a href="index.jsp">Home</a></li>
                                                    <li><a href="seller.jsp">Dashboard</a></li>
                                                    <li><a href="logout.jsp">Logout</a></li>
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>
                                </div>
                                <!-- Mobile Menu -->
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
            <section class="dashboard-area section-padding40">
                <div class="container-fluid">
                    <div class="row">
                        <!-- Sidebar -->
                        <div class="col-lg-3 col-md-4 sidebar">
                            <ul class="nav flex-column" id="sidebarNav">
                                <li class="nav-item"><a href="#courses" class="nav-link">Courses</a></li>
                                <li class="nav-item"><a href="#blogs" class="nav-link">Blogs</a></li>
                                <li class="nav-item"><a href="#communication" class="nav-link">Communication</a></li>
                                <li class="nav-item"><a href="#performance" class="nav-link">Performance</a></li>
                                <li class="nav-item"><a href="#tools" class="nav-link">Tools</a></li>
                                <li class="nav-item"><a href="#resources" class="nav-link">Resources</a></li>
                            </ul>
                        </div>
                        <!-- Main Content -->
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Seller Dashboard</h2>
                            <p>Welcome, [Seller Name]. Manage your blogs and courses here.</p>

                            <!-- Course Management Section -->

                            <%
                                TopicDAO topicDAO = new TopicDAO();
                                List<Topic> topics = topicDAO.getAllTopics(); // hoặc topicDAO.getTopicsByCreator nếu cần theo seller
                            %>

                            <form action="listCousera" method="get" class="form-inline mb-4">
                                <input type="hidden" name="page" value="1" />

                                <!-- Search by title -->
                                <div class="form-group mr-3">
                                    <label for="title" class="mr-2">Title:</label>
                                    <input type="text" class="form-control" name="title" id="title" value="${param.title}" placeholder="Enter course title">
                                </div>

                                <!-- Search by created date -->
                                <div class="form-group mr-3">
                                    <label for="createdDate" class="mr-2">Created Date:</label>
                                    <input type="date" class="form-control" name="createdDate" id="createdDate" value="${param.createdDate}">
                                </div>

                                <!-- Filter by topic -->
                                <div class="form-group mr-3">
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

                                <!-- Submit -->
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary">Search</button>
                                </div>
                            </form>

                            <!-- Blog Management Section -->
                            <div id="courses" class="management-section container mt-4">
                                <h3>Course Management</h3>
                                <a href="blog_course_form.jsp?type=course&action=create" class="btn btn-primary mb-3">Create New Course</a>
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Title</th>
                                            <th>Price</th>

                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="course" items="${courses}">
                                            <tr>
                                                <td>${course.title}</td>
                                                <td>$${course.price}</td>

                                                <td>
                                                    <a href="blog_course_form.jsp?type=course&action=update&courseId=${course.course_id}"
                                                       class="btn btn-sm btn-warning">Update</a>
                                                    <a href="deleteCourse?courseId=${course.course_id}" 
                                                       class="btn btn-sm btn-danger" 
                                                       onclick="return confirm('Are you sure you want to delete this course??');">
                                                        Delete
                                                    </a>

                                                    <a href="courseDetail?courseId=${course.course_id}" class="btn btn-sm btn-info">Detail</a>


                                                </td

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <jsp:include page="pagination.jsp" />
                            </div>

                            <!-- Other Placeholder Sections -->
                            <div id="communication" class="management-section" style="display: none;">
                                <h3>Communication</h3>
                                <p>Placeholder for communication features.</p>
                            </div>
                            <div id="performance" class="management-section" style="display: none;">
                                <h3>Performance</h3>
                                <p>Placeholder for performance analytics.</p>
                            </div>
                            <div id="tools" class="management-section" style="display: none;">
                                <h3>Tools</h3>
                                <p>Placeholder for tools section.</p>
                            </div>
                            <div id="resources" class="management-section" style="display: none;">
                                <h3>Resources</h3>
                                <p>Placeholder for resources section.</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>

        <footer>
            <div class="footer-wrappper footer-bg">
                <!-- Footer Start -->
                <div class="footer-area footer-padding">
                    <div class="container">
                        <div class="row justify-content-between">
                            <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                                <div class="single-footer-caption mb-50">
                                    <div class="single-footer-caption mb-30">
                                        <!-- logo -->
                                        <div class="footer-logo mb-25">
                                            <a href="index.jsp"><img src="assets/img/logo/logo2_footer.png" alt=""></a>
                                        </div>
                                        <div class="footer-tittle">
                                            <div class="footer-pera">
                                                <p>The automated process starts as soon as your clothes go into the machine.</p>
                                            </div>
                                        </div>
                                        <!-- social -->
                                        <div class="footer-social">
                                            <a href="#"><i class="fab fa-twitter"></i></a>
                                            <a href="https://bit.ly/sai4ull"><i class="fab fa-facebook-f"></i></a>
                                            <a href="#"><i class="fab fa-pinterest-p"></i></a>
                                        </div>
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
                </div>
                <!-- footer-bottom area -->
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

        <!-- Scroll Up -->
        <div id="back-top">
            <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
        </div>

        <!-- JS here -->
        <script src="./assets/js/vendor/modernizr-3.5.0.min.js"></script>
        <script src="./assets/js/vendor/jquery-1.12.4.min.js"></script>
        <script src="./assets/js/popper.min.js"></script>
        <script src="./assets/js/bootstrap.min.js"></script>
        <script src="./assets/js/jquery.slicknav.min.js"></script>
        <script src="./assets/js/owl.carousel.min.js"></script>
        <script src="./assets/js/slick.min.js"></script>
        <script src="./assets/js/wow.min.js"></script>
        <script src="./assets/js/animated.headline.js"></script>
        <script src="./assets/js/jquery.magnific-popup.js"></script>
        <script src="./assets/js/gijgo.min.js"></script>
        <script src="./assets/js/jquery.nice-select.min.js"></script>
        <script src="./assets/js/jquery.sticky.js"></script>
        <script src="./assets/js/jquery.barfiller.js"></script>
        <script src="./assets/js/jquery.counterup.min.js"></script>
        <script src="./assets/js/waypoints.min.js"></script>
        <script src="./assets/js/jquery.countdown.min.js"></script>
        <script src="./assets/js/hover-direction-snake.min.js"></script>
        <script src="./assets/js/contact.js"></script>
        <script src="./assets/js/jquery.form.js"></script>
        <script src="./assets/js/jquery.validate.min.js"></script>
        <script src="./assets/js/mail-script.js"></script>
        <script src="./assets/js/jquery.ajaxchimp.min.js"></script>
        <script src="./assets/js/plugins.js"></script>
        <script src="./assets/js/main.js"></script>
        <script>
                                            document.addEventListener('DOMContentLoaded', function () {
                                                const navLinks = document.querySelectorAll('#sidebarNav .nav-link');
                                                const sections = document.querySelectorAll('.management-section');

                                                // Hide all sections by default
                                                sections.forEach(section => section.style.display = 'none');

                                                navLinks.forEach(link => {
                                                    link.addEventListener('click', function (e) {
                                                        e.preventDefault();
                                                        const targetId = this.getAttribute('href').substring(1);

                                                        // Remove active class from all links
                                                        navLinks.forEach(l => l.classList.remove('active'));
                                                        // Add active class to clicked link
                                                        this.classList.add('active');

                                                        // Hide all sections
                                                        sections.forEach(section => section.style.display = 'none');
                                                        // Show the target section
                                                        document.getElementById(targetId).style.display = 'block';
                                                    });
                                                });

                                                // Set initial active section to #courses by default
                                                const initialSection = window.location.hash || '';
                                                if (initialSection) {
                                                    document.querySelector(`#sidebarNav a[href="${initialSection}"]`).classList.add('active');
                                                    document.getElementById(initialSection.substring(1)).style.display = 'block';
                                                } else {
                                                    // Mặc định hiển thị section courses nếu không có hash
                                                    document.querySelector('#sidebarNav a[href="#courses"]').classList.add('active');
                                                    document.getElementById('courses').style.display = 'block';
                                                }
                                            });

        </script>


    </body>

</html>