package controller;

import dal.CourseDAO;
import dal.TopicDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import model.Course;
import model.Topic;

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
            TopicDAO topicDAO = new TopicDAO();
            
            // Lấy các tham số filter từ request
            String searchTerm = request.getParameter("search");
            String priceFilter = request.getParameter("price");
            String ratingFilter = request.getParameter("rating");
            String sortBy = request.getParameter("sort");
            String topicFilter = request.getParameter("topic");
            
            // Pagination params
            int page = 1;
            int size = 6;
            try {
                if (request.getParameter("page") != null) page = Integer.parseInt(request.getParameter("page"));
                if (request.getParameter("size") != null) size = Integer.parseInt(request.getParameter("size"));
            } catch (NumberFormatException ignored) {}
            if (page < 1) page = 1;
            if (size < 1) size = 6;
            int offset = (page - 1) * size;

            // Lấy danh sách khóa học (phân trang)
            List<Course> courses;
            int totalCount;
            if (hasActiveFilters(searchTerm, priceFilter, ratingFilter, sortBy, topicFilter)) {
                courses = courseDAO.getFilteredCoursesPaged(searchTerm, priceFilter, ratingFilter, sortBy, topicFilter, offset, size);
                totalCount = courseDAO.countFilteredCourses(searchTerm, priceFilter, ratingFilter, topicFilter);
            } else {
                courses = courseDAO.getAllCoursePaged(offset, size);
                totalCount = courseDAO.countAllCourses();
            }
            
            // Lấy danh sách topics
            List<Topic> allTopics = topicDAO.getAllTopics();
            
            // Lấy topic cho từng course
            Map<Long, Topic> courseTopicsMap = new HashMap<>();
            for (Course course : courses) {
                Topic courseTopic = topicDAO.getTopicByCourseId(course.getCourse_id());
                courseTopicsMap.put(course.getCourse_id(), courseTopic);
            }
            
            // Đặt dữ liệu vào request
            request.setAttribute("allCourses", courses);
            request.setAttribute("topics", allTopics);
            request.setAttribute("courseTopicsMap", courseTopicsMap); // Thêm map vào request
            request.setAttribute("totalResults", totalCount);
            request.setAttribute("page", page);
            request.setAttribute("size", size);
            request.setAttribute("hasMore", (page * size) < totalCount);
            
            // Đặt lại các tham số filter để giữ nguyên trạng thái UI
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("priceFilter", priceFilter);
            request.setAttribute("ratingFilter", ratingFilter);
            request.setAttribute("sortBy", sortBy != null ? sortBy : "newest");
            request.setAttribute("topicFilter", topicFilter);
            
            // Nếu là request AJAX để load thêm, trả về fragment HTML
            String ajax = request.getParameter("ajax");
            if ("1".equals(ajax)) {
                // For fragment rendering, provide the list via 'pageCourses'
                request.setAttribute("pageCourses", courses);
                request.getRequestDispatcher("/partials/course_cards.jsp").forward(request, response);
            } else {
                // Forward đến trang courses.jsp
                request.getRequestDispatcher("courses.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
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
