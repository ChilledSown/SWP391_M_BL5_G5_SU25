<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Manage Balance | Instructor Dashboard</title>
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
        .table {
            background: #ffffff;
            border-radius: 8px;
            overflow: hidden;
        }
        .table th {
            background-color: #007bff;
            color: #ffffff;
            font-weight: 600;
            padding: 15px;
        }
        .table td {
            vertical-align: middle;
            padding: 15px;
            color: #343a40;
        }
        .table tbody tr:hover {
            background-color: #f1f3f5;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-control {
            border-radius: 6px;
            border: 1px solid #ced4da;
            padding: 10px;
            transition: border-color 0.3s ease;
        }
        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
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
        .btn-action {
            background-color: #ff8243;
            color: white;
            border: none;
            border-radius: 8px;
            padding: 6px 8px;
            margin: 2px;
            font-size: 14px;
            width: 32px;
            height: 32px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            transition: background-color 0.2s ease-in-out;
        }
        .btn-action:hover {
            background-color: #e67030;
        }
        .btn-action i {
            font-size: 16px;
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
        .error-message {
            color: #dc3545;
            font-size: 1rem;
            margin-bottom: 20px;
        }
        .success-message {
            color: #28a745;
            font-size: 1rem;
            margin-bottom: 20px;
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
        .filter-form {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        .status-form {
            display: inline-block;
        }
        .status-select {
            width: 120px;
            padding: 5px;
            margin: 0;
        }
        .action-cell {
            white-space: nowrap; /* Prevent wrapping */
        }
        .clearfix {
            clear: both;
            margin-bottom: 20px;
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
            .filter-form {
                flex-direction: column;
            }
            .status-select {
                width: 100px; /* Adjusted for smaller screens */
            }
        }
        @media (max-width: 767px) {
            .form-inline .form-group {
                margin-bottom: 15px;
                width: 100%;
            }
            .form-control {
                width: 100%;
            }
            .btn-primary {
                width: 100%;
                text-align: center;
            }
            .dashboard-card {
                margin-bottom: 15px;
            }
            .action-cell {
                display: block; /* Stack elements on mobile */
            }
            .status-select {
                width: 100%;
            }
            .filter-form {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
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
                        <div class="col-lg-3 col-md-4 sidebar">
                            <ul class="nav flex-column" id="sidebarNav">
                                <li class="nav-item"><a href="instructor_Doashboard.jsp" class="nav-link">Overview</a></li>
                                <li class="nav-item"><a href="listCourses" class="nav-link">Courses</a></li>
                                <li class="nav-item"><a href="listBlogsInstructor" class="nav-link">Blogs</a></li>
                                <li class="nav-item"><a href="balance" class="nav-link active">Balance</a></li>
                                <li class="nav-item"><a href="listReviews" class="nav-link">Reviews</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Balance Management</h2>
                            <p>View your earnings and transaction history here.</p>
                            <c:if test="${not empty errorMessage}">
                                <div class="error-message">${errorMessage}</div>
                            </c:if>
                            <c:if test="${not empty message}">
                                <div class="success-message">${message}</div>
                            </c:if>
                            <div class="dashboard-card">
                                <h4>Current Balance</h4>
                                <p><fmt:formatNumber value="${balance}" type="currency" currencySymbol="$" maxFractionDigits="2" /></p>
                            </div>
                            <form action="${pageContext.request.contextPath}/balance" method="get" class="filter-form">
                                <div class="form-group">
                                    <label for="fromDate">From Date:</label>
                                    <input type="date" name="fromDate" id="fromDate" class="form-control" value="${fromDate}">
                                </div>
                                <div class="form-group">
                                    <label for="toDate">To Date:</label>
                                    <input type="date" name="toDate" id="toDate" class="form-control" value="${toDate}">
                                </div>
                                <div class="form-group">
                                    <label for="searchTerm">Search:</label>
                                    <input type="text" name="searchTerm" id="searchTerm" class="form-control" value="${searchTerm}" placeholder="Enter description...">
                                </div>
                                <button type="submit" class="btn btn-primary">Filter</button>
                            </form>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Date</th>
                                        <th>Description</th>
                                        <th>Amount</th>
                                        <th>Method</th>
                                        <th>Status</th>
                                        <th>Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="transaction" items="${transactions}">
                                        <tr>
                                            <td><fmt:formatDate value="${transaction.orderDate}" pattern="yyyy-MM-dd" /></td>
                                            <td>${transaction.description}</td>
                                            <td><fmt:formatNumber value="${transaction.amount}" type="currency" currencySymbol="$" maxFractionDigits="2" /></td>
                                            <td>${transaction.paymentMethod}</td>
                                            <td>
                                                <form action="${pageContext.request.contextPath}/balance" method="post" class="status-form">
                                                    <input type="hidden" name="action" value="updateStatus">
                                                    <input type="hidden" name="orderId" value="${transaction.orderId}">
                                                    <input type="hidden" name="fromDate" value="${fromDate}">
                                                    <input type="hidden" name="toDate" value="${toDate}">
                                                    <input type="hidden" name="searchTerm" value="${searchTerm}">
                                                    <select name="status" class="form-control status-select" onchange="this.form.submit()">
                                                        <option value="completed" ${transaction.paymentStatus == 'completed' ? 'selected' : ''}>Completed</option>
                                                        <option value="cancelled" ${transaction.paymentStatus == 'cancelled' ? 'selected' : ''}>Cancelled</option>
                                                    </select>
                                                </form>
                                            </td>
                                            <td class="action-cell">
                                                <a href="balanceDetail?orderId=${transaction.orderId}" class="btn-action" title="Detail">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty transactions}">
                                        <tr>
                                            <td colspan="6" class="text-center">No transactions found.</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                            <div class="clearfix"></div> <!-- Ensures pagination is below the table -->
                            <jsp:include page="pagination.jsp" />
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