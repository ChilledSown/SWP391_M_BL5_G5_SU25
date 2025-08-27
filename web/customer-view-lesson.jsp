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
        <link rel="stylesheet" href="https://vjs.zencdn.net/8.10.0/video-js.css">
        <style>
            body { 
                font-family: "Helvetica Neue", Arial, sans-serif; 
                background: #f7f9fc;
                margin: 0;
                padding: 0;
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
            .lesson-layout {
                display: flex;
                height: calc(100vh - 80px);
                background: #f7f9fc;
            }

            /* Sidebar - Coursera Style */
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

            .lesson-list {
                flex: 1;
                overflow-y: auto;
                list-style: none;
                margin: 0;
                padding: 0;
            }

            .lesson-item {
                border-bottom: 1px solid #f0f2f5;
                transition: all 0.2s ease;
            }

            .lesson-item a {
                display: flex;
                align-items: center;
                padding: 16px 24px;
                color: #1f1f1f;
                text-decoration: none;
                transition: all 0.2s ease;
                position: relative;
            }

            .lesson-item:hover {
                background: #f8f9fa;
            }

            .lesson-item.active {
                background: #e8f4fd;
                border-left: 4px solid #0056b3;
            }

            .lesson-item.active a {
                color: #0056b3;
                font-weight: 600;
            }

            .lesson-number {
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

            .lesson-item.active .lesson-number {
                background: #0056b3;
                color: white;
            }

            .lesson-content {
                flex: 1;
            }

            .lesson-title {
                font-size: 14px;
                font-weight: 500;
                line-height: 1.4;
            }

            .quiz-item {
                background: #fff3cd;
            }

            .quiz-item .lesson-title {
                color: #856404;
                font-weight: 600;
            }

            .quiz-item .lesson-number {
                background: #ffc107;
                color: #856404;
            }

            /* Main Content */
            .main-content {
                flex: 1;
                display: flex;
                flex-direction: column;
                background: white;
            }

            .video-container {
                height: 700px;
                position: relative;
                background: #000;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .video-wrapper {
                width: 100%;
                height: 100%;
                position: relative;
            }

            .video-js {
                width: 100% !important;
                height: 100% !important;
            }

            .video-js .vjs-loading-spinner {
                display: block !important;
                z-index: 10 !important;
            }

            .video-js .vjs-big-play-button {
                z-index: 5 !important;
            }

            .video-js .vjs-control-bar {
                z-index: 5 !important;
            }

            /* Loading Animation */
            .loading-animation {
                display: flex;
                align-items: center;
                justify-content: center;
                flex-direction: column;
                color: white;
            }

            .loading-icon {
                width: 60px;
                height: 60px;
                border: 3px solid #6c757d;
                border-top: 3px solid #667eea;
                border-radius: 50%;
                animation: spin 1s linear infinite;
                margin-bottom: 20px;
            }

            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
            /* Video Quiz Styles */
            .video-quiz-overlay {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, 0.8);
                display: flex;
                align-items: center;
                justify-content: center;
                z-index: 1000;
            }

            .video-quiz-container {
                background: white;
                border-radius: 12px;
                max-width: 500px;
                width: 90%;
                max-height: 80%;
                overflow-y: auto;
            }

            .video-quiz-content {
                padding: 0;
            }

            .video-quiz-header {
                padding: 20px 20px 15px;
                border-bottom: 1px solid #eef2f7;
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .video-quiz-header h4 {
                margin: 0;
                color: #2c3e50;
                font-weight: 600;
            }

            .video-quiz-body {
                padding: 20px;
            }

            .video-quiz-body p {
                font-size: 16px;
                color: #2c3e50;
                margin-bottom: 20px;
                line-height: 1.5;
            }

            .video-quiz-actions {
                margin-top: 20px;
                text-align: right;
            }

            .quiz-option {
                display: block;
                width: 100%;
                padding: 12px 15px;
                margin-bottom: 10px;
                border: 2px solid #eef2f7;
                border-radius: 8px;
                background: white;
                cursor: pointer;
                transition: all 0.2s ease;
                text-align: left;
            }

            .quiz-option:hover {
                border-color: #3445d4;
                background: #f8f9ff;
            }

            .quiz-option.selected {
                border-color: #3445d4;
                background: #eef2ff;
            }

            .quiz-option input[type="radio"] {
                margin-right: 10px;
            }

            .quiz-result {
                padding: 15px;
                border-radius: 8px;
                margin-top: 15px;
            }

            .quiz-result.correct {
                background: #d4edda;
                border: 1px solid #c3e6cb;
                color: #155724;
            }

            .quiz-result.incorrect {
                background: #f8d7da;
                border: 1px solid #f5c6cb;
                color: #721c24;
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

            /* Responsive */
            @media (max-width: 768px) {
                .lesson-layout {
                    flex-direction: column;
                    height: auto;
                }
                
                .sidebar {
                    width: 100%;
                    height: auto;
                    max-height: 300px;
                }
                
                .main-content {
                    height: 400px;
                }
            }
        </style>
    </head>
    <body>
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

        <main class="lesson-layout">
            <!-- Sidebar -->
            <div class="sidebar">
                <div class="sidebar-header">
                    <h3 class="course-title">${course.title}</h3>
                    <button class="close-btn" onclick="window.history.back()">&times;</button>
                </div>
                <ul class="lesson-list">
                    <c:forEach var="lessonItem" items="${lessons}" varStatus="status">
                        <li class="lesson-item ${lessonItem.lessonId == activeLesson.lessonId ? 'active' : ''}">
                            <a href="customer-view-lesson?courseId=${courseId}&lessonId=${lessonItem.lessonId}">
                                <div class="lesson-number">${status.index + 1}</div>
                                <div class="lesson-content">
                                    <div class="lesson-title">${lessonItem.title}</div>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                    <li class="lesson-item quiz-item">
                        <a href="customer-quiz-preparation?courseId=${courseId}&lessonId=${activeLesson.lessonId}">
                            <div class="lesson-number">Q</div>
                            <div class="lesson-content">
                                <div class="lesson-title">Practice Quiz</div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Main Content -->
            <div class="main-content">
                <div class="video-container">
                    <div class="video-wrapper">
                        <c:choose>
                            <c:when test="${activeLesson != null && activeLesson.videoUrl != null && activeLesson.videoUrl ne '' && !(fn:contains(activeLesson.videoUrl,'youtube.com') or fn:contains(activeLesson.videoUrl,'youtu.be'))}">
                                <c:choose>
                                    <c:when test="${fn:startsWith(activeLesson.videoUrl,'http')}">
                                        <c:set var="resolvedVideoUrl" value="${activeLesson.videoUrl}" />
                                    </c:when>
                                    <c:when test="${fn:startsWith(activeLesson.videoUrl,'/')}">
                                        <c:url value="${activeLesson.videoUrl}" var="resolvedVideoUrl" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:url value="/${activeLesson.videoUrl}" var="resolvedVideoUrl" />
                                    </c:otherwise>
                                </c:choose>
                                <video id="lessonVideo" class="video-js vjs-default-skin" controls preload="auto" src="${resolvedVideoUrl}" playsinline>
                                    Your browser does not support HTML5 video.
                                </video>
                            </c:when>
                            <c:otherwise>
                                <div class="loading-animation">
                                    <div class="loading-icon"></div>
                                    <div>Loading video content...</div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <!-- Video Quiz Overlay -->
                        <div id="videoQuizOverlay" class="video-quiz-overlay" style="display: none;">
                            <div class="video-quiz-container">
                                <div class="video-quiz-content">
                                    <div class="video-quiz-header">
                                        <h4>Quick Quiz</h4>
                                        <button type="button" class="btn-close" onclick="closeVideoQuiz()"></button>
                                    </div>
                                    <div class="video-quiz-body">
                                        <p id="quizQuestion"></p>
                                        <div id="quizOptions"></div>
                                        <div class="video-quiz-actions">
                                            <button type="button" class="btn btn-primary" onclick="submitQuizAnswer()">Submit Answer</button>
                                        </div>
                                    </div>
                                </div>
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
        <script src="https://vjs.zencdn.net/8.10.0/video.min.js"></script>
        
        <script>
            // Video.js in-video quiz: load quizzes, watch time, pause to ask, resume after
            let videoQuizzes = [];
            let currentQuiz = null;
            let selectedAnswer = null;
            let videoElement = null;
            let quizCheckInterval = null;
            document.addEventListener('DOMContentLoaded', function() {
                const lessonId = '${activeLesson.lessonId}';
                if (lessonId) {
                    loadVideoQuizzes(lessonId);
                    setupVideoTracking();
                }
            });

            // Load quizzes from backend (plain text format)
            function loadVideoQuizzes(lessonId) {
                fetch('video-quiz?action=get-quizzes&lessonId=' + lessonId)
                    .then(response => response.text())
                    .then(data => {
                        videoQuizzes = [];
                        const lines = data.split('\n');
                        lines.forEach(raw => {
                            const line = raw.trim();
                            if (line.startsWith('QUIZ|')) {
                                const parts = line.split('|');
                                if (parts.length >= 7) {
                                    videoQuizzes.push({
                                        videoQuizId: parseInt(parts[1]),
                                        lessonId: parseInt(parts[2]),
                                        timestamp: parseInt(parts[3], 10),
                                        question: parts[4],
                                        answerOptions: parts[5],
                                        correctAnswer: parts[6],
                                        explanation: parts[7] || '',
                                        isActive: true,
                                        shown: false
                                    });
                                }
                            }
                        });
                    })
                    .catch(error => {
                        console.error('Error loading video quizzes:', error);
                    });
            }
            
            // Initialize Video.js player and time tracking
            function setupVideoTracking() {
                const el = document.getElementById('lessonVideo');
                if (!el) return;
                // Ensure any existing Video.js instance is disposed to prevent nested players
                try {
                    const existing = videojs.getPlayer('lessonVideo');
                    if (existing && !existing.isDisposed()) {
                        existing.dispose();
                    }
                } catch (_) {}

                try {
                    videoElement = videojs('lessonVideo', {
                        controls: true,
                        preload: 'auto',
                        fluid: false,
                        responsive: true
                    });

                    // Ensure the source is applied explicitly in case Video.js missed initial src
                    try {
                        if (videoElement && typeof videoElement.src === 'function') {
                            videoElement.src({ src: '${resolvedVideoUrl}', type: 'video/mp4' });
                        }
                    } catch (_) {}
                    
                    videoElement.ready(function() {
                        this.on('timeupdate', function() {
                            checkForQuizzes();
                            updateTimeDisplay();
                            updateProgress();
                        });
                        this.on('loadstart', function() {
                        });
                        this.on('loadeddata', function() {
                            console.log('Video data loaded');
                            updateTimeDisplay();
                        });
                    });
                    
                    if (quizCheckInterval) { clearInterval(quizCheckInterval); quizCheckInterval = null; }
                    quizCheckInterval = setInterval(checkForQuizzes, 1000);
                } catch (e) {
                    videoElement = el;
                    el.addEventListener('timeupdate', function() {
                        checkForQuizzes();
                        updateTimeDisplay();
                        updateProgress();
                    });
                    if (quizCheckInterval) { clearInterval(quizCheckInterval); quizCheckInterval = null; }
                    quizCheckInterval = setInterval(checkForQuizzes, 1000);
                }
            }

            // Update current time and duration text
            function updateTimeDisplay() {
                if (videoElement) {
                    const current = Math.floor(videoElement.currentTime());
                    const total = Math.floor(videoElement.duration());
                    document.getElementById('currentTime').textContent = formatTime(current);
                    document.getElementById('totalTime').textContent = formatTime(total);
                }
            }

            // Update simple progress bar width
            function updateProgress() {
                if (videoElement) {
                    const progress = (videoElement.currentTime() / videoElement.duration()) * 100;
                    document.getElementById('progressFill').style.width = progress + '%';
                }
            }

            // Convert seconds to mm:ss
            function formatTime(seconds) {
                const mins = Math.floor(seconds / 60);
                const secs = seconds % 60;
                return `${mins}:${secs.toString().padStart(2, '0')}`;
            }
            
            // Check if it's time to show any quiz
            function checkForQuizzes() {
                if (videoQuizzes.length === 0) return;
                
                let currentTime = 0;
                if (videoElement) {
                    try {
                        if (typeof videoElement.currentTime === 'function') {
                            currentTime = Math.floor(videoElement.currentTime());
                        } else if (typeof videoElement.currentTime === 'number') {
                            currentTime = Math.floor(videoElement.currentTime);
                        }
                    } catch (_) {}
                } else {
                    currentTime = Math.floor(Date.now() / 1000) % 3600;
                }
                
                const nextQuiz = videoQuizzes.find(q => q.isActive && !q.shown);
                const quizToShow = videoQuizzes.find(q => q.isActive && !q.shown && currentTime >= q.timestamp);
                if (quizToShow) {
                    showVideoQuiz(quizToShow);
                    quizToShow.shown = true;
                }
            }

            // Render and show quiz overlay, pause video
            function showVideoQuiz(quiz) {
                currentQuiz = quiz;
                selectedAnswer = null;
                
                document.getElementById('quizQuestion').textContent = quiz.question;
                let options = [];
                if (quiz.answerOptions.includes('|')) {
                    options = quiz.answerOptions.split('|');
                } else {
                    options = quiz.answerOptions.split(';');
                }
                var container = document.getElementById('quizOptions');
                container.innerHTML = '';
                options.map(function(option) { return option.trim(); })
                       .filter(function(option) { return option.length > 0; })
                       .forEach(function(option, index) {
                           var optionId = 'option_' + index;
                           var label = document.createElement('label');
                           label.className = 'quiz-option';
                           label.setAttribute('for', optionId);
                           
                           var input = document.createElement('input');
                           input.type = 'radio';
                           input.name = 'quizAnswer';
                           input.id = optionId;
                           input.value = option; 
                           input.addEventListener('change', function() { selectOption(optionId, option); });
                           
                           var textNode = document.createElement('span');
                           textNode.textContent = option; 
                           
                           label.appendChild(input);
                           label.appendChild(textNode);
                           container.appendChild(label);
                       });
                
                document.getElementById('videoQuizOverlay').style.display = 'flex';
                
                try {
                    if (videoElement && typeof videoElement.pause === 'function') {
                        videoElement.pause();
                    } else if (videoElement && videoElement.pause) {
                        videoElement.pause();
                    }
                } catch (_) {}
            }
            
            // Mark user's selected option
            function selectOption(optionId, val) {
                document.querySelectorAll('.quiz-option').forEach(opt => opt.classList.remove('selected'));
                const input = document.getElementById(optionId);
                if (input) input.checked = true;
                const label = document.querySelector('label[for="' + optionId + '"]');
                if (label) label.classList.add('selected');
                selectedAnswer = val;
            }

            // Submit user's answer to backend and show feedback
            function submitQuizAnswer() {
                if (!selectedAnswer || !currentQuiz) {
                    alert('Please select an answer');
                    return;
                }
                
                fetch('video-quiz?action=submit-answer', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: 'quizId=' + currentQuiz.videoQuizId + '&selectedAnswer=' + encodeURIComponent(selectedAnswer)
                })
                .then(r => r.text())
                .then(text => {
                    if (text.startsWith('RESULT|')) {
                        const p = text.split('|');
                        const result = { isCorrect: p[1] === 'CORRECT', correctAnswer: p[2] || '', explanation: p[3] || '' };
                        showQuizResult(result);
                    } else {
                        alert('Submit failed');
                    }
                })
                .catch(() => alert('Submit failed'));
            }
            
            // Show result (correct/incorrect) and explanation, change button to Continue
            function showQuizResult(result) {
                var html = '<div class="quiz-result ' + (result.isCorrect ? 'correct' : 'incorrect') + '">' +
                           '<strong>' + (result.isCorrect ? 'Correct!' : 'Incorrect!') + '</strong>' +
                           '<p class="mb-1">Correct answer: ' + result.correctAnswer + '</p>';
                if (result.explanation) {
                    html += '<p class="mb-0">' + result.explanation + '</p>';
                }
                html += '</div>';
                document.getElementById('quizOptions').innerHTML = html;
                var btn = document.querySelector('.video-quiz-actions button');
                btn.textContent = 'Continue';
                btn.onclick = closeVideoQuiz;
                btn.className = 'btn btn-success';
            }
            
            // Hide overlay and resume video playback
            function closeVideoQuiz() {
                document.getElementById('videoQuizOverlay').style.display = 'none';
                currentQuiz = null;
                selectedAnswer = null;
                try {
                    if (videoElement && typeof videoElement.play === 'function') {
                        videoElement.play();
                    } else if (videoElement && videoElement.play) {
                        videoElement.play();
                    }
                } catch (_) {}
            }
            
            // Cleanup on page unload: clear timers and dispose Video.js
            window.addEventListener('beforeunload', function() {
                if (quizCheckInterval) clearInterval(quizCheckInterval);
                try {
                    const p = videojs.getPlayer('lessonVideo');
                    if (p && !p.isDisposed()) p.dispose();
                } catch (_) {}
            });
        </script>
    </body>
</html>

