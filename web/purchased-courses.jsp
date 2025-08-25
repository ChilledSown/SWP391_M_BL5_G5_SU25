<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>My Purchased Courses | Education</title>
        <meta name="description" content="">
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

        <!-- Custom CSS for Purchased Courses -->
        <style>
            /* Combined Header Styling */
            .combined-header {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                position: relative;
                overflow: hidden;
                box-shadow: none;
            }

            .combined-header::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(255,255,255,0.1);
                backdrop-filter: blur(10px);
            }

            .header-top {
                padding: 20px 0;
                border-bottom: 1px solid rgba(255,255,255,0.2);
                position: relative;
                z-index: 2;
            }

            .page-header-content {
                padding: 60px 0;
                position: relative;
                z-index: 2;
                text-align: center;
                color: white;
            }

            .page-header-content h1 {
                font-size: 36px;
                font-weight: 700;
                margin-bottom: 15px;
                text-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }

            .page-header-content p {
                font-size: 18px;
                opacity: 0.9;
                margin: 0;
            }

            .stats-container {
                background: white;
                border-radius: 15px;
                padding: 30px;
                margin-bottom: 40px;
                box-shadow: none;
                width: 100%;
            }

            .stat-item {
                text-align: center;
                padding: 20px;
            }

            .stat-number {
                font-size: 32px;
                font-weight: 700;
                color: #667eea;
                margin-bottom: 8px;
            }

            .stat-label {
                color: #6c757d;
                font-size: 14px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
            }

            /* Course card uniform height styles - Compact version */
            .properties.properties2 {
                height: 100%;
                display: flex;
                flex-direction: column;
                background: white;
                border-radius: 12px;
                box-shadow: none;
                transition: all 0.3s ease;
                width: 100%;
            }

            .properties.properties2:hover {
                transform: translateY(-5px);
                box-shadow: none;
            }

            .properties__card {
                height: 100%;
                display: flex;
                flex-direction: column;
            }

            .properties__img {
                flex-shrink: 0;
                height: 180px;
                overflow: hidden;
                border-radius: 12px 12px 0 0;
            }

            .properties__img img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            .properties__caption {
                flex: 1;
                display: flex;
                flex-direction: column;
                padding: 20px;
                padding-bottom: 15px;
            }

            .properties__caption > p:first-child {
                color: #667eea;
            }

            .properties__caption h3 {
                min-height: 40px;
                max-height: 40px;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                overflow: hidden;
                text-overflow: ellipsis;
                margin-bottom: 12px;
                font-size: 18px;
                line-height: 1.3;
            }

            .properties__caption h3 a {
                color: #2c3e50;
                text-decoration: none;
                transition: color 0.3s ease;
            }

            .properties__caption h3 a:hover {
                color: #667eea;
            }

            .properties__caption p {
                color: #6c757d;
                font-size: 14px;
                line-height: 1.5;
                margin-bottom: 15px;
                flex: 1;
            }

            .properties__footer {
                margin-top: auto;
                padding-top: 15px;
                border-top: 1px solid #e9ecef;
            }

            .restaurant-name .rating {
                color: #ffc107;
                font-size: 14px;
            }

            .restaurant-name p {
                color: #6c757d;
                font-size: 12px;
                margin: 5px 0 0 0;
            }

            .price span {
                color: #667eea;
                font-weight: 700;
                font-size: 18px;
            }

            .border-btn {
                display: inline-block;
                padding: 12px 24px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                text-decoration: none;
                border-radius: 25px;
                font-weight: 600;
                transition: all 0.3s ease;
                text-align: center;
                margin-top: 15px;
            }

            .border-btn:hover {
                transform: translateY(-2px);
                box-shadow: none;
                color: white;
                text-decoration: none;
            }

            .no-courses {
                text-align: center;
                padding: 80px 20px;
                background: white;
                border-radius: 15px;
                box-shadow: none;
                width: 100%;
            }

            .no-courses i {
                font-size: 64px;
                color: #95a5a6;
                margin-bottom: 20px;
            }

            .no-courses h3 {
                color: #2c3e50;
                margin-bottom: 15px;
                font-weight: 600;
            }

            .no-courses p {
                color: #7f8c8d;
                margin-bottom: 25px;
                font-size: 16px;
            }

            .btn-explore {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 10px 20px;
                border-radius: 12px;
                text-decoration: none;
                font-weight: 600;
                font-size: 14px;
                line-height: 1.2;
                transition: all 0.2s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                gap: 8px;
            }

            .btn-explore:hover {
                transform: translateY(-1px);
                color: white;
                text-decoration: none;
                filter: brightness(1.03);
            }

            .course-topic .badge {
                font-size: 12px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 1px;
                margin-bottom: 8px;
                background: #e3f2fd;
                color: #1976d2;
            }

            /* Navigation Styling for Combined Header */
            #navigation {
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 0;
                margin: 0;
                padding: 0;
                list-style: none;
            }

            #navigation li {
                margin: 0;
                padding: 0;
                display: flex;
                align-items: center;
            }

            #navigation li a {
                color: white !important;
                font-weight: 500;
                font-size: 16px;
                text-decoration: none;
                padding: 12px 20px;
                border-radius: 8px;
                transition: all 0.3s ease;
                display: block;
                position: relative;
                margin: 0 5px;
            }

            #navigation li a:hover,
            #navigation li a.active {
                color: #667eea !important;
                background: rgba(255, 255, 255, 0.2);
                backdrop-filter: blur(10px);
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
            }

            #navigation li a::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 50%;
                width: 0;
                height: 2px;
                background: white;
                transition: all 0.3s ease;
                transform: translateX(-50%);
            }

            #navigation li a:hover::after,
            #navigation li a.active::after {
                width: 80%;
            }

            .logo a {
                filter: brightness(1.2);
            }

            .logo img {
                max-height: 40px;
            }

            @media (max-width: 768px) {
                .combined-header {
                    margin-bottom: 30px;
                }

                .header-top {
                    padding: 15px 0;
                }

                .page-header-content {
                    padding: 40px 0;
                }

                .page-header-content h1 {
                    font-size: 28px;
                }

                .page-header-content p {
                    font-size: 16px;
                }

                .stat-number {
                    font-size: 24px;
                }

                .properties__caption {
                    padding: 15px;
                }

                .logo img {
                    max-height: 35px;
                }
            }

            /* Disable hover effects for non-button nav links */
            #navigation li a:not(.btn):hover {
                color: white !important;
                background: transparent !important;
                transform: none !important;
                box-shadow: none !important;
                backdrop-filter: none !important;
            }

            #navigation li a:not(.btn):hover::after {
                width: 0 !important;
            }
        </style>
    </head>

    <body>
        <!-- Combined Header & Page Header -->
        <div class="combined-header">
            <div class="header-top">
                <div class="container">
                    <div class="row align-items-center">
                        <div class="col-xl-2 col-lg-2 col-md-2">
                            <div class="logo">
                                <a href="home"><img src="assets/img/logo/logo.png" alt=""></a>
                            </div>
                        </div>
                        <div class="col-xl-10 col-lg-10 col-md-10">
                            <div class="menu-wrapper d-flex align-items-center justify-content-end">
                                <div class="main-menu d-none d-lg-block">
                                    <nav>
                                        <ul id="navigation">                                                                                          
                                            <li class="active" ><a href="home">Home</a></li>
                                            <li><a href="courses">Courses</a></li>
                                            <li><a href="purchased-courses">Purchased courses</a></li>
                                            <li><a href="blog">Blog</a></li>
                                            <li><a href="cart">Cart</a></li>
                                            <li><a href="customer-list-order">My Order</a></li>
                                            <li><a href="profile" class="btn">Profile</a></li>
                                            <li><a href="${pageContext.request.contextPath}/logout" class="btn">Logout</a></li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="page-header-content">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-lg-8 text-center">
                            <h1>My Purchased Courses</h1>
                            <p>Access all the courses you've purchased and continue your learning journey</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Stats Section -->
        <div class="container">
            <div class="stats-container">
                <div class="row">
                    <div class="col-md-4">
                        <div class="stat-item">
                            <div class="stat-number">${fn:length(courses)}</div>
                            <div class="stat-label">Total Courses</div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-item">
                            <div class="stat-number">
                                <c:set var="totalValue" value="0" />
                                <c:forEach items="${courses}" var="course">
                                    <c:set var="totalValue" value="${totalValue + course.price}" />
                                </c:forEach>
                                $${totalValue}
                            </div>
                            <div class="stat-label">Total Value</div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="stat-item">
                            <div class="stat-number">
                                <c:set var="avgRating" value="0" />
                                <c:set var="ratingCount" value="0" />
                                <c:forEach items="${courses}" var="course">
                                    <c:if test="${course.averageRating > 0}">
                                        <c:set var="avgRating" value="${avgRating + course.averageRating}" />
                                        <c:set var="ratingCount" value="${ratingCount + 1}" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${ratingCount > 0}">
                                    <c:out value="${String.format('%.1f', avgRating / ratingCount)}" />
                                </c:if>
                                <c:if test="${ratingCount == 0}">0.0</c:if>
                                </div>
                                <div class="stat-label">Average Rating</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Courses area Start -->
            <div class="courses-area section-padding40">
                <div class="container">
                    <div class="row" id="courses-list">
                    <c:choose>
                        <c:when test="${empty courses}">
                            <div class="col-12">
                                <div class="no-courses">
                                    <i class="fas fa-graduation-cap"></i>
                                    <h3>No courses purchased yet</h3>
                                    <p>You haven't purchased any courses yet. Start your learning journey by exploring our course catalog.</p>
                                    <a href="courses" class="btn-explore">
                                        <i class="fas fa-search"></i> Explore Courses
                                    </a>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="course" items="${courses}">
                                <div class="col-lg-4 col-md-6 mb-30">
                                    <div class="properties properties2">
                                        <div class="properties__card">
                                            <div class="properties__img overlay1">
                                                <a href="customer-course-detail?id=${course.course_id}">
                                                    <img src="<c:out value="${empty course.thumbnail_url ? 'assets/img/gallery/featured1.png' : course.thumbnail_url}" />" alt="${course.title}">
                                                </a>
                                            </div>
                                            <div class="properties__caption">
                                                <h3>
                                                    <a href="customer-course-detail?id=${course.course_id}">${course.title}</a>
                                                </h3>
                                                <p>
                                                    <c:choose>
                                                        <c:when test="${empty course.description}">
                                                            No description available
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${fn:length(course.description) > 100}">
                                                                    ${fn:substring(course.description, 0, 100)}...
                                                                </c:when>
                                                                <c:otherwise>
                                                                    ${course.description}
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </p>

                                                <div class="properties__footer d-flex justify-content-between align-items-center">
                                                    <div class="restaurant-name">
                                                        <div class="rating">
                                                            <c:set var="rating" value="${course.averageRating}" />
                                                            <c:forEach begin="1" end="5" var="i">
                                                                <c:choose>
                                                                    <c:when test="${i <= rating}">
                                                                        <i class="fas fa-star"></i>
                                                                    </c:when>
                                                                    <c:when test="${(i - 0.5) <= rating and rating < i}">
                                                                        <i class="fas fa-star-half"></i>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <i class="far fa-star"></i>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </div>
                                                        <p><span>(${rating})</span> rating</p>
                                                    </div>
                                                    <div class="price">
                                                        <span>$${course.price}</span>
                                                    </div>
                                                </div>
                                                <a href="customer-course-detail?id=${course.course_id}" class="border-btn">Continue Learning</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <!-- Courses area End -->

        <!-- Footer Start -->
        <footer>
            <div class="footer-wrappper footer-bg">
                <div class="footer-area footer-padding">
                    <div class="container">
                        <div class="row justify-content-between">
                            <div class="col-xl-4 col-lg-5 col-sm-6">
                                <div class="single-footer-caption">
                                    <div class="single-footer-caption">
                                        <div class="footer-logo">
                                            <a href="home"><img src="assets/img/logo/logo2_footer.png" alt=""></a>
                                        </div>
                                        <div class="footer-tittle">
                                            <div class="footer-pera">
                                                <p>Your gateway to knowledge and skill development. Start your learning journey today.</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-4 col-lg-4 col-sm-6">
                                <div class="single-footer-caption">
                                    <div class="footer-tittle">
                                        <h4>Quick Links</h4>
                                        <ul>
                                            <li><a href="home">Home</a></li>
                                            <li><a href="courses">Courses</a></li>
                                            <li><a href="purchased-courses">My Courses</a></li>
                                            <li><a href="cart">Cart</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-4 col-lg-3 col-sm-6">
                                <div class="single-footer-caption">
                                    <div class="footer-tittle">
                                        <h4>Contact Info</h4>
                                        <ul>
                                            <li><a href="#">support@onlinelearning.com</a></li>
                                            <li><a href="#">+1 234 567 8900</a></li>
                                            <li><a href="#">123 Learning Street, Education City</a></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Footer End -->

        <!-- JS here -->
        <script src="assets/js/vendor/modernizr-3.5.0.min.js"></script>
        <script src="assets/js/vendor/jquery-1.12.4.min.js"></script>
        <script src="assets/js/popper.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/owl.carousel.min.js"></script>
        <script src="assets/js/slick.min.js"></script>
        <script src="assets/js/jquery.slicknav.min.js"></script>
        <script src="assets/js/wow.min.js"></script>
        <script src="assets/js/animated.headline.js"></script>
        <script src="assets/js/jquery.magnific-popup.js"></script>
        <script src="assets/js/gijgo.min.js"></script>
        <script src="assets/js/jquery.nice-select.min.js"></script>
        <script src="assets/js/jquery.sticky.js"></script>
        <script src="assets/js/jquery.barfiller.js"></script>
        <script src="assets/js/jquery.counterup.min.js"></script>
        <script src="assets/js/waypoints.min.js"></script>
        <script src="assets/js/jquery.countdown.min.js"></script>
        <script src="assets/js/hover-direction-snake.min.js"></script>
        <script src="assets/js/contact.js"></script>
        <script src="assets/js/jquery.form.js"></script>
        <script src="assets/js/jquery.validate.min.js"></script>
        <script src="assets/js/mail-script.js"></script>
        <script src="assets/js/jquery.ajaxchimp.min.js"></script>
        <script src="assets/js/plugins.js"></script>
        <script src="assets/js/main.js"></script>
    </body>
</html>


