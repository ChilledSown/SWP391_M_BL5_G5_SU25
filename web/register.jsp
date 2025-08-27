<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>App landing</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="manifest" href="site.webmanifest">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

    <!-- CSS here -->
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
    
</head>
<body>
    <!-- Preloader Start -->
    <div id="preloader-active">
        <div class="preloader d-flex align-items-center justify-content-center">
            <div class="preloader-inner position-relative">
                <div class="preloader-circle"></div>
                <div class="preloader-img pere-text">
                    <img src="assets/img/logo/loder.png" alt="">
                </div>
            </div>
        </div>
    </div>
    <!-- Preloader Start -->

    <!-- Register -->
    <main class="login-body" data-vide-bg="assets/img/login-bg.mp4">
        <form class="form-default" action="register" method="POST">
            <div class="login-form">
                <!-- logo-login -->
                <div class="logo-login">
                    <a href="index.jsp"><img src="assets/img/logo/loder.png" alt=""></a>
                </div>
                <h2>Registration Here</h2>

                <div class="form-input">
                    <label for="firstName">First Name</label>
                    <input type="text" name="firstName" value="${firstName != null ? firstName : ''}" placeholder="First Name" >
                </div>
                <div class="form-input">
                    <label for="middleName">Middle Name</label>
                    <input type="text" name="middleName" value="${middleName != null ? middleName : ''}" placeholder="Middle Name" >
                </div>
                <div class="form-input">
                    <label for="lastName">Last Name</label>
                    <input type="text" name="lastName" value="${lastName != null ? lastName : ''}" placeholder="Last Name" >
                </div>
                <div class="form-input">
                    <label for="email">Email Address</label>
                    <input type="email" name="email" value="${email != null ? email : ''}" placeholder="example@gmail.com" >
                </div>
                <div class="form-input">
                    <label for="password">Password</label>
                    <input type="password" name="password" placeholder="******" >
                </div>
                <div class="form-input">
                    <label for="phone">Phone</label>
                    <input type="text" name="phone" value="${phone != null ? phone : ''}" placeholder="03********" >
                </div>
                <div class="form-input">
                    <label for="address">Address</label>
                    <input type="text" name="address" value="${address != null ? address : ''}" placeholder="Address" >
                </div>
                <div class="form-input pt-30">
                    <input type="submit" name="submit" value="Registration">
                </div>
                <div style="color:white">${message}</div>
                <!-- Forget Password -->
                <a href="login" class="registration">Login</a>
            </div>
        </form>
    </main>

    <!-- JavaScript files -->
    <script src="./assets/js/vendor/modernizr-3.5.0.min.js"></script>
    <script src="./assets/js/vendor/jquery-1.12.4.min.js"></script>
    <script src="./assets/js/popper.min.js"></script>
    <script src="./assets/js/bootstrap.min.js"></script>
    <script src="./assets/js/jquery.slicknav.min.js"></script>
    <script src="./assets/js/jquery.vide.js"></script>
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