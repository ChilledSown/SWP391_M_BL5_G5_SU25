<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Create Video Quiz | Seller Dashboard</title>
    <meta name="description" content="Seller dashboard for creating video quizzes">
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
        .input-group-text {
            background-color: #f8f9fa;
            border-radius: 6px 0 0 6px;
            font-weight: 500;
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
        .btn-back {
            background-color: #ff8243;
            color: white;
            border: none;
            border-radius: 8px;
            padding: 10px 20px;
            font-size: 14px;
            transition: background-color 0.2s ease-in-out;
        }
        .btn-back:hover {
            background-color: #e67030;
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
        .error-message {
            color: #dc3545;
            font-size: 0.9rem;
            margin-top: 5px;
            display: none;
        }
        .error-message.show {
            display: block;
        }
        .form-text {
            font-size: 0.9rem;
            color: #6c757d;
            margin-top: 5px;
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
            }
            .form-control {
                font-size: 0.9rem;
            }
            .btn-primary, .btn-back {
                width: 100%;
                text-align: center;
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
                            <h2>Create New Video Quiz</h2>
                            <p>Fill in the details to create a new video quiz.</p>
                            <c:if test="${not empty message}">
                                <div class="alert alert-success">${fn:escapeXml(message)}</div>
                            </c:if>
                            <c:if test="${not empty error}">
                                <div class="alert alert-danger">${fn:escapeXml(error)}</div>
                            </c:if>
                            <form action="instructorvideoquiz?action=create" method="post" id="createForm">
                                <input type="hidden" name="answerOptions" id="answerOptions">
                                <input type="hidden" name="correctAnswer" id="correctAnswer">
                                <div class="row">
                                    <div class="col-md-6 form-group">
                                        <label for="lessonId" class="mb-2">Lesson</label>
                                        <select name="lessonId" id="lessonId" class="form-control" required>
                                            <option value="" disabled ${empty submittedLessonId ? 'selected' : ''}>Select Lesson</option>
                                            <c:forEach var="lesson" items="${lessons}">
                                                <option value="${lesson.lessonId}" ${lesson.lessonId == submittedLessonId ? 'selected' : ''}>
                                                    ${fn:escapeXml(lesson.title)}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <div class="error-message" id="lessonIdError"></div>
                                    </div>
                                    <div class="col-md-6 form-group">
                                        <label for="timestamp" class="mb-2">Timestamp (seconds)</label>
                                        <input type="number" class="form-control" name="timestamp" id="timestamp" min="0" step="1" value="${submittedTimestamp}" required placeholder="Enter timestamp in seconds">
                                        <div class="error-message" id="timestampError"></div>
                                    </div>
                                    <div class="col-12 form-group">
                                        <label for="question" class="mb-2">Question</label>
                                        <input type="text" class="form-control" name="question" id="question" value="${fn:escapeXml(submittedQuestion)}" required placeholder="Enter the quiz question" maxlength="500">
                                        <div class="error-message" id="questionError"></div>
                                    </div>
                                    <div class="col-12 form-group">
                                        <label class="mb-2">Answer Options (at least two required)</label>
                                        <div class="input-group mb-2">
                                            <span class="input-group-text">A</span>
                                            <input type="text" class="form-control" name="answerOptionA" id="answerOptionA" value="${fn:escapeXml(submittedAnswerOptionA)}" placeholder="Enter option A" maxlength="250">
                                        </div>
                                        <div class="error-message" id="answerOptionAError"></div>
                                        <div class="input-group mb-2">
                                            <span class="input-group-text">B</span>
                                            <input type="text" class="form-control" name="answerOptionB" id="answerOptionB" value="${fn:escapeXml(submittedAnswerOptionB)}" placeholder="Enter option B" maxlength="250">
                                        </div>
                                        <div class="error-message" id="answerOptionBError"></div>
                                        <div class="input-group mb-2">
                                            <span class="input-group-text">C</span>
                                            <input type="text" class="form-control" name="answerOptionC" id="answerOptionC" value="${fn:escapeXml(submittedAnswerOptionC)}" placeholder="Enter option C" maxlength="250">
                                        </div>
                                        <div class="error-message" id="answerOptionCError"></div>
                                        <div class="input-group mb-2">
                                            <span class="input-group-text">D</span>
                                            <input type="text" class="form-control" name="answerOptionD" id="answerOptionD" value="${fn:escapeXml(submittedAnswerOptionD)}" placeholder="Enter option D" maxlength="250">
                                        </div>
                                        <div class="error-message" id="answerOptionDError"></div>
                                    </div>
                                    <div class="col-12 form-group">
                                        <label class="mb-2">Correct Answers (select at least one)</label>
                                        <div class="form-check">
                                            <input type="checkbox" class="form-check-input correct-checkbox" id="correctA" name="correctLetters[]" value="A">
                                            <label class="form-check-label" for="correctA">A</label>
                                        </div>
                                        <div class="form-check">
                                            <input type="checkbox" class="form-check-input correct-checkbox" id="correctB" name="correctLetters[]" value="B">
                                            <label class="form-check-label" for="correctB">B</label>
                                        </div>
                                        <div class="form-check">
                                            <input type="checkbox" class="form-check-input correct-checkbox" id="correctC" name="correctLetters[]" value="C">
                                            <label class="form-check-label" for="correctC">C</label>
                                        </div>
                                        <div class="form-check">
                                            <input type="checkbox" class="form-check-input correct-checkbox" id="correctD" name="correctLetters[]" value="D">
                                            <label class="form-check-label" for="correctD">D</label>
                                        </div>
                                        <div class="form-text">Select the letters corresponding to the correct answers (e.g., A for Option A).</div>
                                        <div class="error-message" id="correctLettersError"></div>
                                    </div>
                                    <div class="col-12 form-group">
                                        <label for="explanation" class="mb-2">Explanation (optional)</label>
                                        <textarea class="form-control" name="explanation" id="explanation" rows="3" placeholder="Enter explanation" maxlength="1000">${fn:escapeXml(submittedExplanation)}</textarea>
                                        <div class="error-message" id="explanationError"></div>
                                    </div>
                                    <div class="col-12 form-group">
                                        <button type="submit" class="btn-primary">Create Quiz</button>
                                        <a href="instructorvideoquiz" class="btn-back" title="Back to List">
                                            <i class="fas fa-arrow-left"></i> Back
                                        </a>
                                    </div>
                                </div>
                            </form>
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
                    // Redirect on success message
                    const message = $('.alert-success').text();
                    if (message && message.trim() !== '') {
                        setTimeout(function() {
                            window.location.href = 'instructorvideoquiz';
                        }, 2000);
                    }

                    // Update correct options based on input
                    function updateCorrectOptions() {
                        const optionA = $('#answerOptionA').val().trim();
                        const optionB = $('#answerOptionB').val().trim();
                        const optionC = $('#answerOptionC').val().trim();
                        const optionD = $('#answerOptionD').val().trim();

                        $('#correctA').prop('disabled', !optionA);
                        if (!optionA) $('#correctA').prop('checked', false);
                        $('#correctB').prop('disabled', !optionB);
                        if (!optionB) $('#correctB').prop('checked', false);
                        $('#correctC').prop('disabled', !optionC);
                        if (!optionC) $('#correctC').prop('checked', false);
                        $('#correctD').prop('disabled', !optionD);
                        if (!optionD) $('#correctD').prop('checked', false);
                    }

                    $('#answerOptionA, #answerOptionB, #answerOptionC, #answerOptionD').on('input', updateCorrectOptions);
                    updateCorrectOptions();

                    $('#createForm').validate({
                        rules: {
                            lessonId: { required: true },
                            timestamp: { 
                                required: true, 
                                number: true, 
                                min: 0,
                                remote: {
                                    url: "instructorvideoquiz?action=checkTimestamp",
                                    type: "GET",
                                    data: {
                                        lessonId: function() {
                                            return $('#lessonId').val();
                                        },
                                        timestamp: function() {
                                            return $('#timestamp').val();
                                        }
                                    }
                                }
                            },
                            question: { required: true, maxlength: 500 },
                            answerOptionA: { required: true, maxlength: 250 },
                            answerOptionB: { required: true, maxlength: 250 },
                            answerOptionC: { maxlength: 250 },
                            answerOptionD: { maxlength: 250 },
                            "correctLetters[]": { 
                                required: true,
                                validCorrectLetters: true
                            },
                            explanation: { maxlength: 1000 }
                        },
                        messages: {
                            lessonId: { required: "Please select a lesson." },
                            timestamp: {
                                required: "Timestamp is required.",
                                number: "Timestamp must be a number.",
                                min: "Timestamp must be non-negative.",
                                remote: "This timestamp already exists for the selected lesson."
                            },
                            question: {
                                required: "Question is required.",
                                maxlength: "Question cannot exceed 500 characters."
                            },
                            answerOptionA: { 
                                required: "Option A is required.",
                                maxlength: "Option A cannot exceed 250 characters."
                            },
                            answerOptionB: { 
                                required: "Option B is required.",
                                maxlength: "Option B cannot exceed 250 characters."
                            },
                            answerOptionC: { maxlength: "Option C cannot exceed 250 characters." },
                            answerOptionD: { maxlength: "Option D cannot exceed 250 characters." },
                            "correctLetters[]": {
                                required: "At least one correct answer is required.",
                                validCorrectLetters: "Please select valid correct answers corresponding to non-empty options."
                            },
                            explanation: { maxlength: "Explanation cannot exceed 1000 characters." }
                        },
                        errorElement: 'div',
                        errorClass: 'error-message show',
                        errorPlacement: function(error, element) {
                            if (element.attr("name") == "correctLetters[]") {
                                error.insertAfter("#correctLettersError");
                            } else {
                                error.insertAfter(element);
                            }
                        },
                        highlight: function(element) {
                            $(element).addClass('is-invalid').removeClass('is-valid');
                            $(element).nextAll('.error-message').addClass('show');
                        },
                        unhighlight: function(element) {
                            $(element).removeClass('is-invalid').addClass('is-valid');
                            $(element).nextAll('.error-message').removeClass('show').empty();
                        },
                        submitHandler: function(form) {
                            var lessonId = $('#lessonId').val();
                            var timestamp = $('#timestamp').val();

                            var optionA = $('#answerOptionA').val().trim();
                            var optionB = $('#answerOptionB').val().trim();
                            var optionC = $('#answerOptionC').val().trim();
                            var optionD = $('#answerOptionD').val().trim();
                            var answerOptionsArray = [];
                            if (optionA) answerOptionsArray.push('A. ' + optionA);
                            if (optionB) answerOptionsArray.push('B. ' + optionB);
                            if (optionC) answerOptionsArray.push('C. ' + optionC);
                            if (optionD) answerOptionsArray.push('D. ' + optionD);
                            var answerOptions = answerOptionsArray.join('; ');

                            var correctLetters = [];
                            $('input[name="correctLetters[]"]:checked').each(function() {
                                correctLetters.push($(this).val());
                            });
                            var correctAnswerArray = [];
                            for (var i = 0; i < correctLetters.length; i++) {
                                var letter = correctLetters[i];
                                var opt = $('#answerOption' + letter).val().trim();
                                if (opt) correctAnswerArray.push(letter + '. ' + opt);
                            }
                            var correctAnswer = correctAnswerArray.join('; ');

                            $('#answerOptions').val(answerOptions);
                            $('#correctAnswer').val(correctAnswer);

                            if (answerOptionsArray.length < 2) {
                                $('#answerOptionAError').text('At least two options are required.').addClass('show');
                                return;
                            }
                            if (correctAnswerArray.length < 1) {
                                $('#correctLettersError').text('At least one correct answer is required.').addClass('show');
                                return;
                            }

                            $.ajax({
                                url: form.action,
                                type: form.method,
                                data: $(form).serialize(),
                                success: function(response) {
                                    if (response.startsWith('success:')) {
                                        $('#createForm').prepend(
                                            '<div class="alert alert-success">' + response.substring(8) + '</div>'
                                        );
                                        setTimeout(function() {
                                            window.location.href = 'instructorvideoquiz';
                                        }, 2000);
                                    } else if (response.startsWith('error:')) {
                                        $('#createForm').prepend(
                                            '<div class="alert alert-danger">' + response.substring(6) + '</div>'
                                        );
                                    } else {
                                        $('#createForm').prepend(
                                            '<div class="alert alert-danger">Unexpected server response. Please try again.</div>'
                                        );
                                    }
                                },
                                error: function(xhr) {
                                    let errorMessage = 'Failed to create quiz. Please try again.';
                                    if (xhr.responseText && xhr.responseText.startsWith('error:')) {
                                        errorMessage = xhr.responseText.substring(6);
                                    }
                                    $('#createForm').prepend(
                                        '<div class="alert alert-danger">' + errorMessage + '</div>'
                                    );
                                }
                            });
                        }
                    });

                    $.validator.addMethod('validCorrectLetters', function(value, element) {
                        var checked = $('input[name="correctLetters[]"]:checked');
                        if (checked.length === 0) return false;
                        var valid = true;
                        checked.each(function() {
                            var letter = $(this).val();
                            var optVal = $('#answerOption' + letter).val().trim();
                            if (!optVal) valid = false;
                        });
                        return valid;
                    }, 'Please select valid correct answers corresponding to non-empty options.');
                });
            </script>
        </body>
</html>