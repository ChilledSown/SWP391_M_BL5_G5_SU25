<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    
    <!-- Custom CSS for Search and Filter -->
    <style>
        .search-filter-container {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 35px;
            border-radius: 20px;
            margin-bottom: 40px;
            position: relative;
            overflow: visible;
            width: 100%;
        }
        
        .search-filter-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(255,255,255,0.1);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            pointer-events: none;
        }
        
        .search-filter-container > * {
            position: relative;
            z-index: 2;
        }
        
        .search-filter-title {
            text-align: center;
            margin-bottom: 30px;
            color: white;
        }
        
        .search-filter-title h3 {
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 8px;
            text-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .search-filter-title p {
            font-size: 16px;
            opacity: 0.9;
            margin: 0;
        }
        
        .search-box {
            position: relative;
            display: flex;
            align-items: center;
            margin-bottom: 25px;
        }
        
        .search-box input {
            padding: 18px 60px 18px 25px;
            border-radius: 50px;
            border: none;
            background: rgba(255,255,255,0.95);
            font-size: 16px;
            transition: all 0.3s ease;
            width: 100%;
        }
        
        .search-box input:focus {
            outline: none;
            background: white;
            transform: translateY(-2px);
        }
        
        .search-btn {
            position: absolute;
            right: 0.1px;
            top: 50%;
            transform: translateY(-50%);
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            width: 45px;
            height: 45px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            transition: all 0.3s ease;
        }
        
        .search-btn:hover {
            transform: translateY(-50%) scale(1.1);
        }
        
        .search-btn i {
            font-size: 18px;
        }
        
        .course-topic {
            margin-bottom: 15px;
        }
        
        .course-topic .badge {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 8px 15px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            width: 100%;
        }
        
        .course-topic .badge:hover {
            transform: translateY(-2px);
        }
        
        .course-topic .badge i {
            margin-right: 5px;
            font-size: 10px;
        }
        
        .filters-section {
            background: rgba(255,255,255,0.1);
            border-radius: 15px;
            padding: 25px;
            backdrop-filter: blur(10px);
            overflow: visible;
            width: 100%;
        }

        .nice-select { width: 100%; }
        .nice-select .list { z-index: 10000; }
        .nice-select.open .list { max-height: 280px; overflow-y: auto; }
        
        .filters-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            align-items: end;
        }
        
        .filter-item {
            margin-bottom: 0;
        }
        
        .filter-label {
            font-weight: 600;
            color: white;
            margin-bottom: 8px;
            display: block;
            font-size: 14px;
            text-shadow: 0 1px 2px rgba(0,0,0,0.1);
        }
        
        .filter-item select {
            width: 100%;
            padding: 12px 15px;
            border-radius: 12px;
            border: none;
            background: rgba(255,255,255,0.95);
            font-size: 14px;
            transition: all 0.3s ease;
            cursor: pointer;
        }
        
        .filter-item select:focus {
            outline: none;
            background: white;
            transform: translateY(-1px);
        }
        
        .filter-item select:hover {
            background: rgba(255,255,255,1);
        }
        
        .clear-filters-btn {
            background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
            border: none;
            color: white;
            padding: 12px 20px;
            border-radius: 12px;
            font-weight: 600;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
            width: 100%;
        }
        
        .clear-filters-btn:hover {
            transform: translateY(-2px);
            color: white;
            text-decoration: none;
        }
        
        .clear-filters-btn i {
            margin-right: 8px;
        }
        
        .active-filters {
            margin-top: 20px;
            padding: 15px;
            background: rgba(255,255,255,0.1);
            border-radius: 10px;
            backdrop-filter: blur(5px);
        }
        
        .active-filters h5 {
            color: white;
            font-size: 16px;
            margin-bottom: 10px;
            font-weight: 600;
        }
        
        .filter-tags {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }
        
        .filter-tag {
            background: rgba(255,255,255,0.2);
            color: white;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 12px;
            backdrop-filter: blur(5px);
        }
        
        .properties.properties2 {
            height: 100%;
            display: flex;
            flex-direction: column;
            background: white;
            border-radius: 12px;
            transition: all 0.3s ease;
            width: 100%;
        }
        
        .properties.properties2:hover {
            transform: translateY(-5px);
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
        
        /* No results styling */
        .no-results {
            padding: 60px 20px;
            text-align: center;
        }
        
        .no-results h4 {
            color: #2c3e50;
            margin-bottom: 15px;
            font-weight: 600;
        }
        
        .no-results p {
            color: #7f8c8d;
            margin-bottom: 10px;
            font-size: 16px;
        }
        
        .no-results a {
            text-decoration: none;
            font-weight: 600;
        }
        
        .no-results a:hover {
            text-decoration: underline;
        }
        
        .course-topic .badge {
            font-size: 12px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            margin-bottom: 8px;
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
        
        .properties__caption p:nth-of-type(2) {
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
        
        /* Ensure all course cards in a row have the same height */
        .row .col-lg-4 {
            display: flex;
            margin-bottom: 25px;
        }
        
        .row .col-lg-4 .properties.properties2 {
            width: 100%;
        }
        
        /* Rating and price styling */
        .restaurant-name .rating {
            margin-bottom: 5px;
        }
        
        .restaurant-name .rating i {
            color: #ffc107;
            font-size: 14px;
        }
        
        .restaurant-name p {
            font-size: 12px;
            color: #6c757d;
            margin: 0;
        }
        
                 .price span {
             font-size: 18px;
             font-weight: 700;
             color: #667eea;
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
    <main>
        <!--? slider Area Start-->
        <section class="slider-area slider-area2">
            <div class="slider-active">
                <!-- Single Slider -->
                <div class="single-slider slider-height2">
                    <div class="container">
                        <div class="row">
                            <div class="col-xl-8 col-lg-11 col-md-12">
                                <div class="hero__caption hero__caption2">
                                    <h1 data-animation="bounceIn" data-delay="0.2s">Our courses</h1>
                                    <!-- breadcrumb Start-->
                                    <nav aria-label="breadcrumb">
                                        <ol class="breadcrumb">
                                            <li class="breadcrumb-item"><a href="home">Home</a></li>
                                            <li class="breadcrumb-item"><a href="courses">Courses</a></li> 
                                        </ol>
                                    </nav>
                                    <!-- breadcrumb End -->
                                </div>
                            </div>
                        </div>
                    </div>          
                </div>
            </div>
        </section>
        <!-- Courses area start -->
        <div class="courses-area section-padding40 fix">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xl-7 col-lg-8">
                        <div class="section-tittle text-center mb-55">
                            <h2>Our featured courses</h2>
                            <c:if test="${not empty searchTerm or not empty priceFilter or not empty ratingFilter or not empty topicFilter}">
                                <div class="results-info mt-3">
                                    <p class="text-muted">
                                        Showing <strong>${totalResults}</strong> course(s)
                                        <c:if test="${not empty searchTerm}">
                                            for "<strong>${searchTerm}</strong>"
                                        </c:if>
                                    </p>
                                </div>
                            </c:if>
                                </div>
                            </div>
                        </div>
                
                <!-- Search and Filter Section -->
                <div class="row justify-content-center mb-40">
                    <div class="col-xl-11 col-lg-12">
                        <form action="courses" method="GET" class="search-filter-container">
                            <!-- Title Section -->
                            <div class="search-filter-title">
                                <h3><i class="fas fa-search"></i> Find Your Perfect Course</h3>
                                <p>Discover courses that match your interests and learning goals</p>
                                </div>
                            
                            <!-- Search Bar -->
                            <div class="search-box">
                                <input type="text" name="search" value="${searchTerm}" class="form-control" placeholder="What would you like to learn today?" aria-label="Search courses">
                                </div>
                                
                            <!-- Filters Section -->
                            <div class="filters-section">
                                <div class="filters-row">
                                    <div class="filter-item">
                                        <label for="priceFilter" class="filter-label">
                                            <i class="fas fa-dollar-sign"></i> Price Range
                                        </label>
                                        <select name="price" id="priceFilter" onchange="this.form.submit()">
                                            <option value="">All Prices</option>
                                            <option value="0-50" <c:if test="${priceFilter == '0-50'}">selected</c:if>>$0 - $50</option>
                                            <option value="51-100" <c:if test="${priceFilter == '51-100'}">selected</c:if>>$51 - $100</option>
                                            <option value="101-200" <c:if test="${priceFilter == '101-200'}">selected</c:if>>$101 - $200</option>
                                            <option value="201+" <c:if test="${priceFilter == '201+'}">selected</c:if>>$201+</option>
                                        </select>
                                </div>
                                    
                                    <div class="filter-item">
                                        <label for="ratingFilter" class="filter-label">
                                            <i class="fas fa-star"></i> Minimum Rating
                                        </label>
                                        <select name="rating" id="ratingFilter" onchange="this.form.submit()">
                                            <option value="">All Ratings</option>
                                            <option value="4.5" <c:if test="${ratingFilter == '4.5'}">selected</c:if>>4.5+ Stars</option>
                                            <option value="4.0" <c:if test="${ratingFilter == '4.0'}">selected</c:if>>4.0+ Stars</option>
                                            <option value="3.5" <c:if test="${ratingFilter == '3.5'}">selected</c:if>>3.5+ Stars</option>
                                            <option value="3.0" <c:if test="${ratingFilter == '3.0'}">selected</c:if>>3.0+ Stars</option>
                                        </select>
                                </div>
                                
                                    <div class="filter-item">
                                        <label for="sortBy" class="filter-label">
                                            <i class="fas fa-sort"></i> Sort By
                                        </label>
                                        <select name="sort" id="sortBy" onchange="this.form.submit()">
                                            <option value="newest" <c:if test="${sortBy == 'newest'}">selected</c:if>>Newest First</option>
                                            <option value="oldest" <c:if test="${sortBy == 'oldest'}">selected</c:if>>Oldest First</option>
                                            <option value="price-low" <c:if test="${sortBy == 'price-low'}">selected</c:if>>Price: Low to High</option>
                                            <option value="price-high" <c:if test="${sortBy == 'price-high'}">selected</c:if>>Price: High to Low</option>
                                            <option value="rating" <c:if test="${sortBy == 'rating'}">selected</c:if>>Highest Rated</option>
                                        </select>
                                </div>
                                    
                                    <div class="filter-item">
                                        <label for="topicFilter" class="filter-label">
                                            <i class="fas fa-tag"></i> Topic
                                        </label>
                                                                                 <select name="topic" id="topicFilter" onchange="this.form.submit()">
                                             <option value="">All Topics</option>
                                             <c:forEach items="${topics}" var="topic">
                                                 <option value="${topic.name}" <c:if test="${topicFilter == topic.name}">selected</c:if>>${topic.name}</option>
                                             </c:forEach>
                                         </select>
                                </div>
                                
                                    <div class="filter-item">
                                        <a href="courses" class="clear-filters-btn">
                                            <i class="fas fa-times"></i> Clear All
                                        </a>
                        </div>
                    </div>
                                
                                <!-- Active Filters Display -->
                                <c:if test="${not empty searchTerm or not empty priceFilter or not empty ratingFilter or not empty topicFilter}">
                                <div class="active-filters">
                                    <h5><i class="fas fa-filter"></i> Active Filters:</h5>
                                    <div class="filter-tags">
                                        <c:if test="${not empty searchTerm}">
                                            <span class="filter-tag">
                                                <i class="fas fa-search"></i> "${searchTerm}"
                                            </span>
                                        </c:if>
                                        <c:if test="${not empty priceFilter}">
                                            <span class="filter-tag">
                                                <i class="fas fa-dollar-sign"></i> ${priceFilter}
                                            </span>
                                        </c:if>
                    
                                        <c:if test="${not empty ratingFilter}">
                                            <span class="filter-tag">
                                                <i class="fas fa-star"></i> ${ratingFilter}+ Stars
                                            </span>
                                        </c:if>
                                        <c:if test="${not empty topicFilter}">
                                            <span class="filter-tag">
                                                <i class="fas fa-tag"></i> ${topicFilter}
                                            </span>
                                        </c:if>
                                        </div>
                                    </div>
                                </c:if>
                                </div>
                        </form>
                            </div>
                        </div>
                <div class="row" id="courses-list">
                    <c:if test="${not empty allCourses}">
                        <c:forEach var="course" items="${allCourses}">
                            <div class="col-lg-4">
                                <div class="properties properties2 mb-30">
                                    <div class="properties__card">
                                        <div class="properties__img overlay1">
                                            <a href="#"><img src="<c:out value="${empty course.thumbnail_url ? 'assets/img/gallery/featured1.png' : course.thumbnail_url}" />" alt="${course.title}"></a>
                                        </div>
                                        <div class="properties__caption">
                                            <h3><a href="#">${course.title}</a></h3>
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
                                            
                                            <c:if test="${not empty courseTopicsMap}">
                                                <c:set var="courseTopic" value="${courseTopicsMap[course.course_id]}" />
                                                <c:if test="${not empty courseTopic}">
                                                    <div class="course-topic">
                                                        <span class="badge">
                                                            <i class="fas fa-tag"></i>${courseTopic.name}
                                                        </span>
                                                    </div>
                                                </c:if>
                                            </c:if>
                                            
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
                                            <a href="customer-course-detail?id=${course.course_id}" class="border-btn border-btn2">Find out more</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty allCourses}">
                        <div class="col-12 text-center">
                            <c:choose>
                                <c:when test="${not empty searchTerm or not empty priceFilter or not empty ratingFilter or not empty topicFilter}">
                                    <div class="no-results">
                                        <i class="fas fa-search" style="font-size: 48px; color: #95a5a6; margin-bottom: 20px;"></i>
                                        <h4>No courses found</h4>
                                        <p>No courses match your current filters.</p>
                                        <p>Try adjusting your search criteria or <a href="courses" class="text-primary">clear all filters</a>.</p>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <p>Cannot found other courses.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:if>
                </div>
                
                <!-- Load More Button -->
                <div class="row justify-content-center">
                    <div class="col-xl-7 col-lg-8">
                        <div class="section-tittle text-center mt-40">
                            <c:if test="${hasMore}">
                                <button id="loadMoreBtn" class="border-btn" onclick="loadMore()">Load More</button>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Courses area End -->  
        <!-- ? services-area -->
        <div class="services-area services-area2 section-padding40">
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
    
     <script>
         // Auto-submit form when Enter key is pressed in search input
         document.addEventListener('DOMContentLoaded', function() {
             const searchInput = document.querySelector('input[name="search"]');
             if (searchInput) {
                 searchInput.addEventListener('keyup', function(e) {
                     if (e.key === 'Enter') {
                         this.form.submit();
                     }
                 });
             }
             
             // Add filter change logging
             const filterSelects = document.querySelectorAll('select[name="price"], select[name="rating"], select[name="sort"], select[name="topic"]');
             filterSelects.forEach(select => {
                 select.addEventListener('change', function() {
                     this.form.submit();
                 });
             });
         });
         
         // Function to manually submit filter form
         function submitFilterForm() {
             const form = document.querySelector('.search-filter-container form');
             if (form) {
                 form.submit();
             }
         }
     </script>
    
    <!-- Load more pagination -->
    <script>
        function loadMore() {
            const currentUrl = new URL(window.location);
            const currentPage = parseInt(currentUrl.searchParams.get('page') || '1');
            currentUrl.searchParams.set('page', currentPage + 1);
            window.location.href = currentUrl.toString();
        }
    </script>
</body>
</html>