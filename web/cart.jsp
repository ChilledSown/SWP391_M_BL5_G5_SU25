<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Cart | Education</title>
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
    
    <!-- Custom CSS for Cart -->
    <style>
        .cart-section {
            padding: 80px 0;
            background: #f8f9fa;
            min-height: 70vh;
        }
        .cart-section .container { max-width: none; width: 100%; padding-left: 0; padding-right: 0; }
        
        .cart-container {
            background: white;
            border-radius: 15px;
            box-shadow: none;
            overflow: hidden;
            width: 100%;
            border-radius: 0;
        }
        
        .cart-header { display: none; }
        
        .cart-title { margin: 0; }
        
        .cart-item {
            display: grid;
            grid-template-columns: 120px 1fr 120px auto;
            align-items: center;
            gap: 20px;
            padding: 25px 30px;
            border-bottom: 1px solid #ecf0f1;
            transition: all 0.3s ease;
        }
        
        .cart-item:hover {
            background: #f8f9fa;
        }
        
        .cart-item:last-child {
            border-bottom: none;
        }
        
        .course-image {
            width: 120px;
            height: 80px;
            border-radius: 10px;
            overflow: hidden;
        }
        
        .course-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        
        .course-info {
            min-width: 0;
        }
        
        .course-title {
            font-size: 1.2rem;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 8px;
        }
        
        .course-description {
            color: #7f8c8d;
            font-size: 0.9rem;
            line-height: 1.4;
        }
        
        .course-price {
            font-size: 1.3rem;
            font-weight: 600;
            color: #e74c3c;
            text-align: right;
            white-space: nowrap;
        }
        
        .remove-btn {
            background: #e74c3c;
            color: white;
            border: none;
            border-radius: 25px;
            padding: 8px 15px;
            font-size: 0.9rem;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-left: 20px;
        }
        
        .remove-btn:hover {
            background: #c0392b;
            transform: translateY(-2px);
        }
        
        .cart-summary {
            background: #f8f9fa;
            padding: 30px;
            border-top: 1px solid #ecf0f1;
        }
        
        .summary-row {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        
        .summary-label {
            font-weight: 600;
            color: #2c3e50;
        }
        
        .summary-value {
            font-weight: 600;
            color: #e74c3c;
        }
        
        .total-row {
            border-top: 2px solid #ecf0f1;
            padding-top: 15px;
            margin-top: 15px;
        }
        
        .total-label {
            font-size: 1.2rem;
            font-weight: 700;
        }
        
        .total-value {
            font-size: 1.5rem;
            font-weight: 700;
            color: #e74c3c;
        }
        
        .cart-actions {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: space-between;
            gap: 16px;
            margin-top: 25px;
        }

        @media (max-width: 992px) {
            .cart-item {
                grid-template-columns: 100px 1fr 100px auto;
                gap: 16px;
                padding: 20px;
            }
        }

        @media (max-width: 768px) {
            .cart-item {
                grid-template-columns: 1fr;
                text-align: left;
            }
            .course-image {
                width: 100%;
                height: 160px;
            }
            .course-price {
                text-align: left;
            }
            .cart-actions {
                justify-content: center;
                flex-direction: column;
            }
        }
        
        .btn-continue {
            padding: 15px 30px;
            background: #95a5a6;
            color: white;
            border: none;
            border-radius: 25px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        
        .btn-continue:hover {
            background: #7f8c8d;
            color: white;
            text-decoration: none;
        }
        
        .btn-checkout {
            padding: 15px 30px;
            background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
            color: white;
            border: none;
            border-radius: 25px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        
        .btn-checkout:hover {
            transform: translateY(-2px);
            box-shadow: none;
            color: white;
            text-decoration: none;
        }
        
        .empty-cart {
            text-align: center;
            padding: 80px 30px;
        }
        
        .empty-cart-icon {
            font-size: 5rem;
            color: #bdc3c7;
            margin-bottom: 20px;
        }
        
        .empty-cart-title {
            font-size: 2rem;
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 15px;
        }
        
        .empty-cart-text {
            color: #7f8c8d;
            margin-bottom: 30px;
        }
        
        .btn-shop {
            padding: 15px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 25px;
            font-weight: 600;
            text-decoration: none;
            transition: all 0.3s ease;
        }
        
        .btn-shop:hover {
            transform: translateY(-2px);
            box-shadow: none;
            color: white;
            text-decoration: none;
        }
        .combined-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); position: relative; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.1); }
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
                                        <li class="active"><a href="cart">Cart</a></li>
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
        <div class="page-header-content">
            <div class="container">
                <h1 class="cart-title" style="margin:0;">Shopping Cart</h1>
            </div>
        </div>
    </div>
    <!-- Combined Header End -->

    <main>
        <!-- Cart Section -->
        <section class="cart-section">
            <div class="container">
                <div class="cart-container">
                    
                    <c:choose>
                        <c:when test="${empty cartItems}">
                            <div class="empty-cart" style="width:100%;">
                                <div class="empty-cart-icon">
                                    <i class="fas fa-shopping-cart"></i>
                                </div>
                                <h2 class="empty-cart-title">Your cart is empty</h2>
                                <p class="empty-cart-text">Looks like you haven't added any courses to your cart yet.</p>
                                <a href="courses" class="btn-shop">Start Shopping</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- Cart Items -->
                            <c:forEach items="${cartItems}" var="cartItem">
                                <div class="cart-item">
                                    <div class="course-image">
                                        <img src="${cartItem.courseThumbnail}" alt="${cartItem.courseTitle}">
                                    </div>
                                    <div class="course-info">
                                        <h3 class="course-title">${cartItem.courseTitle}</h3>
                                        <p class="course-description">${cartItem.courseDescription}</p>
                                    </div>
                                    <div class="course-price">$${cartItem.price}</div>
                                    <button class="remove-btn" onclick="removeFromCart(<c:out value='${cartItem.cart_item_id}'/>)">
                                        <i class="fas fa-trash"></i> Remove
                                    </button>
                                </div>
                            </c:forEach>
                            
                            <!-- Cart Summary -->
                            <div class="cart-summary" style="width:100%;">
                                <div class="summary-row total-row">
                                    <span class="summary-label total-label">Total:</span>
                                    <span class="summary-value total-value">$${cartTotal}</span>
                                </div>
                                
                                <div class="cart-actions">
                                    <a href="courses" class="btn-continue">Continue Shopping</a>
                                    <a href="checkout" class="btn-checkout">Proceed to Checkout</a>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </section>
    </main>

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
    // Remove from cart functionality
    function removeFromCart(cartItemId) {
        if (confirm('Are you sure you want to remove this item from your cart?')) {
            fetch('remove-from-cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'cartItemId=' + cartItemId
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('Failed to remove item from cart');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error removing item from cart');
            });
        }
    }
</script>

</body>
</html>
