<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>Quiz | Education</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico" />

    <!-- CSS here -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/fontawesome-all.min.css" />
    <link rel="stylesheet" href="assets/css/style.css" />

    <style>
        body {
            font-family: "Helvetica Neue", Arial, sans-serif;
            background: #f7f9fc;
        }

        /* Header Styling - Consistent with other customer pages */
        .combined-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            position: relative;
            overflow: hidden;
        }
        .combined-header::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
        }
        .header-top {
            padding: 20px 0;
            border-bottom: 1px solid rgba(255, 255, 255, 0.2);
            position: relative;
            z-index: 2;
        }
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
        #navigation li a::after {
            content: "";
            position: absolute;
            bottom: 0;
            left: 50%;
            width: 0;
            height: 2px;
            background: white;
            transition: all 0.3s ease;
            transform: translateX(-50%);
        }
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
        .logo img {
            max-height: 40px;
        }

        /* Main Layout - Coursera Style */
        .main-container {
            display: flex;
            height: calc(100vh - 80px);
            background: #f7f9fc;
        }

        /* Sidebar */
        .sidebar {
            width: 320px;
            background: white;
            border-right: 1px solid #e1e5e9;
            display: flex;
            flex-direction: column;
            overflow: hidden;
        }

        .sidebar-header {
            padding: 20px 24px;
            border-bottom: 1px solid #e1e5e9;
            background: #f8f9fa;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .course-title {
            font-size: 16px;
            font-weight: 600;
            color: #1f1f1f;
            margin: 0;
            line-height: 1.4;
        }

        .close-btn {
            background: none;
            border: none;
            color: #6a6f73;
            font-size: 18px;
            cursor: pointer;
            padding: 4px;
            border-radius: 4px;
            transition: all 0.2s ease;
        }

        .close-btn:hover {
            background: #e1e5e9;
            color: #1f1f1f;
        }

        .quiz-list {
            flex: 1;
            overflow-y: auto;
            list-style: none;
            margin: 0;
            padding: 0;
        }

        .quiz-item {
            border-bottom: 1px solid #f0f2f5;
            transition: all 0.2s ease;
        }

        .quiz-item a {
            display: flex;
            align-items: center;
            padding: 16px 24px;
            color: #1f1f1f;
            text-decoration: none;
            transition: all 0.2s ease;
            position: relative;
        }

        .quiz-item:hover {
            background: #f8f9fa;
        }

        .quiz-item.active {
            background: #e8f4fd;
            border-left: 4px solid #0056b3;
        }

        .quiz-item.active a {
            color: #0056b3;
            font-weight: 600;
        }

        .quiz-number {
            width: 24px;
            height: 24px;
            background: #e9ecef;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
            font-weight: 600;
            margin-right: 12px;
            color: #6c757d;
        }

        .quiz-item.active .quiz-number {
            background: #0056b3;
            color: white;
        }

        .quiz-content {
            flex: 1;
        }

        .quiz-title {
            font-size: 14px;
            font-weight: 500;
            line-height: 1.4;
            margin-bottom: 4px;
        }

        .quiz-lesson {
            font-size: 12px;
            color: #6c757d;
        }

        /* Main Content */
        .main-content {
            flex: 1;
            display: flex;
            flex-direction: column;
            background: white;
            padding: 30px;
            overflow-y: auto;
        }

        /* Quiz Header */
        .quiz-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
            border-radius: 12px;
            margin-bottom: 30px;
        }

        .quiz-header .quiz-title {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .quiz-header .quiz-subtitle {
            font-size: 16px;
            opacity: 0.9;
        }

        /* Score Section */
        .score-section {
            background: #f8f9fa;
            padding: 25px 30px;
            border-radius: 12px;
            border: 1px solid #e9ecef;
            margin-bottom: 30px;
            display: none;
        }

        .score-section.show {
            display: block;
        }

        .score-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .score-text {
            font-size: 24px;
            font-weight: 700;
            color: #2c3e50;
        }

        .passing-requirement {
            font-size: 14px;
            color: #6c757d;
        }

        .try-again-btn {
            background: #6c757d;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .try-again-btn:hover {
            background: #5a6268;
        }

        .close-btn {
            background: none;
            border: none;
            color: #6c757d;
            font-size: 20px;
            cursor: pointer;
            padding: 0;
            margin-left: 10px;
        }

        /* Quiz Content */
        .quiz-content {
            margin-bottom: 30px;
        }

        .question-container {
            background: white;
            border: 1px solid #e9ecef;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 25px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
        }

        .question-number {
            font-size: 18px;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 20px;
        }

        .question-text {
            font-size: 16px;
            color: #495057;
            line-height: 1.6;
            margin-bottom: 20px;
        }

        .options-container {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .option-item {
            display: flex;
            align-items: center;
            padding: 12px 16px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .option-item:hover {
            border-color: #667eea;
            background: #f8f9ff;
        }

        .option-item input[type="radio"] {
            margin-right: 12px;
            transform: scale(1.2);
        }

        .option-label {
            font-size: 15px;
            color: #495057;
            cursor: pointer;
            flex: 1;
        }

        /* Answer Feedback */
        .answer-feedback {
            background: #e8f5e8;
            border: 1px solid #28a745;
            border-radius: 8px;
            padding: 16px;
            margin-top: 15px;
            display: none;
        }

        .answer-feedback.show {
            display: block;
        }

        .correct-answer {
            font-weight: 600;
            color: #155724;
            margin-bottom: 8px;
        }

        .explanation {
            color: #155724;
            line-height: 1.5;
        }

        /* Submit Button */
        .submit-section {
            padding: 20px 0;
            text-align: center;
            border-top: 1px solid #e9ecef;
        }

        .submit-btn {
            background: #28a745;
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .submit-btn:hover {
            background: #218838;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(40, 167, 69, 0.3);
        }

        /* Congratulations Section */
        .congratulations-section {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            padding: 40px 30px;
            text-align: center;
            border-radius: 12px;
            display: none;
            margin-bottom: 30px;
        }

        .congratulations-section.show {
            display: block;
        }

        .congratulations-text {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 20px;
        }

        .navigation-buttons {
            display: flex;
            gap: 15px;
            justify-content: center;
            margin-top: 20px;
        }

        .nav-btn {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: 2px solid rgba(255, 255, 255, 0.3);
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
        }

        .nav-btn:hover {
            background: rgba(255, 255, 255, 0.3);
            color: white;
            text-decoration: none;
        }

        /* Score Section */
        .score-section {
            background: #f8f9fa;
            padding: 25px 30px;
            border-bottom: 1px solid #e9ecef;
            display: none;
        }

        .score-section.show {
            display: block;
        }

        .score-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }

        .score-text {
            font-size: 24px;
            font-weight: 700;
            color: #2c3e50;
        }

        .passing-requirement {
            font-size: 14px;
            color: #6c757d;
        }

        .try-again-btn {
            background: #6c757d;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .try-again-btn:hover {
            background: #5a6268;
        }

        .close-btn {
            background: none;
            border: none;
            color: #6c757d;
            font-size: 20px;
            cursor: pointer;
            padding: 0;
            margin-left: 10px;
        }

        /* Quiz Content */
        .quiz-content {
            padding: 30px;
        }

        .question-container {
            margin-bottom: 30px;
        }

        .question-number {
            font-size: 18px;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 15px;
        }

        .question-text {
            font-size: 16px;
            color: #495057;
            line-height: 1.6;
            margin-bottom: 20px;
        }

        .options-container {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .option-item {
            display: flex;
            align-items: center;
            padding: 12px 16px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .option-item:hover {
            border-color: #667eea;
            background: #f8f9ff;
        }

        .option-item input[type="radio"] {
            margin-right: 12px;
            transform: scale(1.2);
        }

        .option-label {
            font-size: 15px;
            color: #495057;
            cursor: pointer;
            flex: 1;
        }

        /* Answer Feedback */
        .answer-feedback {
            background: #e8f5e8;
            border: 1px solid #28a745;
            border-radius: 8px;
            padding: 16px;
            margin-top: 15px;
            display: none;
        }

        .answer-feedback.show {
            display: block;
        }

        .correct-answer {
            font-weight: 600;
            color: #155724;
            margin-bottom: 8px;
        }

        .explanation {
            color: #155724;
            line-height: 1.5;
        }

        /* Submit Button */
        .submit-section {
            padding: 20px 30px;
            background: #f8f9fa;
            border-top: 1px solid #e9ecef;
            text-align: center;
        }

        .submit-btn {
            background: #28a745;
            color: white;
            border: none;
            padding: 12px 30px;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
        }

        .submit-btn:hover {
            background: #218838;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(40, 167, 69, 0.3);
        }

        /* Congratulations Section */
        .congratulations-section {
            background: linear-gradient(135deg, #28a745 0%, #20c997 100%);
            color: white;
            padding: 40px 30px;
            text-align: center;
            display: none;
        }

        .congratulations-section.show {
            display: block;
        }

        .congratulations-text {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 20px;
        }

        .navigation-buttons {
            display: flex;
            gap: 15px;
            justify-content: center;
            margin-top: 20px;
        }

        .nav-btn {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            border: 2px solid rgba(255, 255, 255, 0.3);
            padding: 10px 20px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
        }

        .nav-btn:hover {
            background: rgba(255, 255, 255, 0.3);
            color: white;
            text-decoration: none;
        }

        /* Footer - Consistent with other customer pages */
        .footer-wrappper {
            background: #2c3e50;
        }
        .footer-area {
            padding: 60px 0;
        }
        .footer-logo img {
            max-height: 40px;
        }
        .footer-tittle h4 {
            color: white;
            font-size: 18px;
            margin-bottom: 20px;
        }
        .footer-pera p {
            color: #bdbdbd;
            line-height: 1.8;
        }
        .footer-social a {
            color: white;
            font-size: 18px;
            margin-right: 15px;
            transition: all 0.3s ease;
        }
        .footer-social a:hover {
            color: #667eea;
        }
        .footer-bottom-area {
            padding: 20px 0;
            border-top: 1px solid #4a5f7a;
        }
        .footer-copy-right p {
            color: #bdbdbd;
            margin: 0;
        }
    </style>
</head>

<body>
    <!-- Header -->
    <div class="combined-header">
        <div class="header-top">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-xl-2 col-lg-2 col-md-2">
                        <div class="logo">
                            <a href="home"><img src="assets/img/logo/logo.png" alt="" /></a>
                        </div>
                    </div>
                    <div class="col-xl-10 col-lg-10 col-md-10">
                        <div class="menu-wrapper d-flex align-items-center justify-content-end">
                            <div class="main-menu d-none d-lg-block">
                                <nav>
                                    <ul id="navigation">
                                        <li><a href="home">Home</a></li>
                                        <li><a href="courses">Courses</a></li>
                                        <li class="active"><a href="purchased-courses">Purchased courses</a></li>
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
    </div>

    <!-- Main Container -->
    <div class="main-container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-header">
                <h3 class="course-title">${course.title}</h3>
                <button class="close-btn" onclick="window.history.back()">&times;</button>
            </div>
            <ul class="quiz-list">
                <c:forEach var="quiz" items="${quizzes}" varStatus="status">
                    <li class="quiz-item" id="quiz-item-${quiz.quizId}">
                        <a href="#quiz-${quiz.quizId}" onclick="scrollToQuiz(${quiz.quizId})">
                            <div class="quiz-number">${status.index + 1}</div>
                            <div class="quiz-content">
                                <div class="quiz-title"><c:out value="${quiz.question}" escapeXml="false"/></div>
                                <div class="quiz-lesson">${quiz.lessonTitle}</div>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Quiz Header -->
            <div class="quiz-header">
                <h1 class="quiz-title">Course Quiz</h1>
                <p class="quiz-subtitle">${course.title} - ${totalCourseQuizzes} Questions</p>
            </div>

            <!-- Score Section (Hidden by default) -->
            <div class="score-section" id="scoreSection">
                <div class="score-info">
                    <div>
                        <div class="score-text">Your score: <span id="scoreText">0%</span></div>
                        <div class="passing-requirement">To pass this course you need at least 100%</div>
                    </div>
                    <div>
                        <button class="try-again-btn" onclick="tryAgain()">Try again</button>
                        <button class="close-btn" onclick="closeScoreSection()">&times;</button>
                    </div>
                </div>
            </div>

            <!-- Quiz Content -->
            <form id="quizForm" method="post" action="customer-list-quiz">
                <input type="hidden" name="courseId" value="${course.course_id}">
                <input type="hidden" name="lessonId" value="${lesson.lessonId}">
                
                <div class="quiz-content">
                    <c:forEach var="quiz" items="${quizzes}" varStatus="status">
                        <div class="question-container" id="quiz-${quiz.quizId}">
                            <div class="question-number">Question ${status.index + 1}: <c:out value="${quiz.question}" escapeXml="false"/></div>
                            <div class="options-container">
                                <c:set var="options" value="${fn:split(quiz.answerOptions, ';')}" />
                                <c:forEach var="option" items="${options}" varStatus="optionStatus">
                                    <c:set var="optionParts" value="${fn:split(option, '.')}" />
                                    <c:if test="${fn:length(optionParts) >= 2}">
                                        <c:set var="optionLetter" value="${optionParts[0]}" />
                                        <c:set var="optionText" value="${optionParts[1]}" />
                                        <div class="option-item">
                                            <input type="radio" 
                                                   id="option_${quiz.quizId}_${optionStatus.index}" 
                                                   name="answer_${quiz.quizId}" 
                                                   value="${optionLetter}"
                                                   <c:if test="${showResults && param['answer_' += quiz.quizId] == optionLetter}">checked</c:if>>
                                            <label class="option-label" for="option_${quiz.quizId}_${optionStatus.index}">
                                                <span>${optionLetter}. </span><span class="option-content"><c:out value="${optionText}"/></span>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            
                            <!-- Answer Feedback (Hidden by default) -->
                            <div class="answer-feedback" id="feedback_${quiz.quizId}">
                                <div class="correct-answer">Correct answer: <c:out value="${quiz.correctAnswer}" escapeXml="false"/></div>
                                <div class="explanation">Explanations: <c:out value="${quiz.explanation}" escapeXml="false"/></div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Submit Button -->
                <div class="submit-section">
                    <button type="submit" class="submit-btn" id="submitBtn">Submit All Answers</button>
                </div>
            </form>

            <!-- Congratulations Section (Hidden by default) -->
            <div class="congratulations-section" id="congratulationsSection">
                <div class="congratulations-text">Congratulations, you have passed the course!!</div>
                <div class="navigation-buttons">
                    <a href="home" class="nav-btn">Back to Home</a>
                    <a href="customer-course-detail?id=${course.course_id}" class="nav-btn">Back to Course</a>
                </div>
            </div>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <div class="footer-wrappper footer-bg">
            <div class="footer-area footer-padding">
                <div class="container">
                    <div class="row justify-content-between">
                        <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                            <div class="single-footer-caption mb-50">
                                <div class="single-footer-caption mb-30">
                                    <div class="footer-logo mb-25">
                                        <a href="index.jsp"><img src="assets/img/logo/logo2_footer.png" alt="" /></a>
                                    </div>
                                    <div class="footer-tittle">
                                        <div class="footer-pera">
                                            <p>Learn better every day.</p>
                                        </div>
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
                            <div class="col-xl-12">
                                <div class="footer-copy-right text-center">
                                    <p>
                                        Copyright &copy;
                                        <script>document.write(new Date().getFullYear());</script>
                                        All rights reserved
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>

    <!-- JavaScript -->
    <script src="assets/js/jquery-1.12.4.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="assets/js/main.js"></script>

    <script>
        // Xử lý HTML entities trong option content giống như VideoQuizServlet
        document.addEventListener('DOMContentLoaded', function() {
            const optionContents = document.querySelectorAll('.option-content');
            optionContents.forEach(function(element) {
                const text = element.textContent;
                // Unescape HTML entities
                const unescaped = text.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&amp;/g, '&');
                element.innerHTML = unescaped;
            });
        });

        // Hiển thị kết quả nếu có
        <c:if test="${showResults}">
            showResults();
        </c:if>

        function showResults() {
            const scoreSection = document.getElementById('scoreSection');
            const congratulationsSection = document.getElementById('congratulationsSection');
            const submitBtn = document.getElementById('submitBtn');
            
            // Hiển thị score section
            scoreSection.classList.add('show');
            
            // Hiển thị feedback cho từng câu hỏi
            <c:forEach var="quiz" items="${quizzes}">
                const feedback = document.getElementById('feedback_${quiz.quizId}');
                if (feedback) {
                    feedback.classList.add('show');
                }
            </c:forEach>
            
            // Cập nhật score text
            const scoreText = document.getElementById('scoreText');
            scoreText.textContent = '${score}%';
            
            // Ẩn submit button
            submitBtn.style.display = 'none';
            
            // Hiển thị congratulations nếu đạt 100%
            if (${score} >= 100) {
                congratulationsSection.classList.add('show');
            }
        }

        function tryAgain() {
            // Reload trang để làm lại quiz
            window.location.reload();
        }

        function closeScoreSection() {
            const scoreSection = document.getElementById('scoreSection');
            scoreSection.classList.remove('show');
        }

        // Function để scroll đến câu hỏi cụ thể
        function scrollToQuiz(quizId) {
            const quizElement = document.getElementById('quiz-' + quizId);
            if (quizElement) {
                quizElement.scrollIntoView({ behavior: 'smooth', block: 'start' });
                
                // Highlight câu hỏi trong sidebar
                document.querySelectorAll('.quiz-item').forEach(item => {
                    item.classList.remove('active');
                });
                document.getElementById('quiz-item-' + quizId).classList.add('active');
            }
        }

        // Highlight câu hỏi hiện tại khi scroll
        function highlightCurrentQuiz() {
            const questions = document.querySelectorAll('.question-container');
            const sidebarItems = document.querySelectorAll('.quiz-item');
            
            questions.forEach((question, index) => {
                const rect = question.getBoundingClientRect();
                if (rect.top <= 100 && rect.bottom >= 100) {
                    sidebarItems.forEach(item => item.classList.remove('active'));
                    if (sidebarItems[index]) {
                        sidebarItems[index].classList.add('active');
                    }
                }
            });
        }

        // Thêm scroll listener để highlight câu hỏi hiện tại
        document.addEventListener('scroll', highlightCurrentQuiz);

        // Disable form submission nếu đã submit
        <c:if test="${showResults}">
            document.getElementById('quizForm').addEventListener('submit', function(e) {
                e.preventDefault();
            });
        </c:if>
    </script>
</body>
</html>
