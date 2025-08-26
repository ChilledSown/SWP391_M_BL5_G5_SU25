<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Video Quiz Detail | Seller Dashboard</title>
    <meta name="description" content="Seller dashboard for viewing video quiz details">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="manifest" href="site.webmanifest">
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap">
    <style>
        body {
            background: linear-gradient(120deg, #7F7FD5, #E86ED0);
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
        .quiz-detail-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background: #fff;
            border: 1px solid #ced4da;
            border-radius: 6px;
        }
        .quiz-detail-table th, .quiz-detail-table td {
            padding: 12px;
            border: 1px solid #ced4da;
            text-align: left;
            font-size: 1rem;
            color: #495057;
        }
        .quiz-detail-table th {
            background-color: #f8f9fa;
            font-weight: 500;
            width: 30%;
        }
        .quiz-detail-table td {
            vertical-align: top;
            word-break: break-word;
        }
        .btn-back, .btn-action {
            background-color: #ff8243;
            color: white;
            border: none;
            border-radius: 8px;
            padding: 10px 20px;
            font-size: 14px;
            transition: background-color 0.2s ease-in-out;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            margin: 5px;
        }
        .btn-action {
            width: 40px;
            height: 40px;
        }
        .btn-back:hover, .btn-action:hover {
            background-color: #e67030;
        }
        .btn-back i, .btn-action i {
            font-size: 16px;
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
        .content p {
            color: #495057;
            font-size: 1.1rem;
        }
        .alert {
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
        }
        @media (max-width: 767px) {
            .quiz-detail-table th, .quiz-detail-table td {
                font-size: 0.9rem;
                padding: 8px;
            }
            .btn-back, .btn-action {
                width: 100%;
                text-align: center;
            }
            .btn-action {
                width: 36px;
                height: 36px;
            }
        }
    </style>
</head>
<body>
    <c:if test="${empty sessionScope.user}">
        <c:redirect url="login"/>
    </c:if>
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
        </header>
        <main>
            <section class="dashboard-area section-padding40">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-3 col-md-4 sidebar">
                            <ul class="nav flex-column" id="sidebarNav">
                                <li class="nav-item"><a href="DashBoardSeller.jsp" class="nav-link">Overview</a></li>
                                <li class="nav-item"><a href="listCousera" class="nav-link">Courses</a></li>
                                <li class="nav-item"><a href="instructorvideoquiz" class="nav-link active">Video Quiz</a></li>
                                <li class="nav-item"><a href="listBlogsSeller" class="nav-link">Blogs</a></li>
                                <li class="nav-item"><a href="balance" class="nav-link">Balance</a></li>
                                <li class="nav-item"><a href="reviews.jsp" class="nav-link">Reviews</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Video Quiz Detail</h2>
                            <p>View the details of the selected video quiz.</p>
                            <c:if test="${not empty message}">
                                <div class="alert alert-success" role="alert">${fn:escapeXml(message)}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger" role="alert">${fn:escapeXml(error)}</div>
                            </c:if>
                            <c:if test="${not empty videoQuiz}">
                                <table class="quiz-detail-table">
                                    <tr>
                                        <th>Video Quiz ID</th>
                                        <td>${videoQuiz.videoQuizId}</td>
                                    </tr>
                                    <tr>
                                        <th>Lesson</th>
                                        <td>${fn:escapeXml(videoQuiz.lessonTitle)}</td>
                                    </tr>
                                    <tr>
                                        <th>Timestamp (seconds)</th>
                                        <td>${videoQuiz.timestamp}</td>
                                    </tr>
                                    <tr>
                                        <th>Question</th>
                                        <td>${fn:escapeXml(videoQuiz.question)}</td>
                                    </tr>
                                    <tr>
                                        <th>Answer Options</th>
                                        <td>${fn:escapeXml(videoQuiz.answerOptions)}</td>
                                    </tr>
                                    <tr>
                                        <th>Correct Answer</th>
                                        <td>${fn:escapeXml(videoQuiz.correctAnswer)}</td>
                                    </tr>
                                    <tr>
                                        <th>Explanation</th>
                                        <td>${fn:escapeXml(videoQuiz.explanation)}</td>
                                    </tr>
                                    <tr>
                                        <th>Active</th>
                                        <td>${videoQuiz.isActive ? 'Yes' : 'No'}</td>
                                    </tr>
                                    <tr>
                                        <th>Created At</th>
                                        <td><fmt:formatDate value="${videoQuiz.createdAt}" pattern="yyyy-MM-dd"/></td>
                                    </tr>
                                    <tr>
                                        <th>Updated At</th>
                                        <td><fmt:formatDate value="${videoQuiz.updatedAt}" pattern="yyyy-MM-dd"/></td>
                                    </tr>
                                </table>
                             
                                <a href="instructorvideoquiz" class="btn-back" title="Back to List">
                                    <i class="fas fa-arrow-left"></i> Back
                                </a>
                            </c:if>
                            <c:if test="${empty videoQuiz}">
                                <p>No quiz details available.</p>
                                <a href="instructorvideoquiz" class="btn-back" title="Back to List">
                                    <i class="fas fa-arrow-left"></i> Back
                                </a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </section>
        </main>
        <footer>
            <div class="footer-wrapper footer-bg">
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
            </footer>
            <div id="back-top">
                <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
            </div>
            <script src="${pageContext.request.contextPath}/assets/js/vendor/jquery-1.12.4.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/jquery.validate.min.js"></script>
            <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
            <script>
                document.addEventListener('DOMContentLoaded', function() {
                    console.log('instructorvideodetail.jsp loaded');
                });
            </script>
        </body>
</html>