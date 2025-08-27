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

        <!-- Custom CSS for Professional Product Slider -->
        <style>
            /* Professional Product Slider Styling */
            .product-slider-area {
                position: relative;
                height: 500px;
                overflow: hidden;
                background: #f8f9fa;
                margin: 0;
                width: 100%;
            }

            .product-slider-container {
                position: relative;
                width: 100%;
                height: 100%;
            }

            .product-slider-wrapper {
                position: relative;
                width: 100%;
                height: 100%;
            }

            .product-slider-slide {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                opacity: 0;
                transition: opacity 0.8s ease-in-out;
                z-index: 1;
            }

            .product-slider-slide.active {
                opacity: 1;
                z-index: 2;
            }

            .product-slider-image {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
            }

            .product-slider-image img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                object-position: center;
            }

            .product-slider-content {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                display: flex;
                align-items: center;
                justify-content: center;
                z-index: 3;
            }

            .product-slider-overlay {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: linear-gradient(135deg, rgba(0,0,0,0.4) 0%, rgba(0,0,0,0.2) 50%, rgba(0,0,0,0.4) 100%);
                z-index: 2;
            }

            .product-slider-text {
                text-align: center;
                color: white;
                z-index: 4;
                position: relative;
                max-width: 800px;
                padding: 0 20px;
            }

            .product-slider-title {
                font-size: 3.5rem;
                font-weight: 700;
                margin-bottom: 20px;
                text-shadow: 2px 2px 8px rgba(0,0,0,0.7);
                line-height: 1.2;
                letter-spacing: -0.5px;
            }

            .product-slider-subtitle {
                font-size: 1.25rem;
                font-weight: 400;
                margin-bottom: 30px;
                text-shadow: 1px 1px 4px rgba(0,0,0,0.7);
                opacity: 0.95;
            }

            .product-slider-btn {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                padding: 15px 40px;
                border-radius: 50px;
                text-decoration: none;
                font-weight: 600;
                font-size: 1.1rem;
                transition: all 0.3s ease;
                box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
                display: inline-block;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .product-slider-btn:hover {
                transform: translateY(-3px);
                box-shadow: 0 12px 35px rgba(102, 126, 234, 0.6);
                color: white;
                text-decoration: none;
            }

            /* Professional Navigation Arrows */
            .product-slider-nav {
                position: absolute;
                top: 50%;
                transform: translateY(-50%);
                background: rgba(255,255,255,0.15);
                border: 2px solid rgba(255,255,255,0.3);
                color: white;
                width: 60px;
                height: 60px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                cursor: pointer;
                transition: all 0.3s ease;
                z-index: 10;
                backdrop-filter: blur(15px);
                font-size: 1.2rem;
            }

            .product-slider-nav:hover {
                background: rgba(255,255,255,0.25);
                border-color: rgba(255,255,255,0.5);
                transform: translateY(-50%) scale(1.1);
                box-shadow: 0 8px 25px rgba(0,0,0,0.2);
            }

            .product-slider-prev {
                left: 30px;
            }

            .product-slider-next {
                right: 30px;
            }

            .product-slider-nav i {
                font-size: 24px;
                font-weight: bold;
            }

            /* Professional Indicators */
            .product-slider-indicators {
                position: absolute;
                bottom: 30px;
                left: 50%;
                transform: translateX(-50%);
                display: flex;
                gap: 12px;
                z-index: 10;
            }

            .product-indicator {
                width: 14px;
                height: 14px;
                border-radius: 50%;
                background: rgba(255,255,255,0.4);
                border: 2px solid rgba(255,255,255,0.6);
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .product-indicator:hover {
                background: rgba(255,255,255,0.7);
                border-color: rgba(255,255,255,0.9);
                transform: scale(1.2);
            }

            .product-indicator.active {
                background: white;
                border-color: white;
                transform: scale(1.3);
                box-shadow: 0 0 15px rgba(255,255,255,0.5);
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .product-slider-area {
                    height: 400px;
                }

                .product-slider-title {
                    font-size: 2.5rem;
                }

                .product-slider-subtitle {
                    font-size: 1rem;
                }

                .product-slider-nav {
                    width: 50px;
                    height: 50px;
                }

                .product-slider-nav i {
                    font-size: 20px;
                }
            }

            @media (max-width: 480px) {
                .product-slider-area {
                    height: 350px;
                }

                .product-slider-title {
                    font-size: 2rem;
                }

                .product-slider-subtitle {
                    font-size: 0.9rem;
                }
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
                                                    <li><a href="purchased-courses">Purchased courses</a></li>
                                                    <li><a href="blog">Blog</a></li>
                                                    <li><a href="cart">Cart</a></li>
                                                    <li><a href="profile" class="btn">Profile</a></li>
                                                    <li><a href="${pageContext.request.contextPath}/logout" class="btn">Logout</a></li>
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

            <!--? Professional Product Slider Area Start-->
            <section class="product-slider-area">
                <div class="product-slider-container">
                    <div class="product-slider-wrapper">
                        <c:choose>
                            <c:when test="${not empty sliders}">
                                <c:forEach var="slider" items="${sliders}" varStatus="status">
                                    <div class="product-slider-slide ${status.index == 0 ? 'active' : ''}" data-index="${status.index}">
                                        <div class="product-slider-image">
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
                                        <div class="product-slider-overlay"></div>
                                        <div class="product-slider-content">
                                            <div class="product-slider-text">
                                                <h1 class="product-slider-title" data-animation="fadeInUp" data-delay="0.2s">
                                                    ${slider.title}
                                                </h1>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <!-- Fallback slider content when no data from database -->
                                <div class="product-slider-slide active" data-index="0">
                                    <div class="product-slider-image">
                                        <img src="assets/img/gallery/slider1.jpg" alt="Professional Product Showcase">
                                    </div>
                                    <div class="product-slider-overlay"></div>
                                    <div class="product-slider-content">
                                        <div class="product-slider-text">
                                            <h1 class="product-slider-title" data-animation="fadeInUp" data-delay="0.2s">
                                                Professional<br>Product Showcase
                                            </h1>
                                            <p class="product-slider-subtitle" data-animation="fadeInUp" data-delay="0.4s">
                                                Discover amazing products and services designed for your success
                                            </p>
                                            <a href="#" class="product-slider-btn" data-animation="fadeInUp" data-delay="0.6s">
                                                Explore Now
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Professional Navigation Arrows -->
                    <button class="product-slider-nav product-slider-prev" onclick="changeProductSlide(-1)" aria-label="Previous slide">
                        <i class="fas fa-chevron-left"></i>
                    </button>
                    <button class="product-slider-nav product-slider-next" onclick="changeProductSlide(1)" aria-label="Next slide">
                        <i class="fas fa-chevron-right"></i>
                    </button>

                    <!-- Professional Indicators -->
                    <div class="product-slider-indicators">
                        <c:choose>
                            <c:when test="${not empty sliders}">
                                <c:forEach var="slider" items="${sliders}" varStatus="status">
                                    <c:choose>
                                        <c:when test="${status.index == 0}">
                                            <span class="product-indicator active" onclick="goToProductSlide(<c:out value='${status.index}'/>)"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="product-indicator" onclick="goToProductSlide(<c:out value='${status.index}'/>)"></span>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <span class="product-indicator active" onclick="goToProductSlide(0)"></span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </section>
            <!-- Professional Product Slider Area End -->

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
                        <c:choose>
                            <c:when test="${not empty latestCourses}">
                                <c:forEach var="course" items="${latestCourses}">
                                    <!-- Single Course -->
                                    <div class="properties pb-20">
                                        <div class="properties__card">
                                            <div class="properties__img overlay1">
                                                <a href="#"><img src="${not empty course.thumbnail_url ? course.thumbnail_url : 'assets/img/gallery/featured1.png'}" alt="${course.title}"></a>
                                            </div>
                                            <div class="properties__caption">
                                                <p>Course</p>
                                                <h3><a href="#">${course.title}</a></h3>
                                                <p>${not empty course.description ? course.description : 'No description available'}</p>
                                                <div class="properties__footer d-flex justify-content-between align-items-center">
                                                    <div class="restaurant-name">
                                                        <div class="rating">
                                                            <c:set var="rating" value="${course.averageRating != null ? course.averageRating : 0.0}" />
                                                            <c:set var="fullStars" value="${fn:substringBefore(rating, '.')}" />
                                                            <c:set var="hasHalfStar" value="${rating % 1 >= 0.5}" />

                                                            <c:forEach begin="1" end="${fullStars}" var="i">
                                                                <i class="fa fa-star"></i>
                                                            </c:forEach>

                                                            <c:if test="${hasHalfStar}">
                                                                <i class="fa fa-star-half-o"></i>
                                                            </c:if>

                                                            <c:forEach begin="1" end="${5 - fullStars - (hasHalfStar ? 1 : 0)}" var="i">
                                                                <i class="fa fa-star-o"></i>
                                                            </c:forEach>
                                                        </div>
                                                        <p><span>(${fn:substringBefore(rating, '.')}.${fn:substringAfter(rating, '.')})</span> based on reviews</p>
                                                    </div>
                                                    <div class="price">
                                                        <span>$${course.price}</span>
                                                    </div>
                                                </div>
                                                <a href="customer-course-detail?id=${course.course_id}" class="border-btn border-btn2">Find out more</a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
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
                            </c:otherwise>
                        </c:choose>
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
                                <!-- Fallback content -->
                                <div class="col-12 text-center">
                                    <p>No topics available.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
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

        <!-- Custom JavaScript for Slider -->
        <script>
                                          // Professional Product Slider functionality
                                          let currentProductSlide = 0;
                                          let productSlideInterval;
                                          const productSlideDuration = 4000; // 4 seconds for better user experience

                                          // Get all slides and indicators
                                          const productSlides = document.querySelectorAll('.product-slider-slide');
                                          const productIndicators = document.querySelectorAll('.product-indicator');
                                          const totalProductSlides = productSlides.length;

                                          // Initialize product slider
                                          function initProductSlider() {
                                              if (totalProductSlides > 0) {
                                                  showProductSlide(currentProductSlide);
                                                  startProductAutoSlide();
                                              }
                                          }

                                          // Show specific product slide
                                          function showProductSlide(index) {
                                              // Hide all slides
                                              productSlides.forEach(slide => {
                                                  slide.classList.remove('active');
                                              });

                                              // Remove active class from all indicators
                                              productIndicators.forEach(indicator => {
                                                  indicator.classList.remove('active');
                                              });

                                              // Show current slide
                                              if (productSlides[index]) {
                                                  productSlides[index].classList.add('active');
                                              }

                                              // Activate current indicator
                                              if (productIndicators[index]) {
                                                  productIndicators[index].classList.add('active');
                                              }

                                              currentProductSlide = index;
                                          }

                                          // Change product slide (next/previous)
                                          function changeProductSlide(direction) {
                                              let newIndex = currentProductSlide + direction;

                                              if (newIndex >= totalProductSlides) {
                                                  newIndex = 0;
                                              } else if (newIndex < 0) {
                                                  newIndex = totalProductSlides - 1;
                                              }

                                              showProductSlide(newIndex);
                                              resetProductAutoSlide();
                                          }

                                          // Go to specific product slide
                                          function goToProductSlide(index) {
                                              showProductSlide(index);
                                              resetProductAutoSlide();
                                          }

                                          // Start auto-sliding
                                          function startProductAutoSlide() {
                                              productSlideInterval = setInterval(() => {
                                                  changeProductSlide(1);
                                              }, productSlideDuration);
                                          }

                                          // Reset auto-slide timer
                                          function resetProductAutoSlide() {
                                              clearInterval(productSlideInterval);
                                              startProductAutoSlide();
                                          }

                                          // Pause auto-slide on hover
                                          function pauseProductAutoSlide() {
                                              clearInterval(productSlideInterval);
                                          }

                                          // Resume auto-slide when mouse leaves
                                          function resumeProductAutoSlide() {
                                              startProductAutoSlide();
                                          }

                                          // Initialize product slider when DOM is loaded
                                          document.addEventListener('DOMContentLoaded', function () {
                                              initProductSlider();

                                              // Add hover events to pause/resume auto-slide
                                              const productSliderContainer = document.querySelector('.product-slider-container');
                                              if (productSliderContainer) {
                                                  productSliderContainer.addEventListener('mouseenter', pauseProductAutoSlide);
                                                  productSliderContainer.addEventListener('mouseleave', resumeProductAutoSlide);
                                              }

                                              // Add touch/swipe support for mobile
                                              let touchStartX = 0;
                                              let touchEndX = 0;

                                              productSliderContainer.addEventListener('touchstart', function (e) {
                                                  touchStartX = e.changedTouches[0].screenX;
                                              });

                                              productSliderContainer.addEventListener('touchend', function (e) {
                                                  touchEndX = e.changedTouches[0].screenX;
                                                  handleSwipe();
                                              });

                                              function handleSwipe() {
                                                  const swipeThreshold = 50;
                                                  const diff = touchStartX - touchEndX;

                                                  if (Math.abs(diff) > swipeThreshold) {
                                                      if (diff > 0) {
                                                          // Swipe left - next slide
                                                          changeProductSlide(1);
                                                      } else {
                                                          // Swipe right - previous slide
                                                          changeProductSlide(-1);
                                                      }
                                                  }
                                              }
                                          });

                                          // Keyboard navigation
                                          document.addEventListener('keydown', function (e) {
                                              if (e.key === 'ArrowLeft') {
                                                  changeProductSlide(-1);
                                              } else if (e.key === 'ArrowRight') {
                                                  changeProductSlide(1);
                                              }
                                          });

                                          // Add smooth animations for better UX
                                          function addSlideAnimations() {
                                              const activeSlide = document.querySelector('.product-slider-slide.active');
                                              if (activeSlide) {
                                                  const title = activeSlide.querySelector('.product-slider-title');
                                                  const subtitle = activeSlide.querySelector('.product-slider-subtitle');
                                                  const button = activeSlide.querySelector('.product-slider-btn');

                                                  // Reset animations
                                                  [title, subtitle, button].forEach(element => {
                                                      if (element) {
                                                          element.style.opacity = '0';
                                                          element.style.transform = 'translateY(30px)';
                                                      }
                                                  });

                                                  // Animate elements in sequence
                                                  setTimeout(() => {
                                                      if (title) {
                                                          title.style.transition = 'all 0.8s ease';
                                                          title.style.opacity = '1';
                                                          title.style.transform = 'translateY(0)';
                                                      }
                                                  }, 200);

                                                  setTimeout(() => {
                                                      if (subtitle) {
                                                          subtitle.style.transition = 'all 0.8s ease';
                                                          subtitle.style.opacity = '1';
                                                          subtitle.style.transform = 'translateY(0)';
                                                      }
                                                  }, 400);

                                                  setTimeout(() => {
                                                      if (button) {
                                                          button.style.transition = 'all 0.8s ease';
                                                          button.style.opacity = '1';
                                                          button.style.transform = 'translateY(0)';
                                                      }
                                                  }, 600);
                                              }
                                          }

                                          // Call animation function when slide changes
                                          const originalShowProductSlide = showProductSlide;
                                          showProductSlide = function (index) {
                                              originalShowProductSlide(index);
                                              setTimeout(addSlideAnimations, 100);
                                          };
        </script>

    </body>
</html>