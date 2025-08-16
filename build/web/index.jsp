<%@ page import="java.util.*" %>
<%@ page import="model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Courses | Education</title>
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
    
    <!-- Custom CSS for Topic Section and Dynamic Slider -->
    <style>
        /* Dynamic Slider Styling */
        .dynamic-slider-area {
            position: relative;
            height: 600px;
            overflow: hidden;
            background: #000;
            margin: 40px 0;
        }
        
        /* Debug info - remove in production */
        .slider-debug {
            position: absolute;
            top: 10px;
            right: 10px;
            background: rgba(0,0,0,0.7);
            color: white;
            padding: 5px 10px;
            border-radius: 5px;
            font-size: 12px;
            z-index: 100;
        }
        
        .dynamic-slider-container {
            position: relative;
            width: 100%;
            height: 100%;
        }
        
        .dynamic-slider-wrapper {
            position: relative;
            width: 100%;
            height: 100%;
        }
        
        .dynamic-slider-slide {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            opacity: 0;
            transition: opacity 0.8s ease-in-out;
            z-index: 1;
        }
        
        .dynamic-slider-slide.active {
            opacity: 1;
            z-index: 2;
        }
        
        .dynamic-slider-image {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }
        
        .dynamic-slider-image img {
            width: 100%;
            height: 100%;
            object-fit: contain; /* show full image without cropping */
            background-color: #000; /* letterbox background */
            filter: brightness(0.9);
        }
        
        .dynamic-slider-content {
            position: relative;
            z-index: 3;
            height: 100%;
            display: flex;
            align-items: center; /* vertical center */
            justify-content: flex-start; /* left aligned */
            padding-left: 60px;
            padding-right: 60px;
        }
        
        .hero__caption h1 {
            color: white;
            font-size: 48px;
            font-weight: 700;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
        }
        
        .hero__caption p {
            color: rgba(255,255,255,0.9);
            font-size: 18px;
            margin-bottom: 30px;
            text-shadow: 1px 1px 2px rgba(0,0,0,0.5);
        }
        
        .hero-btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 30px;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.3);
            display: inline-block;
        }
        
        .hero-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
            color: white;
            text-decoration: none;
        }
        
        /* Navigation Arrows */
        .dynamic-slider-nav {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background: rgba(255,255,255,0.2);
            border: none;
            color: white;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: all 0.3s ease;
            z-index: 10;
            backdrop-filter: blur(10px);
        }
        
        .dynamic-slider-nav:hover {
            background: rgba(255,255,255,0.3);
            transform: translateY(-50%) scale(1.1);
        }
        
        .dynamic-slider-prev {
            left: 30px;
        }
        
        .dynamic-slider-next {
            right: 30px;
        }
        
        .dynamic-slider-nav i {
            font-size: 20px;
        }
        
        /* Indicators */
        .dynamic-slider-indicators {
            position: absolute;
            bottom: 30px;
            left: 50%;
            transform: translateX(-50%);
            display: flex;
            gap: 10px;
            z-index: 10;
        }
        
        .dynamic-indicator {
            width: 12px;
            height: 12px;
            border-radius: 50%;
            background: rgba(255,255,255,0.5);
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        .dynamic-indicator:hover {
            background: rgba(255,255,255,0.8);
            transform: scale(1.2);
        }
        
        .dynamic-indicator.active {
            background: white;
            transform: scale(1.2);
        }
        
        
        /* Topic styling */
        .topic-area {
            padding: 80px 0;
            background: #f8f9fa;
        }
        
        .single-topic {
            transition: all 0.3s ease;
        }
        
        .single-topic:hover {
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
    </style>
</head>

<body>
    <!-- ? Preloader Start -->
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
    <!-- Preloader Start -->
    <header>
        <!-- Header Start -->
        <div class="header-area header-transparent">
            <div class="main-header ">
                <div class="header-bottom  header-sticky">
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
                                                <li class="active" ><a href="home">Home</a></li>
                                                <li><a href="courses">Courses</a></li>
                                                <li><a href="about.jsp">About</a></li>
                                                <li><a href="blog">Blog</a>
                                                </li>
                                                <li><a href="contact.jsp">Contact</a></li>
                                                <!-- Button -->
                                                <li class="button-header margin-left "><a href="#" class="btn">Join</a></li>
                                                <li class="button-header"><a href="login.jsp" class="btn btn3">Log in</a></li>
                                                <li class="button-header"><a href="listCousera" class="btn btn3">Seller</a></li>
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
        <!-- Header End -->
    </header>
    <main>
        <!--? slider Area Start-->
        <section class="slider-area ">
            <div class="slider-active">
                <!-- Single Slider -->
                <div class="single-slider slider-height d-flex align-items-center">
                    <div class="container">
                        <div class="row">
                            <div class="col-xl-6 col-lg-7 col-md-12">
                                <div class="hero__caption">
                                    <h1 data-animation="fadeInLeft" data-delay="0.2s">Online learning<br> platform</h1>
                                    <p data-animation="fadeInLeft" data-delay="0.4s">Build skills with courses, certificates, and degrees online from world-class universities and companies</p>
                                    <a href="#" class="btn hero-btn" data-animation="fadeInLeft" data-delay="0.7s">Join for Free</a>
                                </div>
                            </div>
                        </div>
                    </div>          
                </div>
            </div>
        </section>
        <!-- ? services-area -->
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
        
        <!--? Dynamic Slider Area Start-->
        <section class="dynamic-slider-area">
            
            <div class="container-fluid p-0">
                <div class="dynamic-slider-container">
                    <div class="dynamic-slider-wrapper">
                        <c:choose>
                            <c:when test="${not empty sliders}">
                                <c:forEach var="slider" items="${sliders}" varStatus="status">
                                    <div class="dynamic-slider-slide ${status.index == 0 ? 'active' : ''}" data-index="${status.index}">
                                        <div class="dynamic-slider-image">
                                            <c:choose>
                                                <c:when test="${not empty slider.image_url}">
                                                    <c:choose>
                                                        <c:when test="${fn:startsWith(slider.image_url, 'http')}">
                                            <img src="${slider.image_url}" alt="${slider.title}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="<c:url value='${slider.image_url}'/>" alt="${slider.title}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="assets/img/hero/h1_hero.png" alt="${slider.title}">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="dynamic-slider-content">
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col-xl-6 col-lg-7 col-md-12">
                                                        <div class="hero__caption">
                                                            <h1 data-animation="fadeInLeft" data-delay="0.2s">${slider.title}</h1>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <!-- Fallback slider content when no data from database -->
                                <div class="dynamic-slider-slide active" data-index="0">
                                    <div class="dynamic-slider-image">
                                        <img src="assets/img/gallery/slider1.jpg" alt="Online Learning Platform">
                                    </div>
                                    <div class="dynamic-slider-content">
                                        <div class="container">
                                            <div class="row">
                                                <div class="col-xl-6 col-lg-7 col-md-12">
                                                    <div class="hero__caption">
                                                        <h1 data-animation="fadeInLeft" data-delay="0.2s">Online learning<br> platform</h1>
                                                        <p data-animation="fadeInLeft" data-delay="0.4s">Build skills with courses, certificates, and degrees online from world-class universities and companies</p>
                                                        <a href="#" class="btn hero-btn" data-animation="fadeInLeft" data-delay="0.7s">Join for Free</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <!-- Navigation Arrows -->
                    <button class="dynamic-slider-nav dynamic-slider-prev" onclick="changeSlide(-1)" aria-label="Previous slide">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <button class="dynamic-slider-nav dynamic-slider-next" onclick="changeSlide(1)" aria-label="Next slide">
                        <i class="fas fa-chevron-right"></i>
                    </button>
                    
                    <!-- Indicators -->
                    <div class="dynamic-slider-indicators">
                        <c:choose>
                            <c:when test="${not empty sliders}">
                                <c:forEach var="slider" items="${sliders}" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.index == 0}">
                                            <span class="dynamic-indicator active" onclick="goToSlide(<c:out value='${status.index}'/>)"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="dynamic-indicator" onclick="goToSlide(<c:out value='${status.index}'/>)"></span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span class="dynamic-indicator active" onclick="goToSlide(0)"></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </section>
        <!-- Dynamic Slider Area End -->
        
        <!-- Courses area start -->
        <div class="courses-area section-padding40 fix">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xl-7 col-lg-8">
                        <div class="section-tittle text-center mb-55">
                            <h2>Our latest courses</h2>
                        </div>
                    </div>
                </div>
                <div class="courses-actives">
                    <% if (request.getAttribute("latestCourses") != null) { %>
                        <% 
                        List<Course> latestCourses = (List<Course>) request.getAttribute("latestCourses");
                        for (Course course : latestCourses) {
                        %>
                        <!-- Single Course -->
                        <div class="properties pb-20">
                            <div class="properties__card">
                                <div class="properties__img overlay1">
                                    <a href="#"><img src="<%= course.getThumbnail_url() != null ? course.getThumbnail_url() : "assets/img/gallery/featured1.png" %>" alt="<%= course.getTitle() %>"></a>
                                </div>
                                <div class="properties__caption">
                                    <p>Course</p>
                                    <h3><a href="#"><%= course.getTitle() %></a></h3>
                                    <p><%= course.getDescription() != null ? course.getDescription() : "No description available" %></p>
                                    <div class="properties__footer d-flex justify-content-between align-items-center">
                                        <div class="restaurant-name">
                                            <div class="rating">
                                                <% 
                                                double rating = course.getAverageRating() != null ? course.getAverageRating() : 0.0;
                                                int fullStars = (int) rating;
                                                boolean hasHalfStar = rating % 1 >= 0.5;
                                                %>
                                                <% for (int i = 0; i < fullStars; i++) { %>
                                                    <i class="fa fa-star"></i>
                                                <% } %>
                                                <% if (hasHalfStar) { %>
                                                    <i class="fa fa-star-half-o"></i>
                                                <% } %>
                                                <% for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) { %>
                                                    <i class="fa fa-star-o"></i>
                                                <% } %>
                                            </div>
                                            <p><span>(<%= String.format("%.1f", rating) %>)</span> based on reviews</p>
                                        </div>
                                        <div class="price">
                                            <span>$<%= course.getPrice() %></span>
                                        </div>
                                    </div>
                                    <a href="#" class="border-btn border-btn2">Find out more</a>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    <% } else { %>
                        <!-- Fallback content if no courses are available -->
                        <div class="properties pb-20">
                            <div class="properties__card">
                                <div class="properties__img overlay1">
                                    <a href="#"><img src="assets/img/gallery/featured1.png" alt=""></a>
                                </div>
                                <div class="properties__caption">
                                    <p>User Experience</p>
                                    <h3><a href="#">Fundamental of UX for Application design</a></h3>
                                    <p>The automated process all your website tasks. Discover tools and techniques to engage effectively with vulnerable children and young people.</p>
                                    <div class="properties__footer d-flex justify-content-between align-items-center">
                                        <div class="restaurant-name">
                                            <div class="rating">
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star"></i>
                                                <i class="fa fa-star-half-o"></i>
                                            </div>
                                            <p><span>(4.5)</span> based on 120</p>
                                        </div>
                                        <div class="price">
                                            <span>$135</span>
                                        </div>
                                    </div>
                                    <a href="#" class="border-btn border-btn2">Find out more</a>
                                </div>
                            </div>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
        <!-- Courses area End -->
        <!--? About Area-1 Start -->
        <section class="about-area1 fix pt-10">
            <div class="support-wrapper align-items-center">
                <div class="left-content1">
                    <div class="about-icon">
                        <img src="assets/img/icon/about.svg" alt="">
                    </div>
                    <!-- section tittle -->
                    <div class="section-tittle section-tittle2 mb-55">
                        <div class="front-text">
                            <h2 class="">Learn new skills online with top educators</h2>
                            <p>The automated process all your website tasks. Discover tools and 
                                techniques to engage effectively with vulnerable children and young 
                            people.</p>
                        </div>
                    </div>
                    <div class="single-features">
                        <div class="features-icon">
                            <img src="assets/img/icon/right-icon.svg" alt="">
                        </div>
                        <div class="features-caption">
                            <p>Techniques to engage effectively with vulnerable children and young people.</p>
                        </div>
                    </div>
                    <div class="single-features">
                        <div class="features-icon">
                            <img src="assets/img/icon/right-icon.svg" alt="">
                        </div>
                        <div class="features-caption">
                            <p>Join millions of people from around the world  learning together.</p>
                        </div>
                    </div>

                    <div class="single-features">
                        <div class="features-icon">
                            <img src="assets/img/icon/right-icon.svg" alt="">
                        </div>
                        <div class="features-caption">
                            <p>Join millions of people from around the world learning together. Online learning is as easy and natural.</p>
                        </div>
                    </div>
                </div>
                <div class="right-content1">
                    <!-- img -->
                    <div class="right-img">
                        <img src="assets/img/gallery/about.png" alt="">

                        <div class="video-icon" >
                            <a class="popup-video btn-icon" href="https://www.youtube.com/watch?v=up68UAfH0d0"><i class="fas fa-play"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- About Area End -->
        <!--? top subjects Area Start -->
       
        <!--? top subjects Area Start -->
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
                    <% if (request.getAttribute("topics") != null) { %>
                        <% 
                        List<Topic> topics = (List<Topic>) request.getAttribute("topics");
                        for (int i = 0; i < topics.size() && i < 8; i++) {
                            Topic topic = topics.get(i);
                        %>
                    <div class="col-lg-3 col-md-4 col-sm-6">
                        <div class="single-topic text-center mb-30">
                            <div class="topic-img">
                                    <img src="<%= topic.getThumbnail_url() != null ? topic.getThumbnail_url() : "assets/img/gallery/topic" + (i + 1) + ".png" %>" alt="<%= topic.getName() %>">
                                <div class="topic-content-box">
                                    <div class="topic-content">
                                            <h3><a href="#"><%= topic.getName() %></a></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                        <% } %>
                    <% } else { %>
                        <!-- Fallback content -->
                        <div class="col-12 text-center">
                            <p>No topics available.</p>
                                    </div>
                    <% } %>
                </div>
                <div class="row justify-content-center">
                    <div class="col-xl-12">
                        <div class="section-tittle text-center mt-20">
                            <a href="courses.jsp" class="border-btn">View More Topics</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- top subjects End -->
        <!--? About Area-3 Start -->
        <section class="about-area3 fix">
            <div class="support-wrapper align-items-center">
                <div class="right-content3">
                    <!-- img -->
                    <div class="right-img">
                        <img src="assets/img/gallery/about3.png" alt="">
                    </div>
                </div>
                <div class="left-content3">
                    <!-- section tittle -->
                    <div class="section-tittle section-tittle2 mb-20">
                        <div class="front-text">
                            <h2 class="">Learner outcomes on courses you will take</h2>
                        </div>
                    </div>
                    <div class="single-features">
                        <div class="features-icon">
                            <img src="assets/img/icon/right-icon.svg" alt="">
                        </div>
                        <div class="features-caption">
                            <p>Techniques to engage effectively with vulnerable children and young people.</p>
                        </div>
                    </div>
                    <div class="single-features">
                        <div class="features-icon">
                            <img src="assets/img/icon/right-icon.svg" alt="">
                        </div>
                        <div class="features-caption">
                            <p>Join millions of people from around the world
                            learning together.</p>
                        </div>
                    </div>
                    <div class="single-features">
                        <div class="features-icon">
                            <img src="assets/img/icon/right-icon.svg" alt="">
                        </div>
                        <div class="features-caption">
                            <p>Join millions of people from around the world learning together.
                            Online learning is as easy and natural.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- About Area End -->
        <!--? Team -->
        <section class="team-area section-padding40 fix">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xl-7 col-lg-8">
                        <div class="section-tittle text-center mb-55">
                            <h2>Community experts</h2>
                        </div>
                    </div>
                </div>
                <div class="team-active">
                    <div class="single-cat text-center">
                        <div class="cat-icon">
                            <img src="assets/img/gallery/team1.png" alt="">
                        </div>
                        <div class="cat-cap">
                            <h5><a href="services.jsp">Mr. Urela</a></h5>
                            <p>The automated process all your website tasks.</p>
                        </div>
                    </div>
                    <div class="single-cat text-center">
                        <div class="cat-icon">
                            <img src="assets/img/gallery/team2.png" alt="">
                        </div>
                        <div class="cat-cap">
                            <h5><a href="services.jsp">Mr. Uttom</a></h5>
                            <p>The automated process all your website tasks.</p>
                        </div>
                    </div>
                    <div class="single-cat text-center">
                        <div class="cat-icon">
                            <img src="assets/img/gallery/team3.png" alt="">
                        </div>
                        <div class="cat-cap">
                            <h5><a href="services.jsp">Mr. Shakil</a></h5>
                            <p>The automated process all your website tasks.</p>
                        </div>
                    </div>
                    <div class="single-cat text-center">
                        <div class="cat-icon">
                            <img src="assets/img/gallery/team4.png" alt="">
                        </div>
                        <div class="cat-cap">
                            <h5><a href="services.jsp">Mr. Arafat</a></h5>
                            <p>The automated process all your website tasks.</p>
                        </div>
                    </div>
                    <div class="single-cat text-center">
                        <div class="cat-icon">
                            <img src="assets/img/gallery/team3.png" alt="">
                        </div>
                        <div class="cat-cap">
                            <h5><a href="services.jsp">Mr. saiful</a></h5>
                            <p>The automated process all your website tasks.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Services End -->
        <!--? About Area-2 Start -->
        <section class="about-area2 fix pb-padding">
            <div class="support-wrapper align-items-center">
                <div class="right-content2">
                    <!-- img -->
                    <div class="right-img">
                        <img src="assets/img/gallery/about2.png" alt="">
                    </div>
                </div>
                <div class="left-content2">
                    <!-- section tittle -->
                    <div class="section-tittle section-tittle2 mb-20">
                        <div class="front-text">
                            <h2 class="">Take the next step
                                toward your personal
                                and professional goals
                            with us.</h2>
                            <p>The automated process all your website tasks. Discover tools and techniques to engage effectively with vulnerable children and young people.</p>
                            <a href="#" class="btn">Join now for Free</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- About Area End -->
    </main>
    <footer>
     <div class="footer-wrappper footer-bg">
        <!-- Footer Start-->
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
                        <div class="col-xl-12 ">
                            <div class="footer-copy-right text-center">
                                <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                  Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                  <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <!-- Footer End-->
      </div>
  </footer> 
  <!-- Scroll Up -->
  <div id="back-top" >
    <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
</div>

<!-- JS here -->
<script src="./assets/js/vendor/modernizr-3.5.0.min.js"></script>
<!-- Jquery, Popper, Bootstrap -->
<script src="./assets/js/vendor/jquery-1.12.4.min.js"></script>
<script src="./assets/js/popper.min.js"></script>
<script src="./assets/js/bootstrap.min.js"></script>
<!-- Jquery Mobile Menu -->
<script src="./assets/js/jquery.slicknav.min.js"></script>

<!-- Jquery Slick , Owl-Carousel Plugins -->
<script src="./assets/js/owl.carousel.min.js"></script>
<script src="./assets/js/slick.min.js"></script>
<!-- One Page, Animated-HeadLin -->
<script src="./assets/js/wow.min.js"></script>
<script src="./assets/js/animated.headline.js"></script>
<script src="./assets/js/jquery.magnific-popup.js"></script>

<!-- Date Picker -->
<script src="./assets/js/gijgo.min.js"></script>
<!-- Nice-select, sticky -->
<script src="./assets/js/jquery.nice-select.min.js"></script>
<script src="./assets/js/jquery.sticky.js"></script>
<!-- Progress -->
<script src="./assets/js/jquery.barfiller.js"></script>

<!-- counter , waypoint,Hover Direction -->
<script src="./assets/js/jquery.counterup.min.js"></script>
<script src="./assets/js/waypoints.min.js"></script>
<script src="./assets/js/jquery.countdown.min.js"></script>
<script src="./assets/js/hover-direction-snake.min.js"></script>

<!-- contact js -->
<script src="./assets/js/contact.js"></script>
<script src="./assets/js/jquery.form.js"></script>
<script src="./assets/js/jquery.validate.min.js"></script>
<script src="./assets/js/mail-script.js"></script>
<script src="./assets/js/jquery.ajaxchimp.min.js"></script>

<!-- Jquery Plugins, main Jquery -->	
<script src="./assets/js/plugins.js"></script>
<script src="./assets/js/main.js"></script>
    
    <!-- Custom JavaScript for Dynamic Slider -->
    <script>
        // Dynamic Slider functionality
        let currentSlide = 0;
        let slideInterval;
        const slideDuration = 3000; // 3 seconds
        
        // Get all slides and indicators
        const slides = document.querySelectorAll('.dynamic-slider-slide');
        const indicators = document.querySelectorAll('.dynamic-indicator');
        const totalSlides = slides.length;
        
        // Initialize slider
        function initSlider() {
            if (totalSlides > 0) {
                showSlide(currentSlide);
                startAutoSlide();
            }
        }
        
        // Show specific slide
        function showSlide(index) {
            // Hide all slides
            slides.forEach(slide => {
                slide.classList.remove('active');
            });
            
            // Remove active class from all indicators
            indicators.forEach(indicator => {
                indicator.classList.remove('active');
            });
            
            // Show current slide
            if (slides[index]) {
                slides[index].classList.add('active');
            }
            
            // Activate current indicator
            if (indicators[index]) {
                indicators[index].classList.add('active');
            }
            
            currentSlide = index;
        }
        
        // Change slide (next/previous)
        function changeSlide(direction) {
            let newIndex = currentSlide + direction;
            
            if (newIndex >= totalSlides) {
                newIndex = 0;
            } else if (newIndex < 0) {
                newIndex = totalSlides - 1;
            }
            
            showSlide(newIndex);
            resetAutoSlide();
        }
        
        // Go to specific slide
        function goToSlide(index) {
            showSlide(index);
            resetAutoSlide();
        }
        
        // Start auto-sliding
        function startAutoSlide() {
            slideInterval = setInterval(() => {
                changeSlide(1);
            }, slideDuration);
        }
        
        // Reset auto-slide timer
        function resetAutoSlide() {
            clearInterval(slideInterval);
            startAutoSlide();
        }
        
        // Pause auto-slide on hover
        function pauseAutoSlide() {
            clearInterval(slideInterval);
        }
        
        // Resume auto-slide when mouse leaves
        function resumeAutoSlide() {
            startAutoSlide();
        }
        
        // Initialize slider when DOM is loaded
        document.addEventListener('DOMContentLoaded', function() {
            initSlider();
            
            // Add hover events to pause/resume auto-slide
            const sliderContainer = document.querySelector('.dynamic-slider-container');
            if (sliderContainer) {
                sliderContainer.addEventListener('mouseenter', pauseAutoSlide);
                sliderContainer.addEventListener('mouseleave', resumeAutoSlide);
            }
        });
        
        // Keyboard navigation
        document.addEventListener('keydown', function(e) {
            if (e.key === 'ArrowLeft') {
                changeSlide(-1);
            } else if (e.key === 'ArrowRight') {
                changeSlide(1);
            }
        });
    </script>

</body>
</html>