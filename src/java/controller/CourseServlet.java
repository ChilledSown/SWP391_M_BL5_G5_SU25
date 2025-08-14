package controller;

import dal.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Course;

@WebServlet(name="CourseServlet", urlPatterns={"/courses"})
public class CourseServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            CourseDAO courseDAO = new CourseDAO();
            
            // Lấy các tham số filter từ request
            String searchTerm = request.getParameter("search");
            String priceFilter = request.getParameter("price");
            String ratingFilter = request.getParameter("rating");
            String sortBy = request.getParameter("sort");
            String topicFilter = request.getParameter("topic");
            
            List<Course> courses;
            
            // Kiểm tra xem có filter nào được áp dụng không
            if (hasActiveFilters(searchTerm, priceFilter, ratingFilter, sortBy, topicFilter)) {
                // Sử dụng phương thức filter
                courses = courseDAO.getFilteredCourses(searchTerm, priceFilter, ratingFilter, sortBy, topicFilter);
            } else {
                // Lấy tất cả khóa học nếu không có filter
                courses = courseDAO.getAllCourse();
            }
            
            // Đặt danh sách khóa học vào request attribute
            request.setAttribute("allCourses", courses);
            
            // Đặt lại các tham số filter để giữ nguyên trạng thái UI
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("priceFilter", priceFilter);
            request.setAttribute("ratingFilter", ratingFilter);
            request.setAttribute("sortBy", sortBy != null ? sortBy : "newest");
            request.setAttribute("topicFilter", topicFilter);
            
            // Forward đến trang courses.jsp
            request.getRequestDispatcher("courses.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi nếu có
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách khóa học");
            request.getRequestDispatcher("courses.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }
    
    // Kiểm tra xem có filter nào được áp dụng không
    private boolean hasActiveFilters(String searchTerm, String priceFilter, String ratingFilter, String sortBy, String topicFilter) {
        return (searchTerm != null && !searchTerm.trim().isEmpty()) ||
               (priceFilter != null && !priceFilter.trim().isEmpty()) ||
               (ratingFilter != null && !ratingFilter.trim().isEmpty()) ||
               (sortBy != null && !sortBy.trim().isEmpty() && !sortBy.equals("newest")) ||
               (topicFilter != null && !topicFilter.trim().isEmpty());
    }
}
