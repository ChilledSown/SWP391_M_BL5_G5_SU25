<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Manage Video Quiz | Seller Dashboard</title>
    <meta name="description" content="Seller dashboard for managing video quizzes">
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
        .table td.actions {
            white-space: nowrap;
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
            padding: 12px;
            font-size: 1rem;
            font-weight: 400;
            height: 48px;
            transition: border-color 0.3s ease;
        }
        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
        }
        .form-control::placeholder {
            color: #6c757d;
            font-weight: 400;
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
        .btn-action, .btn-create, .btn-reset {
            background-color: #ff8243;
            color: white;
            border: none;
            border-radius: 8px;
            padding: 10px 20px;
            margin: 2px;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            transition: background-color 0.2s ease-in-out;
        }
        .btn-action:hover, .btn-create:hover, .btn-reset:hover {
            background-color: #e67030;
        }
        .btn-action i, .btn-create i, .btn-reset i {
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
        .footer-wrapper {
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
        .search-container {
            display: flex;
            align-items: center;
            gap: 10px;
            flex-wrap: wrap;
        }
        .alert {
            margin-bottom: 20px;
        }
        .error-message {
            color: #dc3545;
            font-size: 0.9rem;
            margin-top: 5px;
            display: none;
        }
        .error-message.show {
            display: block;
        }
        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin-top: 20px;
        }
        .pagination a, .pagination span {
            margin: 0 5px;
            padding: 8px 12px;
            border-radius: 4px;
            text-decoration: none;
            color: #007bff;
            font-weight: 500;
            transition: all 0.3s ease;
        }
        .pagination a:hover {
            background-color: #007bff;
            color: #ffffff;
        }
        .pagination .active {
            background-color: #007bff;
            color: #ffffff;
            cursor: default;
        }
        .pagination .disabled {
            color: #6c757d;
            cursor: not-allowed;
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
            .form-group {
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
            .search-container {
                flex-direction: column;
                align-items: stretch;
            }
            .table-responsive {
                overflow-x: auto;
            }
            .pagination {
                flex-wrap: wrap;
            }
            .pagination a, .pagination span {
                margin: 5px;
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
                                <li class="nav-item"><a href="#overview" class="nav-link">Overview</a></li>
                                <li class="nav-item"><a href="listCourses" class="nav-link">Courses</a></li>
                                <li class="nav-item"><a href="instructorvideoquiz" class="nav-link active">Video Quiz</a></li>
                                <li class="nav-item"><a href="listBlogsInstructor" class="nav-link">Blogs</a></li>
                                <li class="nav-item"><a href="balance" class="nav-link">Balance</a></li>
                                <li class="nav-item"><a href="listReviews" class="nav-link">Reviews</a></li>
                            </ul>
                        </div>
                        <div class="col-lg-9 col-md-8 content">
                            <h2>Video Quiz Management</h2>
                            <p>Manage your video quizzes here.</p>
                            <c:if test="${not empty message}">
                                <div class="alert alert-success" role="alert">${fn:escapeXml(message)}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger" role="alert">${fn:escapeXml(error)}</div>
                            </c:if>
                            <form action="instructorvideoquiz" method="get" class="mb-4" id="searchForm">
                                <div class="row">
                                    <div class="col-md-6 form-group">
                                        <label for="title" class="mb-2">Search by Question</label>
                                        <input type="text" class="form-control" name="title" id="title" value="${fn:escapeXml(param.title)}" placeholder="Enter quiz question">
                                    </div>
                                    <div class="col-md-6 form-group">
                                        <label for="lessonId" class="mb-2">Filter by Lesson</label>
                                        <select name="lessonId" id="lessonId" class="form-control" onchange="this.form.submit()">
                                            <option value="">All Lessons</option>
                                            <c:forEach var="lesson" items="${lessons}">
                                                <option value="${lesson.lessonId}" ${lesson.lessonId == param.lessonId ? 'selected' : ''}>
                                                    ${fn:escapeXml(lesson.title)}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-12 form-group">
                                        <div class="search-container">
                                            <button type="submit" class="btn-action" title="Search">
                                                <i class="fas fa-search"></i>
                                            </button>
                                            <a href="instructorvideoquiz" class="btn-reset" title="Reset Search">
                                                <i class="fas fa-undo"></i>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="page" value="${param.page != null ? fn:escapeXml(param.page) : '1'}" id="pageInput" />
                            </form>
                            <a href="instructorvideoquiz?action=create" class="btn-create mb-3" title="Create New Video Quiz">
                                <i class="fas fa-plus"></i>
                            </a>
                            <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th>Video Quiz ID</th>
                                            <th>Lesson</th>
                                            <th>Timestamp (seconds)</th>
                                            <th>Question</th>
                                            <th>Is Active</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="quiz" items="${videoQuizzes}">
                                            <tr>
                                                <td>${quiz.videoQuizId}</td>
                                                <td>${fn:escapeXml(quiz.lessonTitle)}</td>
                                                <td>${quiz.timestamp}</td>
                                                <td>${fn:escapeXml(quiz.question)}</td>
                                                <td>${quiz.isActive ? 'Yes' : 'No'}</td>
                                                <td class="actions">
                                                    <a href="instructorvideoquiz?action=edit&videoQuizId=${quiz.videoQuizId}" class="btn-action" title="Edit">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="instructorvideoquiz?action=delete&videoQuizId=${quiz.videoQuizId}" class="btn-action" title="Delete" onclick="return confirm('Are you sure you want to delete this quiz?');">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </a>
                                                    <a href="instructorvideoquiz?action=view&videoQuizId=${quiz.videoQuizId}" class="btn-action" title="View">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty videoQuizzes}">
                                            <tr>
                                                <td colspan="6" class="text-center">No video quizzes found.</td>
                                            </tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                            <c:if test="${not empty videoQuizzes}">
                                <div class="pagination">
                                    <c:if test="${currentPage > 1}">
                                        <a href="#" data-page="${currentPage - 1}">Previous</a>
                                    </c:if>
                                    <c:if test="${currentPage <= 1}">
                                        <span class="disabled">Previous</span>
                                    </c:if>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <c:choose>
                                            <c:when test="${i == currentPage}">
                                                <span class="active">${i}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="#" data-page="${i}">${i}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${currentPage < totalPages}">
                                        <a href="#" data-page="${currentPage + 1}">Next</a>
                                    </c:if>
                                    <c:if test="${currentPage >= totalPages}">
                                        <span class="disabled">Next</span>
                                    </c:if>
                                </div>
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
                $(document).ready(function() {
                    console.log('instructorvideoquiz.jsp loaded');

                    $('.pagination a').on('click', function(e) {
                        e.preventDefault();
                        const page = $(this).data('page');
                        $('#pageInput').val(page);
                        $('#searchForm').submit();
                    });

                    $('#searchForm').validate({
                        rules: {
                            title: { maxlength: 255 },
                            lessonId: { digits: true, min: 0 }
                        },
                        messages: {
                            title: { maxlength: "Search query cannot exceed 255 characters." },
                            lessonId: {
                                digits: "Lesson ID must be a number.",
                                min: "Lesson ID must be non-negative."
                            }
                        },
                        errorElement: 'div',
                        errorClass: 'error-message show',
                        errorPlacement: function(error, element) {
                            error.insertAfter(element);
                        },
                        highlight: function(element) {
                            $(element).addClass('is-invalid').removeClass('is-valid');
                        },
                        unhighlight: function(element) {
                            $(element).removeClass('is-invalid').addClass('is-valid');
                        },
                        submitHandler: function(form) {
                            console.log('Submitting search form:', $(form).serialize());
                            form.submit();
                        }
                    });
                });
            </script>
        </body>
</html>