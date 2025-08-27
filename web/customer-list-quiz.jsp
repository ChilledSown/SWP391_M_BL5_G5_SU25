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
            backdrop-filter: none !important;
        }
        #navigation li a:not(.btn):hover::after {
            width: 0 !important;
        }
        .logo img {
            max-height: 40px;
        }

        .main-container {
            width: 100%;
            max-width: none;
            margin: 0;
            padding: 20px;
            background: white;
            min-height: calc(100vh - 80px);
        }

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
        .score-section {
            background: #f8f9fa;
            border: 1px solid #e9ecef;
            border-radius: 8px;
            padding: 24px;
            margin-bottom: 30px;
            display: none;
        }

        .score-section.show {
            display: block;
            animation: fadeIn 0.5s ease-in;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .score-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 16px;
        }

        .score-title {
            font-size: 20px;
            font-weight: 600;
            color: #2c3e50;
            margin: 0;
        }

        .score-percentage {
            font-size: 24px;
            font-weight: 700;
            color: #28a745;
        }

        .score-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 16px;
            margin-bottom: 16px;
        }

        .score-item {
            text-align: center;
            padding: 12px;
            background: white;
            border-radius: 6px;
            border: 1px solid #e9ecef;
        }

        .score-label {
            font-size: 12px;
            color: #6c757d;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-bottom: 4px;
        }

        .score-value {
            font-size: 18px;
            font-weight: 600;
            color: #2c3e50;
        }

        .score-status {
            text-align: center;
            padding: 12px;
            border-radius: 6px;
            font-weight: 600;
        }

        .score-status.passed {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .score-status.failed {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .score-actions {
            display: flex;
            justify-content: center;
            gap: 12px;
            margin-top: 16px;
        }

        .btn {
            padding: 8px 16px;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.2s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-primary {
            background: #007bff;
            color: white;
            border: 1px solid #007bff;
        }

        .btn-primary:hover {
            background: #0056b3;
            border-color: #0056b3;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
            border: 1px solid #6c757d;
        }

        .btn-secondary:hover {
            background: #545b62;
            border-color: #545b62;
        }

        /* Quiz Content - Full width */
        .quiz-content {
            margin-bottom: 30px;
            width: 100%;
        }

        .question-container {
            background: white;
            border: 1px solid #e9ecef;
            border-radius: 12px;
            padding: 25px;
            margin-bottom: 25px;
            width: 100%;
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
            width: 100%;
        }

        .options-container {
            display: flex;
            flex-direction: column;
            gap: 12px;
            width: 100%;
        }

        .option-item {
            display: flex;
            align-items: center;
            padding: 12px 16px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
            width: 100%;
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
            width: 100%;
        }

        .answer-feedback {
            margin-top: 20px;
            display: none;
            width: 100%;
        }

        .answer-feedback.show {
            display: block;
            animation: slideInUp 0.4s ease-out;
        }

        @keyframes slideInUp {
            from {
                opacity: 0;
                transform: translateY(10px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .feedback-correct {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 8px;
            padding: 16px;
            width: 100%;
        }

        .feedback-incorrect {
            background: #f8d7da;
            border: 1px solid #f5c6cb;
            border-radius: 8px;
            padding: 16px;
            width: 100%;
        }

        .feedback-unanswered {
            background: #fff3cd;
            border: 1px solid #ffeaa7;
            border-radius: 8px;
            padding: 16px;
            width: 100%;
        }

        .correct-answer {
            font-weight: 600;
            color: #155724;
            margin-bottom: 8px;
        }

        .incorrect-answer {
            font-weight: 600;
            color: #721c24;
            margin-bottom: 8px;
        }

        .unanswered-text {
            font-weight: 600;
            color: #856404;
            margin-bottom: 8px;
        }

        .explanation {
            color: #495057;
            line-height: 1.6;
            font-size: 14px;
            width: 100%;
        }

        .submit-section {
            padding: 20px 0;
            text-align: center;
            border-top: 1px solid #e9ecef;
            width: 100%;
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
        }

        .submit-btn:disabled {
            background: #6c757d;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }

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
            animation: fadeIn 0.6s ease-in;
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
            <!-- Quiz Header -->
            <div class="quiz-header">
                <h1 class="quiz-title">Course Quiz</h1>
                <p class="quiz-subtitle">${course.title} - ${totalCourseQuizzes} Questions</p>
            </div>

            <!-- Score Section (Hidden by default) -->
            <div class="score-section" id="scoreSection">
            <div class="score-header">
                <h2 class="score-title">Quiz Results</h2>
                <div class="score-percentage" id="scorePercentage">0%</div>
                    </div>
            
            <div class="score-details">
                <div class="score-item">
                    <div class="score-label">Total Questions</div>
                    <div class="score-value" id="totalQuestions">0</div>
                    </div>
                <div class="score-item">
                    <div class="score-label">Correct Answers</div>
                    <div class="score-value" id="correctAnswers">0</div>
                </div>
                <div class="score-item">
                    <div class="score-label">Answered</div>
                    <div class="score-value" id="answeredQuestions">0</div>
                </div>
                
            </div>
            
            <div class="score-status" id="scoreStatus">
                <span id="statusText">You need to answer all questions correctly to pass</span>
            </div>
            
            <div class="score-actions">
                <a href="customer-course-detail?id=${course.course_id}" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back to Course
                </a>
                </div>
            </div>

            <!-- Quiz Content -->
            <form id="quizForm" method="post" action="customer-list-quiz">
                <input type="hidden" name="courseId" value="${course.course_id}">
                <input type="hidden" name="lessonId" value="${lesson.lessonId}">
                
                <div class="quiz-content">
                    <c:forEach var="quiz" items="${quizzes}" varStatus="status">
                        <div class="question-container" id="quiz-${quiz.quizId}">
                        <div class="question-number">Question ${status.index + 1}</div>
                        <div class="question-text" id="question-text-${quiz.quizId}">
                            <c:out value="${quiz.question}" escapeXml="true"/>
                        </div>
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
                                            <span>${optionLetter}. </span><span class="option-content" id="option-content-${quiz.quizId}_${optionStatus.index}"><c:out value="${optionText}" escapeXml="true"/></span>
                                            </label>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                            
                            <!-- Answer Feedback (Hidden by default) -->
                            <div class="answer-feedback" id="feedback_${quiz.quizId}">
                            <c:if test="${showResults}">
                                <c:set var="quizResult" value="${quizResults[quiz.quizId]}" />
                                <c:choose>
                                    <c:when test="${quizResult.correct}">
                                        <div class="feedback-correct">
                                            <div class="correct-answer">Correct! Your answer: <c:out value="${quizResult.userAnswer}" escapeXml="true"/></div>
                                            <div class="explanation" id="explanation-${quiz.quizId}"><c:out value="${quiz.explanation}" escapeXml="true"/></div>
                                        </div>
                                    </c:when>
                                    <c:when test="${quizResult.answered}">
                                        <div class="feedback-incorrect">
                                            <div class="incorrect-answer">Incorrect. Your answer: <c:out value="${quizResult.userAnswer}" escapeXml="true"/></div>
                                            <div class="correct-answer">Correct answer: <c:out value="${quiz.correctAnswer}" escapeXml="true"/></div>
                                            <div class="explanation" id="explanation-${quiz.quizId}"><c:out value="${quiz.explanation}" escapeXml="true"/></div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="feedback-unanswered">
                                            <div class="unanswered-text">You didn't answer this question</div>
                                            <div class="correct-answer">Correct answer: <c:out value="${quiz.correctAnswer}" escapeXml="true"/></div>
                                            <div class="explanation" id="explanation-${quiz.quizId}"><c:out value="${quiz.explanation}" escapeXml="true"/></div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
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
        // Show results if available
        var showResultsFlag = ${showResults};
        if (showResultsFlag) {
            showResults();
        }

        function showResults() {
            const scoreSection = document.getElementById('scoreSection');
            const congratulationsSection = document.getElementById('congratulationsSection');
            const submitBtn = document.getElementById('submitBtn');
            
            // Update score information
            document.getElementById('scorePercentage').textContent = '${score}%';
            document.getElementById('totalQuestions').textContent = '${totalQuestions}';
            document.getElementById('correctAnswers').textContent = '${correctAnswers}';
            document.getElementById('answeredQuestions').textContent = '${answeredQuestions}';
            
            
            // Update status
            const statusElement = document.getElementById('scoreStatus');
            const statusText = document.getElementById('statusText');
            
            var isPassedFlag = ${isPassed};
            if (isPassedFlag) {
                statusElement.className = 'score-status passed';
                statusText.textContent = 'Congratulations! You have passed the course!';
            } else {
                statusElement.className = 'score-status failed';
                statusText.textContent = 'You need to answer all questions correctly to pass';
            }
            
            // Show score section with animation
            scoreSection.classList.add('show');
            
            // Show feedback for each question with delay
            const feedbackElements = document.querySelectorAll('[id^="feedback_"]');
            feedbackElements.forEach(function(element, index) {
                setTimeout(() => {
                    element.classList.add('show');
                }, index * 200);
            });
            
            // Hide submit button
            submitBtn.style.display = 'none';
            
            // Show congratulations if passed
            if (isPassedFlag) {
                setTimeout(() => {
                congratulationsSection.classList.add('show');
                }, 1000);
            }
            
            // Scroll to top to see results
            window.scrollTo({ top: 0, behavior: 'smooth' });
        }

        function tryAgain() {
            // Reload page to retake quiz
            window.location.reload();
        }

        // Disable form submission if already submitted
        if (showResultsFlag) {
            document.getElementById('quizForm').addEventListener('submit', function(e) {
                e.preventDefault();
            });
        }
    </script>
</body>
</html>
