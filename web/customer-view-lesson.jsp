<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>View Lesson | Education</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <link rel="stylesheet" href="assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <style>
            body { font-family: "Helvetica Neue", Arial, sans-serif; }
            .lesson-layout { padding: 60px 0; background: #f7f9fc; }
            .sidebar { background: #ffffff; border-radius: 12px; box-shadow: 0 6px 22px rgba(0,0,0,0.06); }
            .sidebar .sidebar-title { padding: 16px 20px; font-weight: 700; color: #2c3e50; border-bottom: 1px solid #eef2f7; }
            .lesson-link { display: block; padding: 14px 18px; color: #2c3e50; text-decoration: none; border-bottom: 1px solid #f0f2f5; transition: background .2s ease; }
            .lesson-link:hover { background: #f7f9fc; }
            .lesson-link.active { background: #eef2ff; color: #3445d4; font-weight: 600; }
            .quiz-box { padding: 18px; background: #f8fafc; }
            .player-card { background: #ffffff; border-radius: 12px; box-shadow: 0 6px 22px rgba(0,0,0,0.06); padding: 16px; }
            .video-wrapper { position: relative; background: #000; border-radius: 10px; overflow: hidden; min-height: 360px; }
            
        </style>
    </head>
    <body>
        <header>
            <div class="header-area header-transparent">
                <div class="main-header ">
                    <div class="header-bottom  header-sticky">
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
                                                    <li class="active"><a href="home">Home</a></li>
                                                    <li><a href="courses">Courses</a></li>
                                                    <li><a href="blog">Blog</a></li>
                                                    <li><a href="customer-course-detail?id=${param.courseId}">MyPaidCourses</a></li>
                                                    <li class="button-header"><a href="logout" class="btn btn3">Logout</a></li>
                                                </ul>
                                            </nav>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-12"><div class="mobile_menu d-block d-lg-none"></div></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <main class="lesson-layout">
            <div class="container">
                <div class="row g-4">
                    <!-- Sidebar lessons -->
                    <div class="col-lg-3">
                        <div class="sidebar">
                            <div class="sidebar-title">Lessons</div>
                            <c:forEach items="${lessons}" var="lesson" varStatus="s">
                                <a class="lesson-link ${activeLesson != null && lesson.lessonId == activeLesson.lessonId ? 'active' : ''}"
                                   href="customer-view-lesson?courseId=${courseId}&lessonId=${lesson.lessonId}">
                                    ${s.index + 1} - ${lesson.title}
                                </a>
                            </c:forEach>
                            <div class="quiz-box">
                                <c:choose>
                                    <c:when test="${not empty quizzes}">
                                        <a class="btn w-100 btn-primary" href="quizDetail?lessonId=${activeLesson.lessonId}&quizId=${quizzes[0].quizId}">Lesson Quiz</a>
                                        <div class="text-muted mt-2" style="font-size: 13px">${fn:length(quizzes)} questions available</div>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn w-100 btn-secondary" disabled>No quiz available</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <!-- Main content -->
                    <div class="col-lg-9">
                        <div class="player-card">
                            <div class="video-wrapper">
                                <c:choose>
                                    <c:when test="${activeLesson != null && activeLesson.videoUrl != null && (activeLesson.videoUrl.contains('youtube.com') || activeLesson.videoUrl.contains('youtu.be'))}">
                                        <iframe width="100%" height="480" src="https://www.youtube.com/embed/${fn:substringAfter(activeLesson.videoUrl, 'v=')}" title="Lesson video" frameborder="0" allowfullscreen></iframe>
                                    </c:when>
                                    <c:when test="${activeLesson != null && activeLesson.videoUrl != null && activeLesson.videoUrl ne ''}">
                                        <video width="100%" height="480" controls>
                                            <source src="${activeLesson.videoUrl}" type="video/mp4">
                                            Your browser does not support HTML5 video.
                                        </video>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="color:#fff;display:flex;align-items:center;justify-content:center;height:360px;">No video available for this lesson</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

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
                                            <div class="footer-pera"><p>Learn better every day.</p></div>
                                        </div>
                                        <div class="footer-social">
                                            <a href="#"><i class="fab fa-twitter"></i></a>
                                            <a href="#"><i class="fab fa-facebook-f"></i></a>
                                            <a href="#"><i class="fab fa-pinterest-p"></i></a>
                                        </div>
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
                                <div class="col-xl-12 ">
                                    <div class="footer-copy-right text-center">
                                        <p>Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </footer>

        <script src="assets/js/vendor/jquery-1.12.4.min.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <script src="assets/js/main.js"></script>
    </body>
    </html>


