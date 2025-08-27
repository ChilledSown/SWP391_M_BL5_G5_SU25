package controller;

import dal.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;

@WebServlet(name = "ListCoursesInstructor", urlPatterns = {"/listCourses"})
public class ListCoursesInstructor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("instructor_courses.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        model.User currentUser = (model.User) session.getAttribute("user");
        if (currentUser == null || !"instructor".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = currentUser.getUser_id().intValue();
        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Math.max(1, Integer.parseInt(pageParam));
            } catch (NumberFormatException ignored) {
            }
        }
        int offset = (page - 1) * pageSize;

        // Lấy các tham số filter
        String title = request.getParameter("title");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String topicIdStr = request.getParameter("topicId");
        Long topicId = null;
        if (topicIdStr != null && !topicIdStr.isEmpty()) {
            try {
                topicId = Long.parseLong(topicIdStr);
            } catch (NumberFormatException e) {
                topicId = null;
            }
        }

        CourseDAO dao = new CourseDAO();
        int totalItems = dao.countFilteredCoursesByCreator(userId, title, startDate, endDate, topicId);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);
        List<Course> courses = dao.getFilteredCoursesByCreatorPaged(userId, title, startDate, endDate, topicId, offset, pageSize);

        // Truyền lại filter
        request.setAttribute("title", title);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("topicId", topicIdStr);

        // Build baseUrl
        StringBuilder qs = new StringBuilder();
        if (title != null && !title.isEmpty()) {
            qs.append("title=").append(java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8)).append("&");
        }
        if (startDate != null && !startDate.isEmpty()) {
            qs.append("startDate=").append(startDate).append("&");
        }
        if (endDate != null && !endDate.isEmpty()) {
            qs.append("endDate=").append(endDate).append("&");
        }
        if (topicIdStr != null && !topicIdStr.isEmpty()) {
            qs.append("topicId=").append(topicIdStr).append("&");
        }
        String baseUrl = "listCourses" + (qs.length() > 0 ? "?" + qs.substring(0, qs.length() - 1) : "");
        request.setAttribute("baseUrl", baseUrl);

        // Gửi sang JSP
        request.setAttribute("courses", courses);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("instructor_courses.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}