<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Instructor Dashboard | Online Learning</title>
    <meta name="description" content="Instructor dashboard for managing courses, lessons, quizzes, and blogs">
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
        .dashboard-card {
            background: #ffffff;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
            border-left: 4px solid #007bff;
            text-align: center;
            height: 100%; /* Đảm bảo card đồng đều chiều cao */
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
        .dashboard-card h4 {
            color: #007bff;
            font-weight: 600;
            margin-bottom: 10px;
        }
        .dashboard-card .card-number {
            color: #343a40;
            font-size: 2rem;
            font-weight: 700;
            word-wrap: break-word; /* Xử lý số liệu dài */
        }
        .dashboard-card i {
            font-size: 2.5rem;
            color: #007bff;
            margin-bottom: 15px;
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
        .btn-success {
            background-color: #48bb78;
            border-color: #48bb78;
        }
        .btn-info {
            background-color: #4299e1;
            border-color: #4299e1;
        }
        .btn-warning {
            background-color: #ed8936;
            border-color: #ed8936;
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
        .quick-action-card {
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            text-align: center;
        }
        .quick-action-card h4 {
            color: white;
            margin-bottom: 15px;
        }
        .quick-action-card .btn {
            margin: 5px;
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
            .dashboard-card {
                height: auto; /* Điều chỉnh chiều cao trên màn hình nhỏ */
            }
        }
        @media (max-width: 767px) {
            .dashboard-card {
                margin-bottom: 15px;
                height: auto;
            }
            .row > [class*="col-"] {
                flex: 0 0 100%; /* Mỗi card chiếm toàn bộ chiều rộng trên mobile */
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
                                                <li class="nav-item"><a href="profile" class="nav-link active">Profile</a></li>
                                                <li><a href="DashBoard">Dashboard</a></li>
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
        </div>
    </header>
    <main>
        <section class="dashboard-area section-padding40">
            <div class="container-fluid">
                <div class="row">
                    <!-- Sidebar -->
                    <div class="col-lg-3 col-md-4 sidebar">
                        <ul class="nav flex-column" id="sidebarNav">
                         
                            <li class="nav-item"><a href="DashBoard" class="nav-link active">Overview</a></li>
                            <li class="nav-item"><a href="listCourses" class="nav-link">Courses</a></li>
                            <li class="nav-item"><a href="instructorvideoquiz" class="nav-link">Video Quiz</a></li>
                            <li class="nav-item"><a href="listBlogsInstructor" class="nav-link">Blogs</a></li>
                            <li class="nav-item"><a href="balance" class="nav-link">Order</a></li>
                            <li class="nav-item"><a href="listReviews" class="nav-link">Reviews</a></li>
                        </ul>
                    </div>
                    <!-- Main Content -->
                    <div class="col-lg-9 col-md-8 content">
                        <h2><i class="fas fa-chalkboard-teacher"></i> Instructor Dashboard</h2>
                        <p>Welcome, <strong>${user.firstName} ${user.lastName}</strong>. Manage your courses, lessons, and content here.</p>
                      
                        <!-- KPI Widgets Section -->
                        <div class="management-section">
                            <h3>Dashboard Overview</h3>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-book"></i>
                                        <h4>Total Courses</h4>
                                        <div class="card-number">${totalCourses}</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-play-circle"></i>
                                        <h4>Total Lessons</h4>
                                        <div class="card-number">${totalLessons}</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-question-circle"></i>
                                        <h4>Total Quizzes</h4>
                                        <div class="card-number">${totalQuizzes}</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-blog"></i>
                                        <h4>Total Blogs</h4>
                                        <div class="card-number">${totalBlogs}</div>
                                    </div>
                                </div>
                            </div>
                          
                            <div class="row mt-4">
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-users"></i>
                                        <h4>Total Customers</h4>
                                        <div class="card-number">${totalStudents}</div>
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-shopping-cart"></i>
                                        <h4>Total Orders</h4>
                                        <div class="card-number">${totalOrders}</div>
                                    </div>
                                </div>
                               
                                <div class="col-md-3">
                                    <div class="dashboard-card">
                                        <i class="fas fa-dollar-sign"></i>
                                        <h4>Total Revenue</h4>
                                        <div class="card-number">
                                            <fmt:formatNumber value="${revenueThisMonth >= 1000000 ? revenueThisMonth / 1000000 : revenueThisMonth}" 
                                                              maxFractionDigits="1" /> 
                                            <c:if test="${revenueThisMonth >= 1000000}">M</c:if>
                                            <c:if test="${revenueThisMonth < 1000000}">$</c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                          
                                        
                            <!-- Recent Activity -->
                            <div class="row mt-4">
                                <div class="col-md-6">
                                    <div class="dashboard-card">
                                        <h4><i class="fas fa-clock"></i> Recent Activity</h4>
                                        <c:choose>
                                            <c:when test="${not empty recentActivities}">
                                                <c:forEach var="activity" items="${recentActivities}">
                                                    <p><small class="text-muted">${activity}</small></p>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <p class="text-muted">No recent activity available.</p>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="dashboard-card">
                                        <h4><i class="fas fa-chart-line"></i> Performance Summary</h4>
                                        <p><strong>This Month (Aug 2025):</strong></p>
                                        <p><i class="fas fa-user-plus text-success"></i> New Customers: ${newStudentsThisMonth}</p>
                                        <p><i class="fas fa-dollar-sign text-info"></i> Revenue: $<fmt:formatNumber value="${revenueThisMonth}" maxFractionDigits="2" /></p>
                                        <p><i class="fas fa-comments text-warning"></i> New Reviews: ${newReviewsThisMonth}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
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
                                        <p>Empowering instructors to create amazing learning experiences for customers worldwide.</p>
                                    </div>
                                </div>
                                <div class="footer-social">
                                    <a href="#"><i class="fab fa-twitter"></i></a>
                                    <a href="#"><i class="fab fa-facebook-f"></i></a>
                                    <a href="#"><i class="fab fa-pinterest-p"></i></a>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-3 col-md-4 col-sm-5">
                            <div class="single-footer-caption mb-50">
                                <div class="footer-tittle">
                                    <h4>For Instructors</h4>
                                    <ul>
                                        <li><a href="#">Create Courses</a></li>
                                        <li><a href="#">Manage Content</a></li>
                                        <li><a href="#">Track Performance</a></li>
                                        <li><a href="#">Customer Analytics</a></li>
                                        <li><a href="#">Revenue Reports</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-4 col-md-4 col-sm-6">
                            <div class="single-footer-caption mb-50">
                                <div class="footer-tittle">
                                    <h4>Support</h4>
                                    <ul>
                                        <li><a href="#">Help Center</a></li>
                                        <li><a href="#">Teaching Guide</a></li>
                                        <li><a href="#">Best Practices</a></li>
                                        <li><a href="#">Community Forum</a></li>
                                        <li><a href="#">Contact Support</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-xl-2 col-lg-3 col-md-4 col-sm-6">
                            <div class="single-footer-caption mb-50">
                                <div class="footer-tittle">
                                    <h4>Resources</h4>
                                    <ul>
                                        <li><a href="#">Teaching Tools</a></li>
                                        <li><a href="#">Video Guidelines</a></li>
                                        <li><a href="#">Course Templates</a></li>
                                        <li><a href="#">Marketing Tips</a></li>
                                        <li><a href="#">Success Stories</a></li>
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
                                        <p>Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | Instructor Dashboard</p>
                                    </div>
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