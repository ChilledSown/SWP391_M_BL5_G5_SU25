# Customer Course Detail Page - Hướng dẫn sử dụng

## Tổng quan
Đã tạo thành công trang `customer-course-detail.jsp` với đầy đủ các tính năng theo yêu cầu:

### 1. Các file đã tạo:

#### DAO Classes:
- `ReviewDAO.java` - Xử lý logic review (CRUD operations)
- `CartDAO.java` - Xử lý logic cart (thêm, xóa, hiển thị cart)

#### Servlet Classes:
- `CustomerCourseDetailServlet.java` - Servlet chính để hiển thị trang chi tiết khóa học
- `AddToCartServlet.java` - Xử lý thêm khóa học vào cart
- `SubmitReviewServlet.java` - Xử lý submit review
- `DeleteReviewServlet.java` - Xử lý xóa review
- `UpdateReviewServlet.java` - Xử lý cập nhật review
- `RemoveFromCartServlet.java` - Xử lý xóa khóa học khỏi cart
- `CartServlet.java` - Hiển thị trang cart

#### Model Classes:
- `CartItemWithCourse.java` - Wrapper class để chứa thông tin cart item và course

#### JSP Pages:
- `customer-course-detail.jsp` - Trang chi tiết khóa học cho customer
- `cart.jsp` - Trang hiển thị cart

### 2. Các tính năng đã implement:

#### Trang Customer Course Detail:
- **Header/Footer**: Sử dụng nguyên header và footer từ `index.jsp`
- **Course Information**: Hiển thị thông tin khóa học (title, price, description, rating)
- **Course Image**: Hiển thị hình ảnh khóa học
- **Action Buttons**: 
  - "Purchase now" - Nút mua ngay
  - "Add to cart" - Nút thêm vào giỏ hàng (chỉ hiển thị nếu chưa có trong cart)
- **What You Will Learn**: Hiển thị danh sách các bài học
- **Reviews Section**: 
  - Hiển thị tất cả reviews của khóa học
  - Chỉ review của user hiện tại mới có nút "..." (settings)
  - Khi click vào "..." sẽ hiển thị dropdown với options "Update" và "Delete"
  - Form thêm review mới (chỉ hiển thị nếu user chưa review)

#### Review Management:
- **Submit Review**: User có thể thêm review mới với rating và comment
- **Update Review**: Modal popup để cập nhật rating và comment
- **Delete Review**: Xóa review ngay lập tức
- **Star Rating**: Hệ thống đánh giá bằng sao (1-5 sao)

#### Cart Management:
- **Add to Cart**: Thêm khóa học vào cart
- **Cart Page**: Hiển thị danh sách khóa học trong cart
- **Remove from Cart**: Xóa khóa học khỏi cart
- **Cart Total**: Tính tổng tiền cart

### 3. Cách sử dụng:

#### Truy cập trang chi tiết khóa học:
```
http://localhost:8080/your-app/customer-course-detail?id=1
```
Trong đó `id=1` là ID của khóa học

#### Truy cập trang cart:
```
http://localhost:8080/your-app/cart
```

### 4. Database Requirements:

Đảm bảo có các bảng sau trong database:
- `Course` - Bảng khóa học
- `Lesson` - Bảng bài học
- `Review` - Bảng đánh giá
- `Cart` - Bảng giỏ hàng
- `CartItem` - Bảng item trong giỏ hàng
- `User` - Bảng người dùng

### 5. Lưu ý:

- Các servlet sử dụng Jakarta EE (không phải javax)
- Cần đăng nhập để sử dụng các tính năng review và cart
- Tất cả các chức năng đều có validation và error handling
- UI được thiết kế responsive và modern
- Sử dụng JSTL để xử lý logic trong JSP

### 6. Các tính năng bổ sung có thể thêm:

- Star rating cho form thêm review mới
- Wishlist functionality
- Course preview/video
- Related courses
- Course enrollment
- Payment integration

### 7. Troubleshooting:

Nếu gặp lỗi import Jakarta, hãy đảm bảo:
- Project sử dụng Jakarta EE 9+ 
- Có đầy đủ dependencies trong pom.xml hoặc build.gradle
- Server hỗ trợ Jakarta EE

Nếu gặp lỗi database, hãy kiểm tra:
- Connection string trong DBContext
- Cấu trúc bảng database
- Quyền truy cập database

