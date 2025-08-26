<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Profile</title>
    <!-- CSS from index.jsp -->
    <link rel="manifest" href="assets/img/site.webmanifest">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="assets/css/slicknav.css">
    <link rel="stylesheet" href="assets/css/flaticon.css">
    <link rel="stylesheet" href="assets/css/progressbar_barfiller.css">
    <link rel="stylesheet" href="assets/css/gijgo.css">
    <link rel="stylesheet" href="assets/css/animate.min.css">
    <link rel="stylesheet" href="assets/css/animated-headline.css">
    <link rel="stylesheet" href="assets/css/magnific-popup.css">
    <link rel="stylesheet" href="assets/css/fontawesome-all.min.css">
    <link rel="stylesheet" href="assets/css/themify-icons.css">
    <link rel="stylesheet" href="assets/css/slick.css">
    <link rel="stylesheet" href="assets/css/nice-select.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7f9;
            color: #333;
        }
        .main-content {
            padding: 30px;
        }
        .main-header {
            margin-bottom: 20px;
            text-align: center;
        }
        .profile-view {
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            max-width: 600px;
            margin: 0 auto;
            position: relative;
        }
        .profile-view h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .profile-field {
            margin-bottom: 20px;
        }
        .profile-field label {
            display: block;
            font-weight: 500;
            margin-bottom: 5px;
        }
        .profile-field span {
            display: block;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #f9f9f9;
        }
        .avatar-preview {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            display: block;
            margin: 0 auto 10px;
            border: 2px solid #3498db;
            position: relative;
        }
        .edit-btn {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
            font-size: 12px;
            position: absolute;
            top: 105px;
            left: 63%;
            transform: translateX(-50%);
        }
        .edit-btn:hover {
            background-color: #2980b9;
        }
        .header-area {
            padding: 20px 0;
            background-color: blueviolet;
            color: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            text-align: center;
        }
        .header-area h1 {
            margin: 0;
            font-size: 24px;
            font-weight: 700;
            color: white;
        }
        .back-btn {
            position: absolute;
            top: 15px;
            right: 20px;
            background-color: #ffffff;
            color: blueviolet;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        .back-btn:hover {
            background-color: #e6e6e6;
            color: #6a0dad;
        }
        .success-message {
            color: #2ecc71;
            background-color: #e6ffe6;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        .error-message {
            color: #ff4d4d;
            background-color: #ffe6e6;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
    </style>
    <script>
        // Lưu trang ban đầu khi đăng nhập
        let initialPage = '<c:out value="${sessionScope.initialPage}" />';

        document.addEventListener('DOMContentLoaded', function() {
            const role = '<c:out value="${sessionScope.user.role}" />';
            let backText = "Back to Admin";
            if (role === 'customer') {
                backText = "Back to Customer";
                initialPage = initialPage || '${pageContext.request.contextPath}/home';
            } else if (role === 'seller') {
                backText = "Back to Seller";
                initialPage = initialPage || '${pageContext.request.contextPath}/DashBoard';
            } else if (role === 'admin') {
                backText = "Back to Admin";
                initialPage = initialPage || '${pageContext.request.contextPath}/overviewadmin';
            }

            const backButton = document.querySelector('.back-btn');
            if (backButton && initialPage) {
                backButton.textContent = backText;
            } else {
                if (backButton) backButton.style.display = 'none';
            }
        });

        function goBack() {
            if (initialPage) {
                window.location.href = initialPage;
            }
        }
    </script>
</head>
<body>
    <header class="header-area">
        <div class="main-header">
            <div class="header-bottom header-sticky">
                <div class="container-fluid">
                    <div class="row align-items-center">
                        <div class="col-12">
                            <h1>Welcome, <c:out value="${sessionScope.user.firstName} ${sessionScope.user.middleName} ${sessionScope.user.lastName}"/></h1>
                            <button class="back-btn" onclick="goBack()">Back</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>

    <main class="main-content">
        <div class="profile-view">
            <h2>View Profile</h2>
            <c:if test="${not empty sessionScope.message}">
                <div class="success-message">${sessionScope.message}</div>
                <% session.removeAttribute("message"); %> <!-- Xóa thông báo sau khi hiển thị -->
            </c:if>
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            <div class="profile-field" style="position: relative;">
                <label>Profile Picture</label>
                <a href="updateprofile" class="edit-btn">Edit</a>
                <img src="${empty user.avataUrl ? 'assets/img/blog/author.png' : user.avataUrl}" alt="Avatar" class="avatar-preview">
            </div>
            <div class="profile-field">
                <label>First Name</label>
                <span>${user.firstName}</span>
            </div>
            <div class="profile-field">
                <label>Middle Name</label>
                <span>${user.middleName}</span>
            </div>
            <div class="profile-field">
                <label>Last Name</label>
                <span>${user.lastName}</span>
            </div>
            <div class="profile-field">
                <label>Phone Number</label>
                <span>${user.phone}</span>
            </div>
            <div class="profile-field">
                <label>Address</label>
                <span>${user.address}</span>
            </div>
            <div class="profile-field">
                <label>Email</label>
                <span>${user.email}</span>
            </div>
            <div class="profile-field">
                <label>Create At</label>
                <span><fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy HH:mm" /></span>
            </div>
            <div class="profile-field">
                <label>Update At</label>
                <span><fmt:formatDate value="${user.updatedAt}" pattern="dd/MM/yyyy HH:mm" /></span>
            </div>
            <div class="profile-field">
                <label>Role</label>
                <span>${user.role}</span>
            </div>
            <div class="profile-field">
                <label>Account Status</label>
                <span>${user.accountStatus}</span>
            </div>          
        </div>
    </main>

    <footer>
        <div class="footer-wrappper footer-bg">
            <!-- Footer Start-->
            <div class="footer-area footer-padding">
                <div class="container">
                    <div class="row justify-content-between">
                        <div class="col-xl-4 col-lg-5 col-md-4 col-sm-6">
                            <div class="single-footer-caption mb-50">
                                <div class="single-footer-caption mb-30">
                                    <!-- logo -->
                                    <div class="footer-logo mb-25">
                                        <a href="index.jsp"><img src="assets/img/logo/logo2_footer.png" alt=""></a>
                                    </div>
                                    <div class="footer-tittle">
                                        <div class="footer-pera">
                                            <p>The automated process starts as soon as your clothes go into the machine.</p>
                                        </div>
                                    </div>
                                    <!-- social -->
                                    <div class="footer-social">
                                        <a href="#"><i class="fab fa-twitter"></i></a>
                                        <a href="https://bit.ly/sai4ull"><i class="fab fa-facebook-f"></i></a>
                                        <a href="#"><i class="fab fa-pinterest-p"></i></a>
                                    </div>
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
            <!-- footer-bottom area -->
            <div class="footer-bottom-area">
                <div class="container">
                    <div class="footer-border">
                        <div class="row d-flex align-items-center">
                            <div class="col-xl-12 ">
                                <div class="footer-copy-right text-center">
                                    <p>Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Footer End-->
        </div>
    </footer>

    <div id="back-top">
        <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
    </div>

    <script src="./assets/js/vendor/modernizr-3.5.0.min.js"></script>
    <script src="./assets/js/vendor/jquery-1.12.4.min.js"></script>
    <script src="./assets/js/popper.min.js"></script>
    <script src="./assets/js/bootstrap.min.js"></script>
    <script src="./assets/js/jquery.slicknav.min.js"></script>
    <script src="./assets/js/owl.carousel.min.js"></script>
    <script src="./assets/js/slick.min.js"></script>
    <script src="./assets/js/wow.min.js"></script>
    <script src="./assets/js/animated.headline.js"></script>
    <script src="./assets/js/jquery.magnific-popup.js"></script>
    <script src="./assets/js/gijgo.min.js"></script>
    <script src="./assets/js/jquery.nice-select.min.js"></script>
    <script src="./assets/js/jquery.sticky.js"></script>
    <script src="./assets/js/jquery.barfiller.js"></script>
    <script src="./assets/js/jquery.counterup.min.js"></script>
    <script src="./assets/js/waypoints.min.js"></script>
    <script src="./assets/js/jquery.countdown.min.js"></script>
    <script src="./assets/js/hover-direction-snake.min.js"></script>
    <script src="./assets/js/contact.js"></script>
    <script src="./assets/js/jquery.form.js"></script>
    <script src="./assets/js/jquery.validate.min.js"></script>
    <script src="./assets/js/mail-script.js"></script>
    <script src="./assets/js/jquery.ajaxchimp.min.js"></script>
    <script src="./assets/js/plugins.js"></script>
    <script src="./assets/js/main.js"></script>
</body>
</html>