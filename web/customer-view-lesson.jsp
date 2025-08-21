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
            body { font-family: "Helvetica Neue", Arial, sans-serif; }
            .lesson-layout { padding: 60px 0; background: #f7f9fc; }
            
            /* Coursera-style sidebar */
            .sidebar { 
                background: #ffffff; 
                border-radius: 8px; 
                box-shadow: 0 2px 8px rgba(0,0,0,0.1); 
                border: 1px solid #e1e5e9;
            }
            .sidebar .sidebar-title { 
                padding: 20px 24px; 
                font-weight: 600; 
                color: #1f1f1f; 
                border-bottom: 1px solid #e1e5e9; 
                font-size: 18px;
                background: #f8f9fa;
                border-radius: 8px 8px 0 0;
            }
            
            /* Coursera-style lesson links */
            .lesson-link { 
                display: flex; 
                align-items: center;
                padding: 16px 24px; 
                color: #1f1f1f; 
                text-decoration: none; 
                border-bottom: 1px solid #f0f2f5; 
                transition: all 0.2s ease;
                font-size: 14px;
                line-height: 1.4;
            }
            .lesson-link:hover { 
                background: #f8f9fa; 
                color: #0056b3;
                text-decoration: none;
            }
            .lesson-link.active { 
                background: #e8f4fd; 
                color: #0056b3; 
                font-weight: 600;
                border-left: 4px solid #0056b3;
                padding-left: 20px;
            }
            
            /* Lesson number styling */
            .lesson-number {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                width: 24px;
                height: 24px;
                background: #e1e5e9;
                border-radius: 50%;
                font-size: 12px;
                font-weight: 600;
                color: #6a6f73;
                margin-right: 12px;
                flex-shrink: 0;
            }
            
            .lesson-link.active .lesson-number {
                background: #0056b3;
                color: white;
            }
            
            /* Coursera-style quiz box */
            .quiz-box { 
                padding: 20px 24px; 
                background: #f8f9fa; 
                border-top: 1px solid #e1e5e9;
                border-radius: 0 0 8px 8px;
            }
            
            .quiz-box .btn {
                border-radius: 6px;
                font-weight: 600;
                font-size: 14px;
                padding: 12px 20px;
                border: none;
                transition: all 0.2s ease;
            }
            
            .quiz-box .btn-primary {
                background: #0056b3;
                color: white;
            }
            
            .quiz-box .btn-primary:hover {
                background: #004085;
                transform: translateY(-1px);
                box-shadow: 0 4px 12px rgba(0, 86, 179, 0.3);
            }
            
            .quiz-box .btn-secondary {
                background: #6c757d;
                color: white;
            }
            
            .quiz-box .quiz-info {
                margin-top: 12px;
                font-size: 13px;
                color: #6a6f73;
                text-align: center;
            }
            
            /* Main content styling */
            .player-card { 
                background: #ffffff; 
                border-radius: 8px; 
                box-shadow: 0 2px 8px rgba(0,0,0,0.1); 
                padding: 20px; 
                border: 1px solid #e1e5e9;
            }
            
            .video-wrapper { 
                position: relative; 
                background: #000; 
                border-radius: 8px; 
                overflow: hidden; 
                height: 480px; 
            }
            
            /* Ensure video fills the wrapper while keeping aspect ratio */
            .video-js, #lessonVideo { width: 100% !important; height: 100% !important; }
            .video-js .vjs-tech, #lessonVideo { object-fit: contain; background-color: #000; }
            
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
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
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
            
        </style>
    </head>
    <body>
        <!-- Combined Header Start (from purchased-courses) -->
        <style>
            .combined-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); position: relative; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
            .combined-header::before { content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: rgba(255,255,255,0.1); backdrop-filter: blur(10px); }
            .header-top { padding: 20px 0; border-bottom: 1px solid rgba(255,255,255,0.2); position: relative; z-index: 2; }
            .page-header-content { padding: 40px 0; position: relative; z-index: 2; text-align: center; color: white; }
            #navigation { display: flex; align-items: center; justify-content: center; gap: 0; margin: 0; padding: 0; list-style: none; }
            #navigation li { margin: 0; padding: 0; display: flex; align-items: center; }
            #navigation li a { color: white !important; font-weight: 500; font-size: 16px; text-decoration: none; padding: 12px 20px; border-radius: 8px; transition: all 0.3s ease; display: block; position: relative; margin: 0 5px; }
            #navigation li a::after { content: ''; position: absolute; bottom: 0; left: 50%; width: 0; height: 2px; background: white; transition: all 0.3s ease; transform: translateX(-50%); }
            #navigation li a:not(.btn):hover { color: white !important; background: transparent !important; transform: none !important; box-shadow: none !important; backdrop-filter: none !important; }
            #navigation li a:not(.btn):hover::after { width: 0 !important; }
            .logo img { max-height: 40px; }
        </style>
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
                                    <span class="lesson-number">${s.index + 1}</span>
                                    ${lesson.title}
                                </a>
                            </c:forEach>
                            <div class="quiz-box">
                                <c:choose>
                                    <c:when test="${not empty quizzes}">
                                        <a class="btn w-100 btn-primary" href="quizDetail?lessonId=${activeLesson.lessonId}&quizId=${quizzes[0].quizId}">Practice Quiz</a>
                                        <div class="quiz-info text-muted mt-2" style="font-size: 13px">${fn:length(quizzes)} questions available</div>
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
                                        <video id="lessonVideo" class="video-js vjs-default-skin" width="100%" height="480" controls data-setup='{}'>
                                            <source src="${resolvedVideoUrl}" type="video/mp4">
                                            Your browser does not support HTML5 video.
                                        </video>
                                    </c:when>
                                    <c:otherwise>
                                        <div style="color:#fff;display:flex;align-items:center;justify-content:center;height:360px;">Chỉ hỗ trợ phát video nội bộ (local). Vui lòng cập nhật URL video không phải YouTube.</div>
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
            // Video Quiz Variables
            let videoQuizzes = [];
            let currentQuiz = null;
            let selectedAnswer = null;
            let videoElement = null; // HTML5 video element or video.js player instance
            let quizCheckInterval = null;
            
            // Initialize video quiz functionality
            document.addEventListener('DOMContentLoaded', function() {
                const lessonId = '${activeLesson.lessonId}';
                if (lessonId) {
                    loadVideoQuizzes(lessonId);
                    setupVideoTracking();
                }
            });
            
            // Load video quizzes for the current lesson (parse QUIZ| lines)
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
                        console.log('Loaded video quizzes:', videoQuizzes);
                    })
                    .catch(error => {
                        console.error('Error loading video quizzes:', error);
                    });
            }
            
            // Setup video tracking for quiz timing
            function setupVideoTracking() {
                const el = document.getElementById('lessonVideo');
                if (!el) return;
                
                if (el.tagName === 'VIDEO') {
                    // Initialize video.js for HTML5 video
                    try {
                        videoElement = videojs('lessonVideo');
                        if (videoElement && typeof videoElement.ready === 'function') {
                            videoElement.ready(function() {
                                console.log('Quiz Timing: video.js ready');
                                this.on('timeupdate', checkForQuizzes);
                                this.on('loadedmetadata', function() {
                                    console.log('Quiz Timing: metadata loaded, duration =', this.duration());
                                });
                            });
                        } else {
                            videoElement.on('timeupdate', checkForQuizzes);
                        }
                        // Fallback timer in case timeupdate fires sparsely
                        quizCheckInterval = setInterval(checkForQuizzes, 1000);
                    } catch (e) {
                        // Fallback: native timeupdate
                        videoElement = el;
                        el.addEventListener('timeupdate', checkForQuizzes);
                        quizCheckInterval = setInterval(checkForQuizzes, 1000);
                    }
                } else if (el.tagName === 'IFRAME') {
                    // YouTube: we cannot read time reliably; use a simple 1s timer demo
                    quizCheckInterval = setInterval(checkForQuizzes, 1000);
                }
            }
            
            // Check if any quiz should be shown at current time
            function checkForQuizzes() {
                if (videoQuizzes.length === 0) return;
                
                let currentTime = 0;
                if (videoElement) {
                    // video.js player has currentTime(); native video has currentTime property
                    try {
                        if (typeof videoElement.currentTime === 'function') {
                            currentTime = Math.floor(videoElement.currentTime());
                        } else if (typeof videoElement.currentTime === 'number') {
                            currentTime = Math.floor(videoElement.currentTime);
                        }
                    } catch (_) {}
                } else {
                    // Fallback demo for iframe
                    currentTime = Math.floor(Date.now() / 1000) % 3600;
                }
                
                // Debug log near next timestamp
                const nextQuiz = videoQuizzes.find(q => q.isActive && !q.shown);
                if (nextQuiz && Math.abs(nextQuiz.timestamp - currentTime) <= 2) {
                    console.log('Quiz Timing: t=', currentTime, 'next=', nextQuiz.timestamp);
                }
                const quizToShow = videoQuizzes.find(q => q.isActive && !q.shown && currentTime >= q.timestamp);
                if (quizToShow) {
                    console.log('Quiz Timing: show quiz id=', quizToShow.videoQuizId, 'at t=', currentTime);
                    showVideoQuiz(quizToShow);
                    quizToShow.shown = true;
                }
            }
            
            // Show video quiz overlay
            function showVideoQuiz(quiz) {
                currentQuiz = quiz;
                selectedAnswer = null;
                
                document.getElementById('quizQuestion').textContent = quiz.question;
                
                // Parse answerOptions (supports both "|" and ";" formats)
                let options = [];
                if (quiz.answerOptions.includes('|')) {
                    options = quiz.answerOptions.split('|');
                } else {
                    options = quiz.answerOptions.split(';');
                }
                
                // Normalize and render safely using DOM API (avoid HTML interpretation of tags like <a>, <form>)
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
                           input.value = option; // keep raw text for server comparison
                           input.addEventListener('change', function() { selectOption(optionId, option); });
                           
                           var textNode = document.createElement('span');
                           textNode.textContent = option; // safe display
                           
                           label.appendChild(input);
                           label.appendChild(textNode);
                           container.appendChild(label);
                       });
                
                document.getElementById('videoQuizOverlay').style.display = 'flex';
                
                // Pause if possible
                try {
                    if (videoElement && typeof videoElement.pause === 'function') {
                        videoElement.pause();
                    } else if (videoElement && videoElement.pause) {
                        videoElement.pause();
                    }
                } catch (_) {}
            }
            
            function selectOption(optionId, val) {
                document.querySelectorAll('.quiz-option').forEach(opt => opt.classList.remove('selected'));
                const input = document.getElementById(optionId);
                if (input) input.checked = true;
                const label = document.querySelector('label[for="' + optionId + '"]');
                if (label) label.classList.add('selected');
                selectedAnswer = val;
            }
            
            // Submit quiz answer
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
                    // Expect format: RESULT|CORRECT|<correct>|<explanation>
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
            
            window.addEventListener('beforeunload', function() {
                if (quizCheckInterval) clearInterval(quizCheckInterval);
            });
        </script>
    </body>
    </html>


