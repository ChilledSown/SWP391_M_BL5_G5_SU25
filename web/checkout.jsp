<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Checkout | Education</title>
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
    
    <style>
        .checkout-section {
            padding: 80px 0;
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            min-height: 80vh;
        }
        .checkout-section .container { max-width: none; width: 100%; padding-left: 0; padding-right: 0; }
        
        .checkout-header { display: none; }
        
        .checkout-header::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><defs><pattern id="grain" width="100" height="100" patternUnits="userSpaceOnUse"><circle cx="25" cy="25" r="1" fill="rgba(255,255,255,0.1)"/><circle cx="75" cy="75" r="1" fill="rgba(255,255,255,0.1)"/><circle cx="50" cy="10" r="0.5" fill="rgba(255,255,255,0.1)"/><circle cx="10" cy="60" r="0.5" fill="rgba(255,255,255,0.1)"/><circle cx="90" cy="40" r="0.5" fill="rgba(255,255,255,0.1)"/></pattern></defs><rect width="100" height="100" fill="url(%23grain)"/></svg>');
            opacity: 0.3;
        }
        
        .checkout-title {
            font-size: 2.8rem;
            font-weight: 700;
            margin: 0;
            position: relative;
            z-index: 2;
        }
        
        .checkout-subtitle {
            font-size: 1.1rem;
            margin: 10px 0 0 0;
            opacity: 0.9;
            position: relative;
            z-index: 2;
        }
        
        .checkout-content {
            background: white;
            border-radius: 0;
            overflow: hidden;
            width: 100%;
        }
        
        .checkout-grid {
            display: grid;
            grid-template-columns: 2fr 1fr;
            gap: 0;
            min-height: 600px;
        }
        
        .left-column {
            padding: 40px;
            border-right: 1px solid #e9ecef;
            width: 100%;
        }
        
        .right-column {
            padding: 40px;
            background: #f8f9fa;
            width: 100%;
        }
        
        .order-summary {
            margin-bottom: 40px;
        }
        
        .order-summary h3 {
            color: #2c3e50;
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 25px;
            text-align: left;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .order-item {
            display: flex;
            align-items: center;
            gap: 20px;
            padding: 20px 0;
            border-bottom: 1px solid #e9ecef;
        }
        
        .order-item:last-child {
            border-bottom: none;
        }
        
        .order-image {
            width: 80px;
            height: 60px;
            border-radius: 10px;
            overflow: hidden;
            flex-shrink: 0;
        }
        
        .order-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .order-details {
            flex: 1;
            min-width: 0;
        }
        
        .order-title {
            font-size: 1.1rem;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 5px;
        }
        
        .order-description {
            color: #6c757d;
            font-size: 0.9rem;
            line-height: 1.4;
        }
        
        .order-price {
            font-size: 1.2rem;
            font-weight: 600;
            color: #e74c3c;
            text-align: right;
            white-space: nowrap;
        }
        
        .payment-section {
            margin-bottom: 40px;
        }
        
        .payment-section h3 {
            color: #2c3e50;
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 25px;
            text-align: left;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .payment-methods {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        
        .payment-method {
            border: 2px solid #e9ecef;
            border-radius: 12px;
            padding: 25px 20px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            background: white;
            position: relative;
        }
        
        .payment-method:hover {
            border-color: #667eea;
            transform: translateY(-2px);
        }
        
        .payment-method.selected {
            border-color: #667eea;
            background: #f8f9ff;
        }
        
        .payment-method.selected::after {
            content: '✓';
            position: absolute;
            top: 10px;
            right: 15px;
            background: #667eea;
            color: white;
            width: 24px;
            height: 24px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
            font-weight: bold;
        }
        
        .payment-method i {
            font-size: 2.5rem;
            color: #667eea;
            margin-bottom: 15px;
        }
        
        .payment-method h4 {
            font-size: 1.1rem;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 8px;
        }
        
        .payment-method p {
            color: #6c757d;
            font-size: 0.9rem;
            margin: 0;
        }
        
        .right-column h3 {
            color: #2c3e50;
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 25px;
            text-align: center;
        }
        
        .price-breakdown {
            background: white;
            border-radius: 15px;
            padding: 25px;
            margin-bottom: 30px;
            box-shadow: none;
            width: 100%;
        }
        
        .price-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            padding: 10px 0;
        }
        
        .price-row:last-child {
            margin-bottom: 0;
        }
        
        .price-label {
            font-weight: 500;
            color: #6c757d;
        }
        
        .price-value {
            font-weight: 600;
            color: #2c3e50;
        }
        
        .total-row {
            border-top: 2px solid #e9ecef;
            padding-top: 15px;
            margin-top: 15px;
        }
        
        .total-label {
            font-size: 1.2rem;
            font-weight: 700;
            color: #2c3e50;
        }
        
        .total-value {
            font-size: 1.5rem;
            font-weight: 700;
            color: #e74c3c;
        }
        
        .checkout-actions {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        
        .btn-back {
            padding: 15px 25px;
            background: #95a5a6;
            color: white;
            border: none;
            border-radius: 12px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
            text-align: center;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
        }
        
        .btn-back:hover {
            background: #7f8c8d;
            color: white;
            text-decoration: none;
            transform: translateY(-2px);
        }
        
        .paypal-container {
            width: 100%;
            display: flex;
            justify-content: center;
        }
        
        .secure-badge {
            text-align: center;
            margin-top: 25px;
            padding: 20px;
            background: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 12px;
            color: #155724;
            box-shadow: none;
            width: 100%;
        }
        
        .secure-badge i {
            margin-right: 8px;
            color: #28a745;
        }
        
        .empty-cart-message {
            text-align: center;
            padding: 60px 20px;
            background: white;
            border-radius: 0;
            width: 100%;
        }
        
        .empty-cart-icon {
            font-size: 4rem;
            color: #bdc3c7;
            margin-bottom: 20px;
        }
        
        .empty-cart-title {
            font-size: 1.5rem;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 15px;
        }
        
        .empty-cart-text {
            color: #7f8c8d;
            margin-bottom: 30px;
        }
        
        .btn-primary {
            padding: 15px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 12px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
            display: inline-block;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            color: white;
            text-decoration: none;
        }
        
        .loading-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.7);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 9999;
        }
        
        .loading-spinner {
            width: 60px;
            height: 60px;
            border: 4px solid #f3f3f3;
            border-top: 4px solid #667eea;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .combined-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); position: relative; overflow: hidden; }
        .combined-header::before { content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0; background: rgba(255,255,255,0.1); backdrop-filter: blur(10px); }
        .header-top { padding: 20px 0; border-bottom: 1px solid rgba(255,255,255,0.2); position: relative; z-index: 2; }
        .page-header-content { padding: 40px 0; position: relative; z-index: 2; text-align: center; color: white; }
        #navigation { display: flex; align-items: center; justify-content: center; gap: 0; margin: 0; padding: 0; list-style: none; }
        #navigation li { margin: 0; padding: 0; display: flex; align-items: center; }
        #navigation li a { color: white !important; font-weight: 500; font-size: 16px; text-decoration: none; padding: 12px 20px; border-radius: 8px; transition: all 0.3s ease; display: block; position: relative; margin: 0 5px; }
        #navigation li a::after { content: ''; position: absolute; bottom: 0; left: 50%; width: 0; height: 2px; background: white; transition: all 0.3s ease; transform: translateX(-50%); }
        /* Disable hover effects for non-button nav links */
        #navigation li a:not(.btn):hover { color: white !important; background: transparent !important; transform: none !important; box-shadow: none !important; backdrop-filter: none !important; }
        #navigation li a:not(.btn):hover::after { width: 0 !important; }
        .logo img { max-height: 40px; }
    </style>
</head>

<body>
    <!-- ? Preloader Start -->
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
                                        <li><a href="home">Home</a></li>
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

    <main>
        <section class="checkout-section">
            <div class="container">
                <div class="checkout-container">
                    <div class="checkout-header">
                        <h1 class="checkout-title">Secure Checkout</h1>
                        <p class="checkout-subtitle">Complete your purchase with confidence</p>
                    </div>
                    
                    <c:choose>
                        <c:when test="${empty cartItems}">
                            <div class="empty-cart-message">
                                <div class="empty-cart-icon">
                                    <i class="fas fa-shopping-cart"></i>
                                </div>
                                <h2 class="empty-cart-title">Your cart is empty</h2>
                                <p class="empty-cart-text">Looks like you haven't added any courses to your cart yet.</p>
                                <a href="cart" class="btn-primary">Return to Cart</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="checkout-content">
                                <div class="checkout-grid">
                                    <!-- Left Column - Order Details & Payment -->
                                    <div class="left-column">
                                        <!-- Order Summary -->
                                        <div class="order-summary">
                                            <h3><i class="fas fa-shopping-bag"></i> Order Summary</h3>
                                            <c:forEach items="${cartItems}" var="cartItem">
                                                <div class="order-item">
                                                    <div class="order-image">
                                                        <img src="${cartItem.courseThumbnail}" alt="${cartItem.courseTitle}">
                                                    </div>
                                                    <div class="order-details">
                                                        <h4 class="order-title">${cartItem.courseTitle}</h4>
                                                        <p class="order-description">${cartItem.courseDescription}</p>
                                                    </div>
                                                    <div class="order-price">$${cartItem.price}</div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        
                                        <!-- Payment Methods -->
                                        <div class="payment-section">
                                            <h3><i class="fas fa-credit-card"></i> Payment Method</h3>
                                            <div class="payment-methods">
                                                <div class="payment-method selected" onclick="selectPaymentMethod(this, 'paypal')">
                                                    <i class="fab fa-paypal"></i>
                                                    <h4>PayPal</h4>
                                                    <p>Fast & Secure Payment</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <!-- Right Column - Price Summary & Checkout -->
                                    <div class="right-column">
                                        <h3><i class="fas fa-calculator"></i> Price Summary</h3>
                                        
                                        <!-- Price Breakdown -->
                                        <div class="price-breakdown">
                                            <c:set var="subtotal" value="0" />
                                            <c:forEach items="${cartItems}" var="cartItem">
                                                <c:set var="subtotal" value="${subtotal + cartItem.price}" />
                                                <div class="price-row">
                                                    <span class="price-label">${cartItem.courseTitle}</span>
                                                    <span class="price-value">$${cartItem.price}</span>
                                                </div>
                                            </c:forEach>
                                            
                                            <div class="price-row total-row">
                                                <span class="price-label total-label">Total Amount</span>
                                                <span class="price-value total-value">$${cartTotal}</span>
                                            </div>
                                        </div>
                                        
                                        <!-- Checkout Actions -->
                                        <div class="checkout-actions">
                                            <a href="cart" class="btn-back">
                                                <i class="fas fa-arrow-left"></i> Back to Cart
                                            </a>
                                            
                                            <div class="paypal-container" id="paypal-button-container">
                                                <!-- PayPal button will be rendered here -->
                                            </div>
                                        </div>
                                        
                                        <!-- Security Badge -->
                                        <div class="secure-badge">
                                            <i class="fas fa-shield-alt"></i>
                                            <strong>Secure Checkout:</strong> Your payment information is encrypted and secure
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>
    </main>

    <!-- Loading Overlay -->
    <div class="loading-overlay" id="loadingOverlay">
        <div class="loading-spinner"></div>
    </div>

    <!-- Footer Start-->
    <footer>
        <div class="footer-wrappper footer-bg">
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
                                    <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                      Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="fa fa-heart" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank">Colorlib</a>
                                      <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
                                  </div>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
          <!-- Footer End-->
      </div>
  </footer> 
  <!-- Scroll Up -->
  <div id="back-top" >
    <a title="Go to Top" href="#"> <i class="fas fa-level-up-alt"></i></a>
</div>

<!-- JS here -->
<script src="assets/js/vendor/modernizr-3.5.0.min.js"></script>
<script src="assets/js/vendor/jquery-1.12.4.min.js"></script>
<script src="assets/js/popper.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/owl.carousel.min.js"></script>
<script src="assets/js/slick.min.js"></script>
<script src="assets/js/jquery.slicknav.min.js"></script>
<script src="assets/js/wow.min.js"></script>
<script src="assets/js/animated.headline.js"></script>
<script src="assets/js/jquery.magnific-popup.js"></script>
<script src="assets/js/gijgo.min.js"></script>
<script src="assets/js/jquery.nice-select.min.js"></script>
<script src="assets/js/jquery.sticky.js"></script>
<script src="assets/js/contact.js"></script>
<script src="assets/js/jquery.form.js"></script>
<script src="assets/js/jquery.validate.min.js"></script>
<script src="assets/js/mail-script.js"></script>
<script src="assets/js/jquery.ajaxchimp.min.js"></script>
<script src="assets/js/plugins.js"></script>
<script src="assets/js/main.js"></script>

<script>
    function selectPaymentMethod(element, method) {
        document.querySelectorAll('.payment-method').forEach(el => {
            el.classList.remove('selected');
        });
        
        element.classList.add('selected');
        if (method === 'paypal') {
            document.getElementById('paypal-button-container').style.display = 'block';
        } else {
            document.getElementById('paypal-button-container').style.display = 'none';
        }
    }
    
    function showLoading() {
        document.getElementById('loadingOverlay').style.display = 'flex';
    }
    function hideLoading() {
        document.getElementById('loadingOverlay').style.display = 'none';
    }
    document.addEventListener('DOMContentLoaded', function() {
        // Initialize payment method selection
        selectPaymentMethod(document.querySelector('.payment-method.selected'), 'paypal');
    });
</script>
<script src="https://www.paypal.com/sdk/js?client-id=ATdH4OWCF17eQ5EJcvqaswbwhnxjceeobCVEzGY4qMrECabo_aAHhmGIbja5Cmy3ppxGUfRDRKc9z4xw&currency=USD"></script>
<script>
    (function renderPaypalButton() {
        var container = document.getElementById('paypal-button-container');
        if (!container) return;
        
        var total = '${cartTotal}';
        if (!total || total === '0' || total === '0.00') {
            container.innerHTML = '<div class="text-center"><p class="text-muted">No items to checkout</p><a href="cart" class="btn-primary">Return to Cart</a></div>';
            return;
        }

        if (typeof paypal === 'undefined') {
            console.error('PayPal SDK not loaded');
            container.innerHTML = '<div class="text-center"><p class="text-danger">Payment system temporarily unavailable</p></div>';
            return;
        }

        paypal.Buttons({
            style: { 
                shape: 'pill', 
                color: 'gold', 
                layout: 'vertical', 
                label: 'paypal',
                height: 50
            },
            createOrder: function (data, actions) {
                showLoading();
                // First, create order in the system 
                return fetch('process-checkout', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
                })
                .then(function(res) {
                    if (!res.ok) {
                        hideLoading();
                        throw new Error('Failed to create order');
                    }
                    // Proceed to create PayPal order
                    return actions.order.create({
                        purchase_units: [{
                            amount: { value: String(total), currency_code: 'USD' },
                            description: 'Course Purchase - Education Platform'
                        }]
                    });
                })
                .catch(function(error) {
                    hideLoading();
                    console.error('Error creating order:', error);
                    alert('Failed to create order: ' + error.message);
                    throw error;
                });
            },
            onApprove: function (data, actions) {
                showLoading();
                return actions.order.capture().then(function (details) {
                    hideLoading();
                    var amount = String(total);
                    var currency = 'USD';
                    var paypalOrderId = details.id;
                    // Redirect to success page; servlet will use session pendingOrderId
                    window.location.href = 'payment-success?paypalOrderId=' + encodeURIComponent(paypalOrderId) +
                                          '&amount=' + encodeURIComponent(amount) +
                                          '&currency=' + encodeURIComponent(currency);
                });
            },
            onCancel: function (data) {
                hideLoading();
                console.log('Payment cancelled');
            },
            onError: function (err) {
                hideLoading();
                console.error(err);
                alert('Payment failed. Please try again or contact support.');
            }
        }).render('#paypal-button-container');
    })();
</script>

</body>
</html>
