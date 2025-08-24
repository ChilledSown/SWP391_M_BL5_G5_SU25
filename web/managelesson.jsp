<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html class="no-js" lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Course Management</title>
    <link rel="manifest" href="assets/img/site.webmanifest">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

    <!-- CSS từ index.jsp -->
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
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

    <!-- Custom CSS -->
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
            overflow-x: hidden;
        }
        .dashboard-container {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 238px;
            background-color: #2c3e50;
            color: white;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
            position: fixed;
            height: 100%;
            top: 0;
            left: 0;
            z-index: 1000;
        }
        .sidebar-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .sidebar-header h3 {
            color: white; 
        }
        .sidebar-nav ul {
            list-style-type: none;
            padding: 0;
        }
        .sidebar-nav li {
            margin-bottom: 10px;
            transition: background-color 0.3s ease;
            border-radius: 5px;
        }
        .sidebar-nav li:hover {
            background-color: #34495e;
        }
        .sidebar-nav li.active {
            background-color: #3498db;
        }
        .sidebar-nav a {
            display: block;
            padding: 15px 20px;
            color: white;
            text-decoration: none;
            font-size: 16px;
            transition: color 0.3s ease;
        }
        .sidebar-nav a:hover {
            color: #ecf0f1;
        }
        .main-content {
            flex-grow: 1;
            padding: 20px;
            margin-left: 250px; /* Độ rộng sidebar cố định */
            transition: margin-left 0.3s ease-in-out;
        }
        .profile-section {
            text-align: center;
            margin-bottom: 30px;
        }
        .profile-avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            border: 2px solid #3498db;
        }
        .profile-name {
            margin: 10px 0;
            font-size: 18px;
            font-weight: 500;
        }
        .edit-profile-btn {
            background-color: transparent;
            border: none;
            color: #3498db;
            font-size: 16px;
            cursor: pointer;
            transition: color 0.3s ease;
            position: relative;
            top: -58px;
            left: 30px;
        }
        .edit-profile-btn:hover {
            color: #2980b9;
        }
        /* CSS cho các section từ index.jsp */
        .section-tittle {
            text-align: center;
            margin-bottom: 55px;
        }
        .section-tittle h2 {
            font-size: 2rem;
            font-weight: 700;
        }
        .services-area, .topic-area {
            padding: 80px 0;
            background: #f8f9fa;
        }
        .single-services, .single-topic {
            text-align: center;
            transition: all 0.3s ease;
        }
        .single-services:hover, .single-topic:hover {
            transform: translateY(-5px);
        }
        .topic-img {
            position: relative;
            overflow: hidden;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .topic-img img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            transition: transform 0.3s ease;
        }
        .single-topic:hover .topic-img img {
            transform: scale(1.05);
        }
        .topic-content-box {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background: linear-gradient(transparent, rgba(0,0,0,0.7));
            padding: 20px;
            color: white;
        }
        .topic-content h3 {
            margin: 0;
            font-size: 18px;
            font-weight: 600;
        }
        .topic-content h3 a {
            color: white;
            text-decoration: none;
            transition: color 0.3s ease;
        }
        .topic-content h3 a:hover {
            color: #667eea;
        }
        .border-btn {
            display: inline-block;
            padding: 15px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 25px;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
        }
        .border-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
            color: white;
            text-decoration: none;
        }
        /* CSS cho slider từ index.jsp */
        .slider-area {
            position: relative;
            height: 500px;
            overflow: hidden;
            background: #f8f9fa;
            margin: 0;
            width: 100%;
        }
        .single-slider {
            height: 100%;
            background-size: cover;
            background-position: center;
        }
        .hero__caption {
            text-align: center;
            color: white;
            padding: 50px 20px;
            background: rgba(0, 0, 0, 0.5);
        }
        .hero__caption h1 {
            font-size: 3rem;
            font-weight: 700;
        }
        .hero__caption p {
            font-size: 1.2rem;
            margin: 20px 0;
        }
        .hero-btn {
            background: #667eea;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
        }
        @media (max-width: 768px) {
            .sidebar {
                width: 200px;
            }
            .main-content {
                margin-left: 200px;
            }
            .hero__caption h1 {
                font-size: 2rem;
            }
            .hero__caption p {
                font-size: 1rem;
            }
        }
        /* Đảm bảo footer chạm sát aside */
        header.header-area {
            margin-left: 250px;
            width: calc(100% - 250px);
            position: relative;
            z-index: 900;
        }
        footer {
            margin-left: 238px; /* Chính xác bằng độ rộng của sidebar */
            width: calc(100% - 238px); /* Loại bỏ khoảng cách thừa */
            position: relative;
            z-index: 900;
            background-color: #f4f7f9; /* Đảm bảo nền footer khớp với body */
        }
    </style>
</head>
<body>
    <!-- Preloader từ index.jsp -->
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

    <div class="dashboard-container">
        <!-- Sidebar giữ nguyên -->
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>Admin Dashboard</h3>
            </div>
            <div class="profile-section">
                <img src="${empty sessionScope.user.avataUrl ? 'assets/img/default-avatar.png' : sessionScope.user.avataUrl}" alt="Avatar" class="profile-avatar">
                <div class="profile-name">
                    <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName} ${sessionScope.user.lastName}"/>
                </div>
                <a href="${pageContext.request.contextPath}/profile"><button class="edit-profile-btn" title="Edit Profile"><i class="fas fa-edit"></i></button></a>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li data-section="overview">
                        <a href="overview">Overview</a>
                    </li>
                    <li data-section="courses">
                        <a href="managetopic">Manage Topic</a>
                    </li>
                    <li data-section="users">
                        <a href="manageuser">Manage Users</a>
                    </li>
                    <li data-section="settings">
                        <a href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </nav>
        </aside>

        <!-- Main content với thông tin từ index.jsp -->
        <main class="main-content">
            <!-- Slider Area từ index.jsp -->
            <section class="slider-area">
                <div class="slider-active">
                    <div class="single-slider slider-height d-flex align-items-center" style="background-image: url('assets/img/hero/h1_hero.png');">
                        <div class="container">
                            <div class="row">
                                <div class="col-12">
                                    <div class="hero__caption">
                                        <h1 data-animation="fadeInLeft" data-delay="0.2s">Online learning platform</h1>
                                        <p data-animation="fadeInLeft" data-delay="0.4s">Build skills with courses, certificates, and degrees online from world-class universities and companies</p>
                                        <a href="#" class="hero-btn" data-animation="fadeInLeft" data-delay="0.7s">Join for Free</a>
                                    </div>
                                </div>
                            </div>
                        </div>          
                    </div>
                </div>
            </section>

            <!-- Services Area từ index.jsp -->
            <div class="services-area">
                <div class="container">
                    <div class="row justify-content-sm-center">
                        <div class="col-lg-4 col-md-6 col-sm-8">
                            <div class="single-services mb-30">
                                <div class="features-icon">
                                    <img src="assets/img/icon/icon1.svg" alt="">
                                </div>
                                <div class="features-caption">
                                    <h3>60+ UX courses</h3>
                                    <p>The automated process all your website tasks.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-6 col-sm-8">
                            <div class="single-services mb-30">
                                <div class="features-icon">
                                    <img src="assets/img/icon/icon2.svg" alt="">
                                </div>
                                <div class="features-caption">
                                    <h3>Expert instructors</h3>
                                    <p>The automated process all your website tasks.</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-6 col-sm-8">
                            <div class="single-services mb-30">
                                <div class="features-icon">
                                    <img src="assets/img/icon/icon3.svg" alt="">
                                </div>
                                <div class="features-caption">
                                    <h3>Life time access</h3>
                                    <p>The automated process all your website tasks.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Topic Area từ index.jsp -->
            <div class="topic-area">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-xl-7 col-lg-8">
                            <div class="section-tittle text-center mb-55">
                                <h2>Explore our topic</h2>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <c:choose>
                            <c:when test="${not empty topics}">
                                <c:forEach var="topic" items="${topics}" varStatus="status">
                                    <c:if test="${status.index < 8}">
                                        <div class="col-lg-3 col-md-4 col-sm-6">
                                            <div class="single-topic text-center mb-30">
                                                <div class="topic-img">
                                                    <img src="${not empty topic.thumbnail_url ? topic.thumbnail_url : 'assets/img/gallery/topic' += (status.index + 1) += '.png'}" alt="${topic.name}">
                                                    <div class="topic-content-box">
                                                        <div class="topic-content">
                                                            <h3><a href="#">${topic.name}</a></h3>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="col-12 text-center">
                                    <p>No topics available.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="row justify-content-center">
                        <div class="col-xl-12">
                            <div class="section-tittle text-center mt-20">
                                <a href="courses" class="border-btn">View More Topics</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <!-- Footer từ index.jsp -->
    <footer>
        <div class="footer-wrappper footer-bg">
            <div class="footer-area footer-padding">
                <div class="container">
                    <div class="row justify-content-between">
                        <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                            <div class="single-footer-caption mb-50">
                                <div class="single-footer-caption mb-30">
                                    <div class="footer-logo mb-25">
                                        <a href="index.jsp"><img src="assets/img/logo/logo2_footer.png" alt=""></a>
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

    <!-- Scroll Up từ index.jsp -->
    <div id="back-top">
        <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
    </div>

    <!-- JS từ index.jsp -->
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
</body>
</html>