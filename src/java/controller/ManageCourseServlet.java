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
        if ("view".equals(action)) {
            try {
                long courseId = Long.parseLong(request.getParameter("courseId"));
                Course course = cdao.getCourseById(courseId);
                if (course == null) {
                    request.setAttribute("error", "Course not found");
                    request.getRequestDispatcher("managecourse.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("course", course);
                // Get the topic for context (to display topic name or navigate back)
                Topic topic = tdao.getTopicById(course.getTopic_id());
                request.setAttribute("topic", topic);
                // Get lessons for the course
                LessonDAO ldao = new LessonDAO();
                List<Lesson> lessons = ldao.getLessonsByCourseId(courseId);
                request.setAttribute("lessons", lessons);
                request.getRequestDispatcher("managelesson.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid course ID");
                request.getRequestDispatcher("managecourse.jsp").forward(request, response);
            }
            return;
        }

        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                // Default to page 1 if invalid
            }
        }

        String searchQuery = request.getParameter("query");
        String topicIdStr = request.getParameter("topicId");
        List<Course> courses;
        int totalCourses;
        
        if (topicIdStr != null && !topicIdStr.trim().isEmpty()) {
            try {
                long topicId = Long.parseLong(topicIdStr);
                courses = cdao.getCoursesByTopicId(topicId);
                totalCourses = courses.size(); // Since getCoursesByTopicId doesn't support pagination, count the list size
                Topic topic = tdao.getTopicById(topicId);
                request.setAttribute("topic", topic);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid topic ID");
                courses = cdao.getAllCoursePaged((page - 1) * PAGE_SIZE, PAGE_SIZE);
                totalCourses = cdao.countAllCourses();
            }
        } else if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            courses = cdao.getFilteredCoursesPaged(searchQuery, null, null, null, null, (page - 1) * PAGE_SIZE, PAGE_SIZE);
            totalCourses = cdao.countFilteredCourses(searchQuery, null, null, null);
        } else {
            courses = cdao.getAllCoursePaged((page - 1) * PAGE_SIZE, PAGE_SIZE);
            totalCourses = cdao.countAllCourses();
        }

        int totalPages = (int) Math.ceil((double) totalCourses / PAGE_SIZE);

        request.setAttribute("courses", courses);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
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
        // Not implementing POST as per the requirement (only data retrieval and view)
        doGet(request, response);
    }
}