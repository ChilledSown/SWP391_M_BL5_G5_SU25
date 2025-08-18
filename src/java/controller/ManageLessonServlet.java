package controller;

import dal.CourseDAO;
import dal.LessonDAO;
import dal.TopicDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Course;
import model.Lesson;
import model.Topic;

@WebServlet(name = "ManageLessonServlet", urlPatterns = {"/manageLesson"})
public class ManageLessonServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LessonDAO ldao = new LessonDAO();
        CourseDAO cdao = new CourseDAO();
        TopicDAO tdao = new TopicDAO();

        String action = request.getParameter("action");
        String courseIdStr = request.getParameter("courseId");
        String topicId = request.getParameter("topicId");
        String searchQuery = request.getParameter("query");
        String title = request.getParameter("title");
        String createdDate = request.getParameter("createdDate");

        // Handle the "details" action
        if ("details".equals(action)) {
            try {
                long lessonId = Long.parseLong(request.getParameter("lessonId"));
                Lesson lesson = ldao.getLessonById(lessonId);
                if (lesson == null) {
                    request.setAttribute("error", "Lesson not found");
                    request.setAttribute("topicId", topicId);
                    request.setAttribute("courseId", courseIdStr);
                    request.getRequestDispatcher("manageLesson.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("lesson", lesson);
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseIdStr);
                request.getRequestDispatcher("lessonDetails.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid lesson ID");
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseIdStr);
                request.getRequestDispatcher("manageLesson.jsp").forward(request, response);
            }
            return;
        }

        // Handle lesson listing with pagination and filtering
        int page = 1;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
        } catch (NumberFormatException e) {
            // Default to page 1 if invalid
        }

        if (courseIdStr == null || courseIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Course ID is required to view lessons");
            request.getRequestDispatcher("managecourse.jsp").forward(request, response);
            return;
        }

        try {
            long courseId = Long.parseLong(courseIdStr);
            Course course = cdao.getCourseById(courseId);
            if (course == null) {
                request.setAttribute("error", "Course not found");
                request.getRequestDispatcher("managecourse.jsp").forward(request, response);
                return;
            }
            request.setAttribute("course", course);
            Topic topic = tdao.getTopicById(course.getTopic_id());
            request.setAttribute("topic", topic);
            request.setAttribute("topicId", topic != null ? topic.getTopic_id() : null);
            request.setAttribute("courseId", courseId);

            // Combine filtering by title, createdDate, and search query
            List<Lesson> lessons;
            int totalItems;
            int totalPages;
            int offset = (page - 1) * PAGE_SIZE;
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                lessons = ldao.getLessonsByCourseIdWithSearch(searchQuery, courseId, offset, PAGE_SIZE);
                totalItems = ldao.getTotalLessonsByCourseIdWithSearch(searchQuery, courseId);
            } else {
                lessons = ldao.getFilteredLessonsByCoursePaged(courseId, title, createdDate, offset, PAGE_SIZE);
                totalItems = ldao.countFilteredLessonsByCourse(courseId, title, createdDate);
            }
            totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) {
                page = totalPages;
                offset = (page - 1) * PAGE_SIZE;
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    lessons = ldao.getLessonsByCourseIdWithSearch(searchQuery, courseId, offset, PAGE_SIZE);
                } else {
                    lessons = ldao.getFilteredLessonsByCoursePaged(courseId, title, createdDate, offset, PAGE_SIZE);
                }
            }

            request.setAttribute("lessons", lessons);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);
            request.setAttribute("title", title);
            request.setAttribute("createdDate", createdDate);

            // Create baseUrl for pagination
            StringBuilder baseUrl = new StringBuilder("manageLesson?courseId=" + courseId);
            if (topicId != null && !topicId.isEmpty()) {
                baseUrl.append("&topicId=").append(java.net.URLEncoder.encode(topicId, java.nio.charset.StandardCharsets.UTF_8));
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                baseUrl.append("&query=").append(java.net.URLEncoder.encode(searchQuery, java.nio.charset.StandardCharsets.UTF_8));
            }
            if (title != null && !title.isEmpty()) {
                baseUrl.append("&title=").append(java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8));
            }
            if (createdDate != null && !createdDate.isEmpty()) {
                baseUrl.append("&createdDate=").append(createdDate);
            }
            request.setAttribute("baseUrl", baseUrl.toString());

            String message = (String) request.getSession().getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                request.getSession().removeAttribute("message");
            }
            request.getRequestDispatcher("manageLesson.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid course ID");
            request.getRequestDispatcher("managecourse.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manages lesson-related operations for the admin dashboard";
    }
}