package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.CourseDAO;
import dal.LessonDAO;
import dal.TopicDAO;
import model.Course;
import model.Lesson;
import model.Topic;
import java.util.List;

@WebServlet(name = "ManageCourseServlet", urlPatterns = {"/managecourse"})
public class ManageCourseServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDAO cdao = new CourseDAO();
        TopicDAO tdao = new TopicDAO();

        String action = request.getParameter("action");
        String topicIdStr = request.getParameter("topicId");

        // Handle the "details" action
        if ("details".equals(action)) {
            try {
                long courseId = Long.parseLong(request.getParameter("courseId"));
                Course course = cdao.getCourseById(courseId);
                if (course == null) {
                    request.setAttribute("error", "Course not found");
                    request.setAttribute("topicId", topicIdStr);
                    request.getRequestDispatcher("managecourse.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("course", course);
                request.setAttribute("topicId", topicIdStr);
                request.getRequestDispatcher("courseDetails.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid course ID");
                request.setAttribute("topicId", topicIdStr);
                request.getRequestDispatcher("managecourse.jsp").forward(request, response);
            }
            return;
        }

        // Handle the "view" action (for lessons)
        if ("view".equals(action)) {
            try {
                long courseId = Long.parseLong(request.getParameter("courseId"));
                Course course = cdao.getCourseById(courseId);
                if (course == null) {
                    request.setAttribute("error", "Course not found");
                    request.setAttribute("topicId", topicIdStr);
                    request.getRequestDispatcher("managecourse.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("course", course);
                Topic topic = tdao.getTopicById(course.getTopic_id());
                request.setAttribute("topic", topic);
                request.setAttribute("topicId", topic != null ? topic.getTopic_id() : null);
                LessonDAO ldao = new LessonDAO();
                List<Lesson> lessons = ldao.getLessonsByCourseId(courseId);
                request.setAttribute("lessons", lessons);
                request.getRequestDispatcher("manageLesson.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid course ID");
                request.setAttribute("topicId", topicIdStr);
                request.getRequestDispatcher("managecourse.jsp").forward(request, response);
            }
            return;
        }

        // Handle course listing with pagination and search
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

        String searchQuery = request.getParameter("query");
        List<Course> courses;
        int totalCourses;
        if (topicIdStr != null && !topicIdStr.trim().isEmpty()) {
            try {
                long topicId = Long.parseLong(topicIdStr);
                courses = cdao.getCoursesByTopicIdWithSearch(searchQuery, topicId, (page - 1) * PAGE_SIZE, PAGE_SIZE);
                totalCourses = cdao.getTotalCoursesByTopicIdWithSearch(searchQuery, topicId);
                int totalPages = (int) Math.ceil((double) totalCourses / PAGE_SIZE);
                if (totalPages == 0) totalPages = 1;
                if (page > totalPages) {
                    page = totalPages;
                    courses = cdao.getCoursesByTopicIdWithSearch(searchQuery, topicId, (page - 1) * PAGE_SIZE, PAGE_SIZE);
                }
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                Topic topic = tdao.getTopicById(topicId);
                request.setAttribute("topic", topic);
                request.setAttribute("topicId", topicId);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                request.setAttribute("courses", List.of());
                request.setAttribute("currentPage", 1);
                request.setAttribute("totalPages", 1);
                request.setAttribute("searchQuery", searchQuery);
                request.getRequestDispatcher("managecourse.jsp").forward(request, response);
                return;
            }
        } else {
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                courses = cdao.getFilteredCoursesPaged(searchQuery, null, null, null, null, (page - 1) * PAGE_SIZE, PAGE_SIZE);
                totalCourses = cdao.countFilteredCourses(searchQuery, null, null, null);
            } else {
                courses = cdao.getAllCoursePaged((page - 1) * PAGE_SIZE, PAGE_SIZE);
                totalCourses = cdao.countAllCourses();
            }
            int totalPages = (int) Math.ceil((double) totalCourses / PAGE_SIZE);
            if (totalPages == 0) totalPages = 1;
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
        }

        request.setAttribute("courses", courses);
        request.setAttribute("searchQuery", searchQuery);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("managecourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Manages course-related operations for the admin dashboard";
    }
}