package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AdminListDAO;
import model.Course;
import model.Lesson;
import java.util.List;

@WebServlet(name="AdminLessonServlet", urlPatterns={"/adminlesson"})
public class AdminLessonServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminListDAO dao = new AdminListDAO();

        String action = request.getParameter("action");
        String courseIdStr = request.getParameter("courseId");
        Long courseId = null;
        try {
            courseId = Long.parseLong(courseIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid course ID");
            request.getRequestDispatcher("admincourse.jsp").forward(request, response);
            return;
        }

        Course selectedCourse = dao.getCourseById(courseId);
        if (selectedCourse == null) {
            request.setAttribute("error", "Course not found");
            request.getRequestDispatcher("admincourse.jsp").forward(request, response);
            return;
        }

        String topicIdStr = request.getParameter("topicId");
        Long topicId = null;
        try {
            topicId = Long.parseLong(topicIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid topic ID");
            request.getRequestDispatcher("admincourse.jsp").forward(request, response);
            return;
        }

        if ("view".equals(action)) {
            try {
                long lessonId = Long.parseLong(request.getParameter("lessonId"));
                Lesson lesson = dao.getLessonById(lessonId);
                if (lesson == null) {
                    request.setAttribute("error", "Lesson not found");
                    request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("lesson", lesson);
                request.setAttribute("courseId", courseId);
                request.setAttribute("topicId", topicId);
                request.getRequestDispatcher("adminlessondetail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid lesson ID");
                request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
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
        List<Lesson> lessons = dao.getLessonsWithPagination(searchQuery, page, PAGE_SIZE, courseId);
        int totalLessons = dao.getTotalLessons(searchQuery, courseId);
        int totalPages = (int) Math.ceil((double) totalLessons / PAGE_SIZE);

        request.setAttribute("selectedCourse", selectedCourse);
        request.setAttribute("lessons", lessons);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("topicId", topicId);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Delegate to doGet for simplicity
    }
}