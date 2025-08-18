<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="zxx">
<head>
  <meta charset="utf-8">
  <meta http-equiv="x-ua-compatible" content="ie=edge">
  <title>My Purchased Courses | Education</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="assets/css/bootstrap.min.css">
  <link rel="stylesheet" href="assets/css/style.css">
  <style>
    .course-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 20px; }
    .card { border: none; border-radius: 12px; box-shadow: 0 8px 24px rgba(0,0,0,.08); overflow: hidden; }
    .card img { height: 160px; object-fit: cover; }
    .card-body { padding: 16px; }
    .card-title { font-size: 16px; font-weight: 700; color: #2c3e50; min-height: 40px; }
    .price { color: #667eea; font-weight: 700; }
    .actions { margin-top: 24px; text-align: center; }
  </style>
</head>
<body>
  <div class="container py-5">
    <h2 class="mb-4">My Purchased Courses</h2>
    <c:choose>
      <c:when test="${empty courses}">
        <div class="alert alert-info">You have not purchased any course yet.</div>
      </c:when>
      <c:otherwise>
        <div class="course-grid">
          <c:forEach items="${courses}" var="c">
            <div class="card">
              <img src="${empty c.thumbnail_url ? 'assets/img/gallery/featured1.png' : c.thumbnail_url}" class="card-img-top" alt="${c.title}">
              <div class="card-body">
                <h5 class="card-title">${c.title}</h5>
                <div class="price">$${c.price}</div>
                <a href="customer-course-detail?id=${c.course_id}" class="btn btn-sm btn-outline-primary mt-2">View course</a>
              </div>
            </div>
          </c:forEach>
        </div>
      </c:otherwise>
    </c:choose>
    <div class="actions">
      <a href="home" class="btn btn-secondary">Back to Home</a>
    </div>
  </div>

  <script src="assets/js/vendor/jquery-1.12.4.min.js"></script>
  <script src="assets/js/bootstrap.min.js"></script>
</body>
</html>


