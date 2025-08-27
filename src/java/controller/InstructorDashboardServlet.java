package controller;

import dal.BalanceDAO;
import dal.BlogDAO;
import dal.CourseDAO;
import dal.LessonDAO;
import dal.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashBoard"})
public class InstructorDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        model.User user = (model.User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"instructor".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        long createdBy = user.getUser_id();

        // Khởi tạo các DAO
        CourseDAO courseDAO = new CourseDAO();
        BlogDAO blogDAO = new BlogDAO();
        LessonDAO lessonDAO = new LessonDAO();
        QuizDAO quizDAO = new QuizDAO();
        BalanceDAO balanceDAO = new BalanceDAO();

        // Lấy dữ liệu từ DAO
        int totalCourses = courseDAO.getTotalCoursesByCreator((int) createdBy); // Tổng số khóa học
        int totalBlogs = blogDAO.getAllBlogs().size(); // Tổng số blog (cần lọc theo createdBy nếu cần)
        int totalStudents = 0; // Số sinh viên duy nhất từ đơn hàng
        int totalOrders = balanceDAO.getTransactionCount((int) createdBy, "completed", null, null, null); // Tổng số đơn hàng
        double averageRating = 0.0; // Đánh giá trung bình
        double revenueThisMonth = 0.0; // Doanh thu trong tháng
        int newStudentsThisMonth = 0; // Sinh viên mới trong tháng
        int newReviewsThisMonth = 0; // Đánh giá mới trong tháng

        // Tính tổng số bài học và bài kiểm tra
        List<model.Course> courses = courseDAO.getCoursesByCreator((int) createdBy);
        int totalLessons = 0;
        int totalQuizzes = 0;
        if (courses != null) {
            for (model.Course course : courses) {
                if (course != null) {
                    totalLessons += lessonDAO.getLessonsByCourseId(course.getCourse_id()).size();
                    totalQuizzes += quizDAO.countQuizzesByCourseId(course.getCourse_id());
                }
            }
        }

        // Tính tổng số sinh viên (dựa trên user_id duy nhất từ Order)
        List<Integer> uniqueUserIds = new ArrayList<>();
        List<model.BalanceDTOSeller> transactions = balanceDAO.getTransactions((int) createdBy, "completed", null, null, null, 1, Integer.MAX_VALUE);
        if (transactions != null) {
            for (model.BalanceDTOSeller transaction : transactions) {
                if (transaction != null && !uniqueUserIds.contains((int) transaction.getOrderId())) {
                    uniqueUserIds.add((int) transaction.getOrderId());
                }
            }
        }
        totalStudents = uniqueUserIds.size();

        // Tính đánh giá trung bình từ các khóa học (xử lý null)
        if (courses != null && !courses.isEmpty()) {
            double sumRating = courses.stream()
                    .filter(c -> c != null && c.getAverageRating() != null)
                    .mapToDouble(model.Course::getAverageRating)
                    .average()
                    .orElse(0.0);
            averageRating = sumRating;
        }

        // Lọc doanh thu, sinh viên mới, và đánh giá mới trong tháng (08/2025)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startOfMonth = "2025-08-01"; // Bắt đầu tháng 08/2025
        String endOfMonth = sdf.format(new Date()); // Đến ngày hiện tại 10:49 PM +07, 25/08/2025
        if (transactions != null) {
            revenueThisMonth = transactions.stream()
                    .filter(t -> t != null && t.getOrderDate() != null)
                    .filter(t -> !t.getOrderDate().before(java.sql.Timestamp.valueOf(startOfMonth + " 00:00:00"))
                            && !t.getOrderDate().after(java.sql.Timestamp.valueOf(endOfMonth + " 23:59:59")))
                    .mapToDouble(model.BalanceDTOSeller::getAmount)
                    .sum();
        }
        newStudentsThisMonth = (int) (transactions != null ? transactions.stream()
                .filter(t -> t != null && t.getOrderDate() != null)
                .filter(t -> !t.getOrderDate().before(java.sql.Timestamp.valueOf(startOfMonth + " 00:00:00"))
                        && !t.getOrderDate().after(java.sql.Timestamp.valueOf(endOfMonth + " 23:59:59")))
                .map(t -> (int) t.getOrderId())
                .distinct()
                .count() : 0); // Giả định
        newReviewsThisMonth = 0; // Cần tích hợp ReviewDAO để tính chính xác

        // Danh sách hoạt động gần đây (giả định)
        List<String> recentActivities = new ArrayList<>();
        recentActivities.add("Logged in at " + new Date());
        // Có thể thêm logic lấy từ bảng log nếu có

        // Truyền dữ liệu vào request
        request.setAttribute("totalCourses", totalCourses);
        request.setAttribute("totalLessons", totalLessons);
        request.setAttribute("totalQuizzes", totalQuizzes);
        request.setAttribute("totalBlogs", totalBlogs);
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("revenueThisMonth", revenueThisMonth);
        request.setAttribute("newStudentsThisMonth", newStudentsThisMonth);
        request.setAttribute("newReviewsThisMonth", newReviewsThisMonth);
        request.setAttribute("recentActivities", recentActivities);
        request.setAttribute("user", user); // Truyền user để hiển thị tên

        // Chuyển hướng đến JSP
        request.getRequestDispatcher("/instructor_Doashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}