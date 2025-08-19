<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Payment Success | Education</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <style>
        .success-hero { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); min-height: 100vh; display: flex; align-items: center; color: #fff; position: relative; }
        .success-card { background: #fff; border-radius: 16px; box-shadow: 0 16px 40px rgba(0,0,0,0.12); padding: 36px; }
        .success-icon { width: 84px; height: 84px; border-radius: 50%; background: #28a745; display: flex; align-items: center; justify-content: center; color: #fff; font-size: 42px; margin: 0 auto 18px; box-shadow: 0 10px 24px rgba(40,167,69,.35); position: relative; animation: popIn .5s ease-out both; }
        .success-icon i { line-height: 1; animation: tickPop .6s .15s ease-out both; }
        .success-icon::after { content: ""; position: absolute; inset: -10px; border-radius: 50%; border: 3px solid rgba(40,167,69,.35); animation: ripple 1.1s .1s ease-out both; }
        @keyframes popIn { from { transform: scale(.7); opacity: .4; } to { transform: scale(1); opacity: 1; } }
        @keyframes tickPop { 0% { transform: scale(.6) rotate(-10deg); opacity: 0; } 60% { transform: scale(1.15) rotate(0); opacity: 1; } 100% { transform: scale(1); } }
        @keyframes ripple { 0% { transform: scale(.8); opacity: .7; } 100% { transform: scale(1.15); opacity: 0; } }
        .success-title { text-align: center; margin-bottom: 8px; color: #2c3e50; font-weight: 800; letter-spacing: .3px; }
        .success-sub { text-align: center; color: #6c757d; margin-bottom: 28px; }
        .actions { display: flex; gap: 12px; justify-content: center; margin-top: 16px; }
        .btn-primary { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border: none; padding: 10px 20px; border-radius: 10px; }
        .btn-outline { border: 2px solid #667eea; color: #667eea; background: transparent; padding: 10px 20px; border-radius: 10px; }
        .btn-outline:hover { background: rgba(102,126,234,.08); }
        @media (max-width: 768px) { }
    </style>
  </head>
  <body>
    <section class="success-hero">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-xl-8 col-lg-9">
            <div class="success-card">
              <div class="success-icon"><i class="fas fa-check"></i></div>
              <h2 class="success-title">Payment Successful</h2>
              <p class="success-sub">Thank you! Your payment has been processed successfully.</p>

              <!-- Details hidden as requested; keeping a brief note only -->
              <p class="text-center" style="color:#5f6b7a;">An order confirmation has been sent to your email. Please check your inbox.</p>

              <div class="actions">
                <a href="purchased-courses" class="btn btn-primary">Go to purchased courses</a>
                <a href="home" class="btn btn-outline">Back to Home</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <script src="assets/js/vendor/jquery-1.12.4.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script src="https://kit.fontawesome.com/a2e0e6ad65.js" crossorigin="anonymous"></script>
  </body>
 </html>


