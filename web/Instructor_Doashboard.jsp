<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Instructor Dashboard | Online Learning</title>
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
        .management-section {
            margin-bottom: 40px;
            display: none;
        }
        .dashboard-card {
            background: #ffffff;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
        .dashboard-card h4 {
            color: #007bff;
            font-weight: 600;
            margin-bottom: 10px;
        }
        .dashboard-card p {
            color: #343a40;
            font-size: 1.5rem;
            font-weight: 700;
        }
        .dashboard-card i {
            font-size: 2rem;
            color: #007bff;
            margin-bottom: 10px;
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
            .dashboard-card {
                margin-bottom: 15px;
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
                                                <li><a href="logout.jsp">Logout</a></li>
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
                                <li class="nav-item"><a href="#overview" class="nav-link active">Overview</a></li>
                                <li class="nav-item"><a href="listCourses" class="nav-link">Courses</a></li>
                                <li class="nav-item"><a href="listBlogsInstructor" class="nav-link">Blogs</a></li>
                                <li class="nav-item"><a href="balance" class="nav-link">Balance</a></li>
                                <li class="nav-item"><a href="listReviews" class="nav-link">Reviews</a></li>
                           
                            </ul>
                        </div>
                        <!-- Main Content -->
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Seller Dashboard</h2>
                            <p>Welcome, [Seller Name]. Get an overview of your activities here.</p>
                            <!-- Overview Section -->
                            <div id="overview" class="management-section">
                                <h3>Overview</h3>
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="dashboard-card">
                                            <i class="fas fa-book"></i>
                                            <h4>Total Courses</h4>
                                            <p>0</p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="dashboard-card">
                                            <i class="fas fa-blog"></i>
                                            <h4>Total Blogs</h4>
                                            <p>0</p>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="dashboard-card">
                                            <i class="fas fa-chart-line"></i>
                                            <h4>Performance</h4>
                                            <p>0 Views</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <div class="col-md-6">
                                        <div class="dashboard-card">
                                            <h4>Recent Activity</h4>
                                            <p>No recent activity available.</p>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="dashboard-card">
<!--                                            <h4>Quick Actions</h4>-->
<!--                                            <a href="blog_course_form.jsp?type=course&action=create" class="btn btn-primary mb-2">Create New Course</a>
                                            <a href="seller_blog.jsp" class="btn btn-primary">Create New Blog</a>-->
                                        </div>
                                    </div>
                                </div>
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
                document.addEventListener('DOMContentLoaded', function () {
                    const navLinks = document.querySelectorAll('#sidebarNav .nav-link');
                    const sections = document.querySelectorAll('.management-section');
                    sections.forEach(section => section.style.display = 'none');
                    navLinks.forEach(link => {
                        link.addEventListener('click', function (e) {
                            const href = this.getAttribute('href');
                            if (!href.startsWith('#')) return; // Allow navigation to external pages like listCousera, seller_blog.jsp, balance.jsp, reviews.jsp
                            e.preventDefault();
                            const targetId = href.substring(1);
                            navLinks.forEach(l => l.classList.remove('active'));
                            this.classList.add('active');
                            sections.forEach(section => section.style.display = 'none');
                            const section = document.getElementById(targetId);
                            if (section) {
                                section.style.display = 'block';
                            }
                            // Update URL with hash
                            history.pushState({}, '', `#${targetId}`);
                        });
                    });
                    const initialSection = window.location.hash;
                    if (initialSection && document.querySelector(initialSection)) {
                        const link = document.querySelector(`#sidebarNav a[href="${initialSection}"]`);
                        const section = document.querySelector(initialSection);
                        if (link && section) {
                            link.classList.add('active');
                            section.style.display = 'block';
                        }
                    } else {
                        document.querySelector('#sidebarNav a[href="#overview"]').classList.add('active');
                        document.getElementById('overview').style.display = 'block';
                    }
                });
            </script>
</body>
</html>