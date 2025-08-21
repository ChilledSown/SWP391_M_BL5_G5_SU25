package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AdminListDAO;
import model.Topic;
import model.Course;
import java.util.List;

@WebServlet(name="AdminCourseServlet", urlPatterns={"/admincourse"})
public class AdminCourseServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminListDAO dao = new AdminListDAO();

        String action = request.getParameter("action");
        String topicIdStr = request.getParameter("topicId");
        Long topicId = null;
        try {
            topicId = Long.parseLong(topicIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid topic ID");
            request.getRequestDispatcher("admintopic.jsp").forward(request, response);
            return;
        }

        Topic selectedTopic = dao.getTopicById(topicId);
        if (selectedTopic == null) {
            request.setAttribute("error", "Topic not found");
            request.getRequestDispatcher("admintopic.jsp").forward(request, response);
            return;
        }

        if ("view".equals(action)) {
            try {
                long courseId = Long.parseLong(request.getParameter("courseId"));
                Course course = dao.getCourseById(courseId);
                if (course == null) {
                    request.setAttribute("error", "Course not found");
                    request.getRequestDispatcher("admincourse.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("course", course);
                request.setAttribute("topicId", topicId);
                request.getRequestDispatcher("admincoursedetail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid course ID");
                request.getRequestDispatcher("admincourse.jsp").forward(request, response);
            }
            return;
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // Default to page 1
            }
        }

        String searchQuery = request.getParameter("query");
        List<Course> courses = dao.getCoursesWithPagination(searchQuery, page, PAGE_SIZE, topicId);
        int totalCourses = dao.getTotalCourses(searchQuery, topicId);
        int totalPages = (int) Math.ceil((double) totalCourses / PAGE_SIZE);

        request.setAttribute("selectedTopic", selectedTopic);
        request.setAttribute("courses", courses);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("admincourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Delegate to doGet for simplicity
    }
}