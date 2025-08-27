package controller;

import dal.CourseDAO;
import dal.LessonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Lesson;
import model.User;

@WebServlet(name = "ManageLessonServlet", urlPatterns = {"/manageLessonInstructor"})
public class ManageLessonServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}

        String courseIdParam = request.getParameter("courseId");
        // Check if courseId is missing or empty
        if (courseIdParam == null || courseIdParam.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Course ID is missing or invalid.");
            request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
            return;
        }
        long courseId;
        try {
            courseId = Long.parseLong(courseIdParam);
            if (courseId <= 0) {
                request.setAttribute("errorMessage", "Course ID must be a positive number.");
                request.getRequestDispatcher("manageLessonSeller.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid Course ID format.");
            request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
            return;
        }
        String title = request.getParameter("title");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int page = 1;
        int pageSize = 2;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Math.max(1, Integer.parseInt(pageParam));
            } catch (NumberFormatException ignored) {
            }
        }
        int offset = (page - 1) * pageSize;
        LessonDAO dao = new LessonDAO();
        List<Lesson> lessons = dao.getFilteredLessonsByCoursePaged(courseId, title, startDate, endDate, offset, pageSize);
        int totalItems = dao.countFilteredLessonsByCourse(courseId, title, startDate, endDate);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);
        // Truyền attribute
        request.setAttribute("lessons", lessons);
        request.setAttribute("courseId", courseId);
        request.setAttribute("title", title);
        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        // Tạo baseUrl để phân trang giữ lại filter
        StringBuilder baseUrl = new StringBuilder("manageLessonInstructor?courseId=" + courseId);
        if (title != null && !title.isEmpty()) {
            baseUrl.append("&title=").append(java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8));
        }
        if (startDate != null && !startDate.isEmpty()) {
            baseUrl.append("&startDate=").append(startDate);
        }
        if (endDate != null && !endDate.isEmpty()) {
            baseUrl.append("&endDate=").append(endDate);
        }
        request.setAttribute("baseUrl", baseUrl.toString());
        request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
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