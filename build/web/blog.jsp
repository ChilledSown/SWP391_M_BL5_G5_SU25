<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Blog | Education</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

    <!-- CSS here -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="assets/css/slicknav.css">
    <link rel="stylesheet" href="assets/css/animate.min.css">
    <link rel="stylesheet" href="assets/css/hamburgers.min.css">
    <link rel="stylesheet" href="assets/css/magnific-popup.css">
    <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/css/themify-icons.css">
    <link rel="stylesheet" href="assets/css/slick.css">
    <link rel="stylesheet" href="assets/css/nice-select.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/responsive.css">

    <!-- Custom CSS -->
    <style>
        .blog-area {
            padding: 40px 0;
        }
        .properties.properties2 {
            height: 100%;
            display: flex;
            flex-direction: column;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
        }
        .properties.properties2:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
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
            flex: 1;
            min-height: 45px;
            max-height: 45px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
            text-overflow: ellipsis;
            margin-bottom: 15px;
            font-size: 14px;
            line-height: 1.4;
            color: #6c757d;
        }
        .properties__footer {
            flex-shrink: 0;
            margin-bottom: 15px;
            padding: 0;
        }
        .properties__caption .border-btn {
            flex-shrink: 0;
            margin-top: auto;
            padding: 8px 20px;
            font-size: 14px;
            border-radius: 8px;
            text-decoration: none;
            text-align: center;
            transition: all 0.3s ease;
        }
        .row .col-lg-4 {
            display: flex;
            margin-bottom: 25px;
        }
        .row .col-lg-4 .properties.properties2 {
            width: 100%;
        }
        .blog-meta {
            font-size: 12px;
            color: #6c757d;
            margin: 0;
        }
        .blog-meta span {
            margin-right: 15px;
        }
        .search-form {
            margin-bottom: 30px;
            text-align: center;
        }
        .search-form input {
            padding: 10px;
            width: 300px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .search-form button {
            padding: 10px 20px;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            margin-left: 10px;
        }
        .search-form .search-btn {
            background: #667eea;
            color: white;
        }
        .search-form .reset-btn {
            background: #6c757d;
            color: white;
        }
        .message.error {
            color: red;
            text-align: center;
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
    <!-- Header Start -->
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
                                        <li><a href="home">Home</a></li>
                                        <li><a href="courses">Courses</a></li>
                                        <li><a href="purchased-courses">Purchased courses</a></li>
                                        <li><a href="blog">Blog</a></li>
                                        <li class="active"><a href="cart">Cart</a></li>
                                        <li><a href="profile" class="btn">Profile</a></li>
                                        <li><a href="${pageContext.request.contextPath}/logout" class="btn">Logout</a></li>
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
    <!-- Header End -->
    <main>
        <!-- Slider Area Start -->
        <section class="slider-area slider-area2">
            <div class="slider-active">
                <div class="single-slider slider-height2">
                    <div class="container">
                        <div class="row">
                            <div class="col-xl-8 col-lg-11 col-md-12">
                                <div class="hero__caption hero__caption2">
                                    <h1 data-animation="bounceIn" data-delay="0.2s">Our Blogs</h1>
                                    <nav aria-label="breadcrumb">
                                        <ol class="breadcrumb">
                                            <li class="breadcrumb-item"><a href="home">Home</a></li>
                                            <li class="breadcrumb-item"><a href="blog">Blog</a></li>
                                        </ol>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        
        <!-- Blog Area Start -->
        <div class="blog-area section-padding40 fix">
            <div class="container">
                <div class="search-form">
                    <form action="blog" method="get">
                        <input type="text" name="search" value="${searchQuery}" placeholder="Search by title">
                        <button type="submit" class="search-btn">Search</button>
                        <button type="button" class="reset-btn" onclick="window.location.href='blog'">Reset</button>
                    </form>
                </div>
                <div class="row justify-content-center">
                    <div class="col-xl-7 col-lg-8">
                        <div class="section-tittle text-center mb-55">
                            <h2>Our Latest Blogs</h2>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty message}">
                    <p class="message error">${message}</p>
                </c:if>
                <div class="row" id="blogs-list">
                    <c:if test="${not empty blogs}">
                        <c:forEach var="blog" items="${blogs}">
                            <div class="col-lg-4">
                                <div class="properties properties2 mb-30">
                                    <div class="properties__card">
                                        <div class="properties__img overlay1">
                                            <a href="blog_details?id=${blog.blogId}">
                                                <img src="${empty blog.thumbnailUrl ? 'assets/img/blog/comment_1.png' : blog.thumbnailUrl}" alt="${blog.title}">
                                            </a>
                                        </div>
                                        <div class="properties__caption">
                                            <h3><a href="blog_details?id=${blog.blogId}">${blog.title}</a></h3>
                                            <p>
                                                <c:choose>
                                                    <c:when test="${empty blog.content}">
                                                        No content available
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${blog.content}
                                                    </c:otherwise>
                                                </c:choose>
                                            </p>
                                            <div class="properties__footer d-flex justify-content-between align-items-center">
                                                <div class="blog-meta">
                                                    <span><i class="fas fa-user"></i> ${blog.createdByName}</span>
                                                    <span><i class="fas fa-calendar-alt"></i> <fmt:formatDate value="${blog.createdAt}" pattern="dd/MM/yyyy HH:mm"/></span>
                                                </div>
                                            </div>
                                            <a href="blog_details?id=${blog.blogId}" class="border-btn border-btn2">Read More</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty blogs}">
                        <div class="col-12 text-center">
                            <p>No blogs found.</p>
                        </div>
                    </c:if>
                </div>
                <c:if test="${currentPage lt totalPages}">
                    <div class="text-center mt-30">
                        <button id="load-more" class="btn">Load More</button>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- Blog Area End -->
        
    </main>
    <footer>
        <div class="footer-wrappper footer-bg">
            <div class="footer-area footer-padding">
                <div class="container">
                    <div class="row justify-content-between">
                        <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                            <div class="single-footer-caption mb-50">
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
    <div id="back-top">
        <a title="Go to Top" href="#"><i class="fas fa-level-up-alt"></i></a>
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
        $(document).ready(function() {
            var currentPage = ${currentPage};
            var totalPages = ${totalPages};
            var searchQuery = '${searchQuery}';
            $('#load-more').click(function() {
                currentPage++;
                var url = 'blog?page=' + currentPage + '&ajax=true';
                if (searchQuery) {
                    url += '&search=' + encodeURIComponent(searchQuery);
                }
                $.ajax({
                    url: url,
                    type: 'GET',
                    success: function(data) {
                        var html = '';
                        data.forEach(function(blog) {
                            var thumbnail = blog.thumbnailUrl ? blog.thumbnailUrl : 'assets/img/blog/comment_1.png';
                            var content = blog.content ? blog.content : 'No content available';
                            var createdAt = new Date(blog.createdAt).toLocaleString('en-GB', {day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit'});
                            html += '<div class="col-lg-4">' +
                                    '<div class="properties properties2 mb-30">' +
                                    '<div class="properties__card">' +
                                    '<div class="properties__img overlay1">' +
                                    '<a href="blog_details?id=' + blog.blogId + '">' +
                                    '<img src="' + thumbnail + '" alt="' + blog.title + '">' +
                                    '</a>' +
                                    '</div>' +
                                    '<div class="properties__caption">' +
                                    '<h3><a href="blog_details?id=' + blog.blogId + '">' + blog.title + '</a></h3>' +
                                    '<p>' + content + '</p>' +
                                    '<div class="properties__footer d-flex justify-content-between align-items-center">' +
                                    '<div class="blog-meta">' +
                                    '<span><i class="fas fa-user"></i> ' + blog.createdByName + '</span>' +
                                    '<span><i class="fas fa-calendar-alt"></i> ' + createdAt + '</span>' +
                                    '</div>' +
                                    '</div>' +
                                    '<a href="blog_details?id=' + blog.blogId + '" class="border-btn border-btn2">Read More</a>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>' +
                                    '</div>';
                        });
                        $('#blogs-list').append(html);
                        if (currentPage >= totalPages) {
                            $('#load-more').hide();
                        }
                    },
                    error: function() {
                        alert('Error loading more blogs');
                    }
                });
            });
        });
    </script>
</body>
</html>