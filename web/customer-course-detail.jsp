<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Course Detail | Education</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="manifest" href="site.webmanifest">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

        <!-- CSS here - Optimized for performance -->
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <!-- Custom CSS for Course Detail -->
        <style>
            /* Combined Header Styling (from purchased-courses) */
            .combined-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); position: relative; overflow: hidden; box-shadow: none; }
            .combined-header::before { content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: rgba(255,255,255,0.1); backdrop-filter: blur(10px); }
            .header-top { padding: 20px 0; border-bottom: 1px solid rgba(255,255,255,0.2); position: relative; z-index: 2; }
            .page-header-content { padding: 60px 0; position: relative; z-index: 2; text-align: center; color: white; }
            .page-header-content h1 { font-size: 36px; font-weight: 700; margin-bottom: 15px; text-shadow: 0 2px 4px rgba(0,0,0,0.1); }
            .page-header-content p { font-size: 18px; opacity: 0.9; margin: 0; }
            #navigation { display: flex; align-items: center; justify-content: center; gap: 0; margin: 0; padding: 0; list-style: none; }
            #navigation li { margin: 0; padding: 0; display: flex; align-items: center; }
            #navigation li a { color: white !important; font-weight: 500; font-size: 16px; text-decoration: none; padding: 12px 20px; border-radius: 8px; transition: all 0.3s ease; display: block; position: relative; margin: 0 5px; }
            #navigation li a::after { content: ''; position: absolute; bottom: 0; left: 50%; width: 0; height: 2px; background: white; transition: all 0.3s ease; transform: translateX(-50%); }
            /* Disable hover effects for non-button nav links */
            #navigation li a:not(.btn):hover { color: white !important; background: transparent !important; transform: none !important; box-shadow: none !important; backdrop-filter: none !important; }
            #navigation li a:not(.btn):hover::after { width: 0 !important; }
            .logo a { filter: brightness(1.2); }
            .logo img { max-height: 40px; }

            .course-detail-section {
                padding: 80px 0;
                background: #f8f9fa;
            }

            .course-image {
                border-radius: 15px;
                overflow: hidden;
                box-shadow: none;
                width: 100%;
            }

            .course-image img {
                width: 100%;
                height: 400px;
                object-fit: cover;
            }

            .course-info {
                padding: 30px;
                background: white;
                border-radius: 15px;
                box-shadow: none;
                width: 100%;
            }

            .course-title {
                font-size: 2.5rem;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 15px;
            }

            .course-price {
                font-size: 2rem;
                font-weight: 600;
                color: #e74c3c;
                margin-bottom: 20px;
            }

            .course-description {
                color: #7f8c8d;
                line-height: 1.8;
                margin-bottom: 25px;
            }

            .rating-stars {
                display: flex;
                align-items: center;
                margin-bottom: 15px;
            }

            .star {
                color: #f39c12;
                font-size: 20px;
                margin-right: 5px;
            }

            .star.empty {
                color: #bdc3c7;
            }

            .rating-count {
                margin-left: 10px;
                color: #7f8c8d;
            }

            .course-topic {
                display: inline-block;
                padding: 8px 20px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border-radius: 25px;
                font-size: 14px;
                margin-bottom: 25px;
            }

            .action-buttons {
                display: flex;
                gap: 15px;
                margin-bottom: 30px;
            }

            .btn-purchase {
                padding: 15px 30px;
                background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
                color: white;
                border: none;
                border-radius: 25px;
                font-weight: 600;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .btn-purchase:hover {
                transform: translateY(-2px);
                box-shadow: none;
                color: white;
            }

            .btn-cart {
                padding: 15px 30px;
                background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
                color: white;
                border: none;
                border-radius: 25px;
                font-weight: 600;
                text-decoration: none;
                transition: all 0.3s ease;
            }

            .btn-cart:hover {
                transform: translateY(-2px);
                box-shadow: none;
                color: white;
            }

            .btn-cart.in-cart {
                background: linear-gradient(135deg, #27ae60 0%, #229954 100%);
            }

            .what-you-learn {
                padding: 80px 0;
                background: white;
            }

            .section-title {
                text-align: center;
                font-size: 2.5rem;
                font-weight: 700;
                color: #2c3e50;
                margin-bottom: 50px;
            }

            .lessons-list {
                background: #f8f9fa;
                border-radius: 15px;
                padding: 30px;
            }

            .lesson-item {
                display: flex;
                align-items: center;
                padding: 15px 20px;
                background: white;
                border-radius: 10px;
                margin-bottom: 15px;
                box-shadow: none;
                transition: all 0.3s ease;
                width: 100%;
            }

            .lesson-item:hover {
                transform: translateX(5px);
                box-shadow: none;
            }

            .lesson-number {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                width: 40px;
                height: 40px;
                border-radius: 50%;
                display: flex;
                align-items: center;
                justify-content: center;
                font-weight: 600;
                margin-right: 20px;
            }

            .lesson-title {
                font-weight: 600;
                color: #2c3e50;
            }

            .reviews-section {
                padding: 80px 0;
                background: #f8f9fa;
            }

            .review-item {
                background: white;
                border-radius: 15px;
                padding: 25px;
                margin-bottom: 20px;
                box-shadow: none;
                width: 100%;
            }

            .review-header {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 15px;
            }

            .review-user {
                display: flex;
                align-items: center;
            }

            .user-avatar {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                display: flex;
                align-items: center;
                justify-content: center;
                color: white;
                font-weight: 600;
                margin-right: 15px;
            }

            .user-name {
                font-weight: 600;
                color: #2c3e50;
            }

            .review-actions {
                position: relative;
                display: inline-block;
            }

            .review-settings {
                background: none;
                border: none;
                font-size: 20px;
                color: #7f8c8d;
                cursor: pointer;
                padding: 5px;
            }

            .review-dropdown {
                position: absolute;
                top: 100%;
                right: 0;
                background: white;
                border: 1px solid #e1e8ed;
                border-radius: 10px;
                box-shadow: none;
                padding: 10px 0;
                min-width: 120px;
                visibility: hidden;
                opacity: 0;
                transform: translateY(-10px);
                transition: all 0.3s ease;
                z-index: 1000;
                margin-top: 5px;
            }

            .review-dropdown.show {
                visibility: visible;
                opacity: 1;
                transform: translateY(0);
            }

            .dropdown-item {
                padding: 8px 15px;
                color: #2c3e50;
                text-decoration: none;
                display: block;
                transition: background 0.3s ease;
                white-space: nowrap;
                font-size: 14px;
            }

            .dropdown-item:hover {
                background: #f8f9fa;
                color: #2c3e50;
                text-decoration: none;
            }

            .dropdown-item.delete {
                color: #e74c3c;
            }

            /* Style for delete button in form */
            .dropdown-item.delete[type="submit"] {
                background: none;
                border: none;
                width: 100%;
                text-align: left;
                padding: 8px 15px;
                cursor: pointer;
                font-family: inherit;
                font-size: inherit;
            }

            .review-content {
                color: #7f8c8d;
                line-height: 1.6;
            }

            .add-review {
                background: white;
                border-radius: 15px;
                padding: 25px;
                box-shadow: none;
                width: 100%;
            }

            .review-form {
                display: flex;
                flex-direction: column;
                gap: 20px;
            }

            .review-textarea {
                flex: 1;
                border: 2px solid #ecf0f1;
                border-radius: 10px;
                padding: 15px;
                resize: vertical;
                min-height: 100px;
                font-family: inherit;
            }

            .review-textarea:focus {
                outline: none;
                border-color: #667eea;
            }

            .btn-send-review {
                padding: 15px 30px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border: none;
                border-radius: 25px;
                font-weight: 600;
                cursor: pointer;
                transition: all 0.3s ease;
                align-self: flex-start;
                font-size: 16px;
            }

            .btn-send-review:hover {
                transform: translateY(-2px);
                box-shadow: none;
            }

            /* Modal styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 10000;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0,0,0,0.5);
            }

            .modal-content {
                background-color: white;
                margin: 5% auto;
                padding: 30px;
                border-radius: 15px;
                width: 90%;
                max-width: 500px;
                box-shadow: none;
            }

            .modal-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 20px;
            }

            .modal-title {
                font-size: 1.5rem;
                font-weight: 600;
                color: #2c3e50;
            }

            .close {
                font-size: 28px;
                font-weight: bold;
                color: #7f8c8d;
                cursor: pointer;
            }

            .close:hover {
                color: #2c3e50;
            }

            .star-rating {
                display: flex;
                gap: 10px;
                margin-bottom: 20px;
            }

            .star-input {
                display: none;
            }

            .star-label {
                font-size: 30px;
                color: #bdc3c7;
                cursor: pointer;
                transition: color 0.3s ease;
            }

            .star-label:hover,
            .star-label:hover ~ .star-label,
            .star-input:checked ~ .star-label {
                color: #f39c12;
            }

            .modal-textarea {
                width: 100%;
                border: 2px solid #ecf0f1;
                border-radius: 10px;
                padding: 15px;
                resize: vertical;
                min-height: 120px;
                font-family: inherit;
                margin-bottom: 20px;
            }

            .modal-textarea:focus {
                outline: none;
                border-color: #667eea;
            }

            .modal-buttons {
                display: flex;
                gap: 15px;
                justify-content: flex-end;
            }

            .btn-cancel {
                padding: 10px 20px;
                background: #95a5a6;
                color: white;
                border: none;
                border-radius: 25px;
                cursor: pointer;
            }

            .btn-update {
                padding: 10px 20px;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                color: white;
                border: none;
                border-radius: 25px;
                cursor: pointer;
            }

            /* Review Rating Section Styles */
            .review-rating-section {
                margin-bottom: 20px;
            }

            .rating-label {
                display: block;
                font-weight: 600;
                color: #2c3e50;
                margin-bottom: 10px;
                font-size: 14px;
            }

            .star-rating-input {
                display: flex;
                gap: 5px;
                align-items: center;
                margin-bottom: 15px;
            }

            .star-rating-input .star-input {
                display: none;
            }

            .star-rating-input .star-label {
                font-size: 28px;
                color: #bdc3c7;
                cursor: pointer;
                transition: color 0.3s ease;
                margin: 0;
            }

            /* Star rating hover effect */
            .star-rating-input .star-label:hover,
            .star-rating-input .star-label:hover ~ .star-label {
                color: #f39c12;
            }

            /* Star rating selected state - khi ch?n sao th? N, t?t c? sao t? 1 ??n N s? s�ng */
            .star-rating-input .star-input:checked + .star-label,
            .star-rating-input .star-input:checked ~ .star-label {
                color: #f39c12;
            }

            /* No Reviews Message */
            .no-reviews-message {
                text-align: center;
                padding: 40px 20px;
                background: #f8f9fa;
                border-radius: 10px;
                margin: 20px 0;
            }

            .no-reviews-message p {
                color: #6c757d;
                font-size: 16px;
                margin: 0;
                font-style: italic;
            }

            /* Alert Messages */
            .alert {
                padding: 15px 20px;
                border-radius: 8px;
                margin: 20px 0;
                text-align: center;
            }

            .alert-success {
                background-color: #d4edda;
                border: 1px solid #c3e6cb;
                color: #155724;
            }

            .alert-error {
                background-color: #f8d7da;
                border: 1px solid #f5c6cb;
                color: #721c24;
            }

            .alert p {
                margin: 0;
                font-size: 14px;
                font-weight: 500;
            }

            /* Review Content Section */
            .review-content-section {
                margin-bottom: 20px;
            }

            .review-textarea {
                width: 100%;
                border: 2px solid #ecf0f1;
                border-radius: 10px;
                padding: 15px;
                resize: vertical;
                min-height: 100px;
                font-family: inherit;
                font-size: 14px;
                line-height: 1.5;
                transition: border-color 0.3s ease;
            }

            .review-textarea:focus {
                outline: none;
                border-color: #667eea;
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            }

            .review-textarea::placeholder {
                color: #95a5a6;
                font-style: italic;
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
                                            <li class="active"><a href="home">Home</a></li>
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
                    </div>
                </div>
            </div>
        </div>

        <main>
            <!-- Course Detail Section -->
            <section class="course-detail-section">
                <div class="container">
                    <div class="row">
                        <!-- Course Image -->
                        <div class="col-lg-6">
                            <div class="course-image">
                                <img src="${course.thumbnail_url}" alt="${course.title}">
                            </div>
                        </div>

                        <!-- Course Info -->
                        <div class="col-lg-6">
                            <div class="course-info">
                                <h1 class="course-title">${course.title}</h1>
                                <div class="course-price">$${course.price}</div>
                                <p class="course-description">${course.description}</p>

                                <!-- Rating -->
                                <div class="rating-stars">
                                    <c:forEach begin="1" end="5" var="i">
                                        <span class="star ${i <= averageRating ? '' : 'empty'}">
                                            <i class="fas fa-star"></i>
                                        </span>
                                    </c:forEach>
                                    <span class="rating-count">${reviewCount} ratings</span>
                                </div>
                                <div class="course-topic">
                                    <c:choose>
                                        <c:when test="${not empty topic}">
                                            ${topic.name}
                                        </c:when>
                                        <c:otherwise>
                                            Course topic
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- Action Buttons -->
                                <div class="action-buttons">
                                    <c:choose>
                                        <c:when test="${hasPurchased}">
                                            <a href="customer-view-lesson?courseId=${course.course_id}" class="btn-purchase" style="background: linear-gradient(135deg, #27ae60 0%, #229954 100%);">Start Learning</a>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${isCourseInCart}">
                                                    <button class="btn-purchase in-cart" disabled>In Cart</button>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="add-to-cart" method="POST" style="display: inline;">
                                                        <input type="hidden" name="courseId" value="${course.course_id}">
                                                        <button type="submit" class="btn-purchase">Purchase now</button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <!-- What You Will Learn Section -->
            <section class="what-you-learn">
                <div class="container">
                    <h2 class="section-title">What will you learn from this course</h2>
                    <div class="lessons-list">
                        <c:forEach items="${lessons}" var="lesson" varStatus="status">
                            <div class="lesson-item">
                                <div class="lesson-number">${status.index + 1}</div>
                                <div class="lesson-title">${lesson.title}</div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </section>

            <!-- Reviews Section -->
            <section class="reviews-section">
                <div class="container">
                    <h2 class="section-title">Reviews</h2>

                    <!-- Existing Reviews -->
                    <c:forEach items="${reviews}" var="review">
                        <div class="review-item">
                            <div class="review-header">
                                <div class="review-user">
                                    <div class="user-avatar">
                                        <c:choose>
                                            <c:when test="${review.user_id == user.user_id}">Me</c:when>
                                            <c:otherwise>
                                                <c:set var="rvUser" value="${reviewUsersMap[review.user_id]}" />
                                                <c:choose>
                                                    <c:when test="${not empty rvUser}">
                                                        ${rvUser.firstName} ${rvUser.lastName}
                                                    </c:when>
                                                    <c:otherwise>Username</c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div>
                                        <div class="user-name">
                                            <c:choose>
                                                <c:when test="${review.user_id == user.user_id}">Me</c:when>
                                                <c:otherwise>
                                                    <c:set var="rvUser" value="${reviewUsersMap[review.user_id]}" />
                                                    <c:choose>
                                                        <c:when test="${not empty rvUser}">
                                                            ${rvUser.firstName} ${rvUser.lastName}
                                                        </c:when>
                                                        <c:otherwise>Username</c:otherwise>
                                                    </c:choose>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="rating-stars">
                                            <c:forEach begin="1" end="5" var="i">
                                                <span class="star ${i <= review.rating ? '' : 'empty'}">
                                                    <i class="fas fa-star"></i>
                                                </span>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>

                                <c:if test="${review.user_id == user.user_id}">
                                    <div class="review-actions">
                                        <button class="review-settings" data-review-id="${review.review_id}">
                                            <i class="fas fa-ellipsis-v"></i>
                                        </button>
                                        <div class="review-dropdown" id="dropdown-${review.review_id}">
                                            <a class="dropdown-item update-review" data-review-id="${review.review_id}" data-rating="${review.rating}" data-comment="${review.comment}">Update</a>
                                            <form action="delete-review" method="POST" style="display: inline;">
                                                <input type="hidden" name="reviewId" value="${review.review_id}">
                                                <input type="hidden" name="courseId" value="${course.course_id}">
                                                <button type="submit" class="dropdown-item delete" onclick="return confirm('Are you sure you want to delete this review?')">Delete</button>
                                            </form>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                            <div class="review-content">${review.comment}</div>
                        </div>
                    </c:forEach>
                    <!-- No Reviews Message -->
                    <c:if test="${empty reviews}">
                        <div class="no-reviews-message">
                            <p>Be the first one to review this course!</p>
                        </div>
                    </c:if>
                    <!-- Add Review Form -->
                    <c:if test="${user != null && userReview == null && hasPurchased}">
                        <div class="add-review">
                            <form class="review-form" action="addReview" method="POST">
                                <div class="user-avatar">
                                    Me
                                </div>

                                <!-- Star Rating Section -->
                                <div class="review-rating-section">
                                    <label class="rating-label">Rate this course:</label>
                                    <div class="star-rating-input">
                                        <c:forEach begin="1" end="5" var="i">
                                            <input type="radio" name="rating" value="${i}" id="review-star${i}" class="star-input" required>
                                            <label for="review-star${i}" class="star-label">
                                                <i class="fas fa-star"></i>
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>

                                <!-- Review Content Section -->
                                <div class="review-content-section">
                                    <textarea 
                                        id="reviewComment" 
                                        name="comment" 
                                        class="review-textarea" 
                                        placeholder="Share your thoughts about this course..."
                                        rows="4"
                                        ></textarea>
                                </div>

                                <!-- Hidden inputs for course and user ID -->
                                <input type="hidden" name="courseId" value="${course.course_id}">
                                <input type="hidden" name="userId" value="${user.user_id}">

                                <button type="submit" class="btn-send-review">Send review</button>
                            </form>
                        </div>
                    </c:if>

                    <!-- Message for users who haven't purchased the course -->
                    <c:if test="${user != null && userReview == null && !hasPurchased}">
                        <div class="add-review">
                            <div style="text-align: center; padding: 30px;">
                                <i class="fas fa-lock" style="font-size: 48px; color: #95a5a6; margin-bottom: 15px;"></i>
                                <h4 style="color: #2c3e50; margin-bottom: 10px;">Purchase Required</h4>
                                <p style="color: #7f8c8d; margin-bottom: 20px;">You need to purchase this course to leave a review.</p>
                                <a href="checkout?courseId=${course.course_id}" class="btn-purchase" style="display: inline-block;">Purchase Course</a>
                            </div>
                        </div>
                    </c:if>




                </div>
            </section>
        </main>

        <!-- Update Review Modal -->
        <div id="updateModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Update Review</h3>
                    <span class="close" onclick="closeUpdateModal()">&times;</span>
                </div>
                <form action="update-review" method="POST">
                    <input type="hidden" id="updateReviewId" name="reviewId">
                    <input type="hidden" name="courseId" value="${course.course_id}">
                    <div class="star-rating">
                        <c:forEach begin="1" end="5" var="i">
                            <input type="radio" name="rating" value="${i}" id="star${i}" class="star-input">
                            <label for="star${i}" class="star-label">
                                <i class="fas fa-star"></i>
                            </label>
                        </c:forEach>
                    </div>
                    <textarea class="modal-textarea" name="comment" placeholder="Write your review..." required></textarea>
                    <div class="modal-buttons">
                        <button type="button" class="btn-cancel" onclick="closeUpdateModal()">Cancel</button>
                        <button type="submit" class="btn-update">Update</button>
                    </div>
                </form>
            </div>
        </div>

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
            </div>
            <!-- Footer End-->
        </div>
    </footer> 
    <!-- Scroll Up -->
    <div id="back-top" >
        <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
    </div>

    <!-- JS here - Optimized for performance -->
    <script src="assets/js/vendor/jquery-1.12.4.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>

    <script>


                                              // Toggle review dropdown function - simplified and more reliable
                                              function toggleReviewDropdown(reviewId) {
                                                  console.log('Toggle dropdown for review:', reviewId);

                                                  // Try multiple methods to find the dropdown
                                                  let dropdown = null;

                                                  // Method 1: Try to find by ID
                                                  dropdown = document.getElementById(`dropdown-${reviewId}`);
                                                  if (dropdown) {
                                                      console.log('Found dropdown by ID');
                                                  } else {
                                                      // Method 2: Try to find by data attribute
                                                      const button = document.querySelector(`.review-settings[data-review-id="${reviewId}"]`);
                                                      if (button) {
                                                          const reviewActions = button.closest('.review-actions');
                                                          if (reviewActions) {
                                                              dropdown = reviewActions.querySelector('.review-dropdown');
                                                              console.log('Found dropdown by DOM traversal');
                                                          }
                                                      }
                                                  }

                                                  // Method 3: If still not found, try to find all and match by partial ID
                                                  if (!dropdown) {
                                                      const allDropdowns = document.querySelectorAll('.review-dropdown');
                                                      console.log('Total dropdowns found:', allDropdowns.length);

                                                      // Try to find by partial ID match
                                                      dropdown = Array.from(allDropdowns).find(dd => {
                                                          const ddId = dd.id;
                                                          console.log('Checking dropdown ID:', ddId);
                                                          return ddId && ddId.includes(reviewId.toString());
                                                      });

                                                      if (dropdown) {
                                                          console.log('Found dropdown by partial ID match');
                                                      }
                                                  }

                                                  if (!dropdown) {
                                                      console.error('All methods failed to find dropdown for review ID:', reviewId);
                                                      console.log('Available dropdowns:');
                                                      document.querySelectorAll('.review-dropdown').forEach((dd, index) => {
                                                          console.log(`Dropdown ${index}:`, dd.id, dd);
                                                      });
                                                      return;
                                                  }

                                                  console.log('Successfully found dropdown:', dropdown);

                                                  // Close all other dropdowns first
                                                  const allDropdowns = document.querySelectorAll('.review-dropdown');
                                                  allDropdowns.forEach(dd => {
                                                      if (dd !== dropdown) {
                                                          dd.classList.remove('show');
                                                      }
                                                  });

                                                  // Toggle current dropdown
                                                  const isVisible = dropdown.classList.contains('show');
                                                  console.log('Current dropdown state:', isVisible);

                                                  if (isVisible) {
                                                      dropdown.classList.remove('show');
                                                  } else {
                                                      dropdown.classList.add('show');
                                                  }
                                              }

                                              // Direct toggle function for onclick - more reliable
                                              function toggleReviewDropdownDirect(reviewId, buttonElement) {
                                                  console.log('Direct toggle for review:', reviewId, 'Button:', buttonElement);

                                                  // Find dropdown within the same review-actions container
                                                  const reviewActions = buttonElement.closest('.review-actions');
                                                  if (!reviewActions) {
                                                      console.error('Review actions container not found');
                                                      return;
                                                  }

                                                  const dropdown = reviewActions.querySelector('.review-dropdown');
                                                  if (!dropdown) {
                                                      console.error('Dropdown not found in review actions');
                                                      return;
                                                  }

                                                  console.log('Found dropdown directly:', dropdown);

                                                  // Close all other dropdowns first
                                                  const allDropdowns = document.querySelectorAll('.review-dropdown');
                                                  allDropdowns.forEach(dd => {
                                                      if (dd !== dropdown) {
                                                          dd.classList.remove('show');
                                                      }
                                                  });

                                                  // Toggle current dropdown
                                                  const isVisible = dropdown.classList.contains('show');
                                                  console.log('Current dropdown state:', isVisible);

                                                  if (isVisible) {
                                                      dropdown.classList.remove('show');
                                                  } else {
                                                      dropdown.classList.add('show');
                                                  }
                                              }




                                              document.addEventListener('click', function (event) {
                                                  // Handle review settings button clicks
                                                  if (event.target.closest('.review-settings')) {
                                                      const button = event.target.closest('.review-settings');
                                                      const reviewId = button.getAttribute('data-review-id');
                                                      console.log('Button clicked, data-review-id:', reviewId);
                                                      console.log('Button element:', button);

                                                      if (reviewId) {
                                                          // Use direct method with button element
                                                          toggleReviewDropdownDirect(reviewId, button);
                                                      } else {
                                                          console.error('No data-review-id found on button');
                                                          console.log('All review-settings buttons:');
                                                          document.querySelectorAll('.review-settings').forEach((btn, index) => {
                                                              console.log(`Button ${index}:`, btn.getAttribute('data-review-id'), btn);
                                                          });
                                                      }
                                                      return; // Exit early to avoid closing dropdown immediately
                                                  }

                                                  // Handle update review clicks
                                                  if (event.target.closest('.update-review')) {
                                                      const updateLink = event.target.closest('.update-review');
                                                      const reviewId = updateLink.getAttribute('data-review-id');
                                                      const rating = updateLink.getAttribute('data-rating');
                                                      const comment = updateLink.getAttribute('data-comment');

                                                      console.log('Update review clicked:', {reviewId, rating, comment});
                                                      openUpdateModal(reviewId, rating, comment);
                                                      return;
                                                  }

                                                  // Handle clicks outside to close dropdowns
                                                  if (!event.target.closest('.review-actions')) {
                                                      document.querySelectorAll('.review-dropdown').forEach(dropdown => {
                                                          dropdown.classList.remove('show');
                                                      });
                                                  }
                                              });

                                              // Keyboard support for accessibility
                                              document.addEventListener('keydown', function (event) {
                                                  if (event.key === 'Escape') {
                                                      if (!dropdownElements) {
                                                          dropdownElements = document.querySelectorAll('.review-dropdown');
                                                      }
                                                      dropdownElements.forEach(dropdown => {
                                                          dropdown.classList.remove('show');
                                                      });
                                                  }
                                              });

                                              // Update review modal
                                              function openUpdateModal(reviewId, rating, comment) {
                                                  document.getElementById('updateReviewId').value = reviewId;
                                                  document.querySelector('input[name="rating"][value="' + rating + '"]').checked = true;
                                                  document.querySelector('.modal-textarea[name="comment"]').value = comment;
                                                  document.getElementById('updateModal').style.display = 'block';
                                              }

                                              function closeUpdateModal() {
                                                  document.getElementById('updateModal').style.display = 'none';
                                              }

                                              // Close modal when clicking outside
                                              window.onclick = function (event) {
                                                  const modal = document.getElementById('updateModal');
                                                  if (event.target === modal) {
                                                      closeUpdateModal();
                                                  }
                                              }

                                              // Star rating functionality
                                              document.addEventListener('DOMContentLoaded', function () {
                                                  const starInputs = document.querySelectorAll('.star-rating-input .star-input');
                                                  const starLabels = document.querySelectorAll('.star-rating-input .star-label');

                                                  starInputs.forEach((input, index) => {
                                                      input.addEventListener('change', function () {
                                                          const rating = parseInt(this.value);

                                                          // Reset all stars to gray
                                                          starLabels.forEach(label => {
                                                              label.style.color = '#bdc3c7';
                                                          });

                                                          // Color stars from 1 to rating with gold
                                                          for (let i = 0; i < rating; i++) {
                                                              starLabels[i].style.color = '#f39c12';
                                                          }
                                                      });
                                                  });

                                                  // Hover effects
                                                  starLabels.forEach((label, index) => {
                                                      label.addEventListener('mouseenter', function () {
                                                          // Color stars from 1 to current hover position
                                                          for (let i = 0; i <= index; i++) {
                                                              starLabels[i].style.color = '#f39c12';
                                                          }
                                                      });

                                                      label.addEventListener('mouseleave', function () {
                                                          // Reset to selected state
                                                          const checkedInput = document.querySelector('.star-rating-input .star-input:checked');
                                                          if (checkedInput) {
                                                              const rating = parseInt(checkedInput.value);
                                                              starLabels.forEach((label, i) => {
                                                                  label.style.color = i < rating ? '#f39c12' : '#bdc3c7';
                                                              });
                                                          } else {
                                                              // No star selected, reset all to gray
                                                              starLabels.forEach(label => {
                                                                  label.style.color = '#bdc3c7';
                                                              });
                                                          }
                                                      });
                                                  });
                                              });


    </script>

</body>
</html>