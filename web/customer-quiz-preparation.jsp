<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
  <head>
    <meta charset="utf-8" />
    <meta http-equiv="x-ua-compatible" content="ie=edge" />
    <title>Quiz Preparation | Education</title>
    <meta name="description" content="" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link
      rel="shortcut icon"
      type="image/x-icon"
      href="assets/img/favicon.ico"
    />

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

      /* Main Layout */
      .main-container {
        display: flex;
        min-height: calc(100vh - 200px);
        padding: 40px 0;
      }

      /* Sidebar */
      .sidebar {
        width: 300px;
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        margin-right: 30px;
        overflow: hidden;
      }

      .sidebar-header {
        padding: 20px;
        background: #f8f9fa;
        border-bottom: 1px solid #e9ecef;
        font-weight: 600;
        color: #495057;
      }

      .lesson-list {
        list-style: none;
        margin: 0;
        padding: 0;
      }

      .lesson-item {
        padding: 16px 20px;
        border-bottom: 1px solid #f1f3f4;
        cursor: pointer;
        transition: all 0.3s ease;
        display: flex;
        align-items: center;
      }

      .lesson-item:hover {
        background: #f8f9fa;
      }

      .lesson-item.active {
        background: #e3f2fd;
        color: #1976d2;
        font-weight: 600;
        border-left: 4px solid #1976d2;
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
        background: #1976d2;
        color: white;
      }

      .quiz-item {
        background: #fff3cd;
        color: #856404;
        font-weight: 600;
      }

      /* Main Content */
      .main-content {
        flex: 1;
        display: flex;
        flex-direction: column;
        gap: 20px;
      }

      /* AI Coaching Section */
      .ai-coaching-section {
        background: white;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      }

      .section-title {
        font-size: 24px;
        font-weight: 700;
        color: #2c3e50;
        margin-bottom: 20px;
      }

      .ai-prompt {
        color: #6c757d;
        font-size: 16px;
        line-height: 1.6;
        margin-bottom: 20px;
      }

      .help-btn {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
        border: none;
        padding: 12px 24px;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
      }

      .help-btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(102, 126, 234, 0.4);
      }

      /* Coach Response Section */
      .coach-response-section {
        background: white;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        display: none;
      }

      .coach-header {
        display: flex;
        align-items: center;
        margin-bottom: 20px;
      }

      .coach-avatar {
        width: 50px;
        height: 50px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-right: 15px;
        color: white;
        font-size: 20px;
      }

      .coach-info h4 {
        margin: 0;
        color: #2c3e50;
        font-weight: 600;
      }

      .coach-info p {
        margin: 0;
        color: #6c757d;
        font-size: 14px;
      }

      .coach-response {
        background: #f8f9fa;
        border-radius: 8px;
        padding: 20px;
        line-height: 1.6;
        color: #495057;
        white-space: pre-line;
      }

      /* Assignment Detail Section */
      .assignment-section {
        background: white;
        border-radius: 12px;
        padding: 30px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
      }

      .assignment-info {
        color: #6c757d;
        font-size: 16px;
        margin-bottom: 20px;
      }

      .start-quiz-btn {
        background: #28a745;
        color: white;
        border: none;
        padding: 12px 24px;
        border-radius: 8px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
        float: right;
      }

      .start-quiz-btn:hover {
        background: #218838;
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(40, 167, 69, 0.4);
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

      /* Loading spinner */
      .loading {
        display: none;
        text-align: center;
        padding: 20px;
      }

      .spinner {
        border: 3px solid #f3f3f3;
        border-top: 3px solid #667eea;
        border-radius: 50%;
        width: 30px;
        height: 30px;
        animation: spin 1s linear infinite;
        margin: 0 auto 10px;
      }

      @keyframes spin {
        0% {
          transform: rotate(0deg);
        }
        100% {
          transform: rotate(360deg);
        }
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
              <div
                class="menu-wrapper d-flex align-items-center justify-content-end"
              >
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
                      <li>
                        <a
                          href="${pageContext.request.contextPath}/logout"
                          class="btn"
                          >Logout</a
                        >
                      </li>
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
    <div class="container">
      <div class="main-container">
        <!-- Sidebar -->
        <div class="sidebar">
          <div class="sidebar-header">Course Content</div>
          <ul class="lesson-list">
            <c:forEach var="lessonItem" items="${lessons}" varStatus="status">
              <li
                class="lesson-item ${lessonItem.lessonId == lesson.lessonId ? 'active' : ''}"
              >
                <div class="lesson-number">${status.index + 1}</div>
                <span>${lessonItem.title}</span>
              </li>
            </c:forEach>
            <li class="lesson-item quiz-item active">
              <div class="lesson-number">Q</div>
              <span>Practice Quiz</span>
            </li>
          </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
          <!-- AI Coaching Section -->
          <div class="ai-coaching-section">
            <h2 class="section-title">AI Coaching</h2>
            <p class="ai-prompt">
              Ready to review what you've learned before starting the practice
              quiz? I'm here to help.
            </p>
            <button class="help-btn" onclick="generateAIPrompt()">
              Help me practice
            </button>
          </div>

          <!-- Coach Response Section -->
          <div class="coach-response-section" id="coachResponse">
            <div class="coach-header">
              <div class="coach-avatar">
                <i class="fas fa-robot"></i>
              </div>
              <div class="coach-info">
                <h4>Coach</h4>
                <p>AI Assistant</p>
              </div>
            </div>
                         <div class="loading" id="loading">
               <div class="spinner"></div>
               <p>Generating response...</p>
             </div>
            <div class="coach-response" id="aiResponse"></div>
          </div>

          <!-- Assignment Detail Section -->
          <div class="assignment-section">
            <h2 class="section-title">Assignment detail</h2>
            <p class="assignment-info">Total question: 10 questions</p>
            <button class="start-quiz-btn" onclick="startQuiz()">
              Start practice quiz
            </button>
            <div style="clear: both"></div>
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
                      <a href="index.jsp"
                        ><img src="assets/img/logo/logo2_footer.png" alt=""
                      /></a>
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
                      <script>
                        document.write(new Date().getFullYear());
                      </script>
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
      function generateAIPrompt() {
        const coachResponse = document.getElementById("coachResponse");
        const loading = document.getElementById("loading");
        const aiResponse = document.getElementById("aiResponse");

        // Hiển thị section và loading
        coachResponse.style.display = "block";
        loading.style.display = "block";
        aiResponse.style.display = "none";

                 // Call AJAX to get response from AI
         fetch("customer-quiz-preparation", {
           method: "POST",
           headers: {
             "Content-Type": "application/x-www-form-urlencoded",
           },
           body: new URLSearchParams({
             courseId: "${course.course_id}",
             lessonId: "${lesson.lessonId}",
           }),
         })
          .then((response) => response.text())
                     .then((data) => {
             // Hide loading and show response
             loading.style.display = "none";
             aiResponse.style.display = "block";
             aiResponse.textContent = data;
           })
           .catch((error) => {
             loading.style.display = "none";
             aiResponse.style.display = "block";
             aiResponse.textContent =
               "An error occurred while generating the response. Please try again.";
             console.error("Error:", error);
           });
      }

             function startQuiz() {
         // Redirect to quiz page
         window.location.href =
           "video-quiz?courseId=${course.course_id}&lessonId=${lesson.lessonId}";
       }
    </script>
  </body>
</html>
