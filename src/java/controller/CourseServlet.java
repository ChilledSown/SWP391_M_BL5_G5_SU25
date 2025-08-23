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
            
            String searchTerm = request.getParameter("search");
            String priceFilter = request.getParameter("price");
            String ratingFilter = request.getParameter("rating");
            String sortBy = request.getParameter("sort");
            String topicFilter = request.getParameter("topic");
            
            // Pagination params
            int page = 1;
            int size = 6;
            try {
                if (request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
            } catch (NumberFormatException ignored) {}
            if (page < 1) page = 1;
            // Load More: luôn trả về danh sách tích lũy theo trang (6, 12, 18, ...)
            int offset = 0;
            int limit = page * size;

            // List of pagination courses
            List<Course> courses;
            int totalCount;
            if (hasActiveFilters(searchTerm, priceFilter, ratingFilter, sortBy, topicFilter)) {
                // Khi có filter: áp dụng phân trang kiểu Load More (cộng dồn)
                courses = courseDAO.getFilteredCoursesPaged(searchTerm, priceFilter, ratingFilter, sortBy, topicFilter, offset, limit);
                totalCount = courseDAO.countFilteredCourses(searchTerm, priceFilter, ratingFilter, topicFilter);
            } else {
                // Không filter: áp dụng phân trang kiểu Load More (cộng dồn)
                courses = courseDAO.getAllCoursePaged(offset, limit);
                totalCount = courseDAO.countAllCourses();
            }
            
            List<Topic> allTopics = topicDAO.getAllTopics();
            
            // Take topic for all specific course
            Map<Long, Topic> courseTopicsMap = new HashMap<>();
            for (Course course : courses) {
                Topic courseTopic = topicDAO.getTopicByCourseId(course.getCourse_id());
                courseTopicsMap.put(course.getCourse_id(), courseTopic);
            }
            
            request.setAttribute("allCourses", courses);
            request.setAttribute("topics", allTopics);
            request.setAttribute("courseTopicsMap", courseTopicsMap);
            request.setAttribute("totalResults", totalCount);
            request.setAttribute("page", page);
            request.setAttribute("size", size);
            request.setAttribute("hasMore", totalCount > (page * size));
            
            // Reset filter parameters
            request.setAttribute("searchTerm", searchTerm);
            request.setAttribute("priceFilter", priceFilter);
            request.setAttribute("ratingFilter", ratingFilter);
            request.setAttribute("sortBy", sortBy != null ? sortBy : "newest");
            request.setAttribute("topicFilter", topicFilter);
            
            request.getRequestDispatcher("courses.jsp").forward(request, response);
            
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
