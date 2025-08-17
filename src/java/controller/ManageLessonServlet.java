package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.CourseDAO;
import dal.LessonDAO;
import dal.QuizDAO;
import dal.TopicDAO;
import model.Course;
import model.Lesson;
import model.Quiz;
import model.Topic;
import java.util.List;

@WebServlet(name = "ManageLessonServlet", urlPatterns = {"/managelesson"})
public class ManageLessonServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LessonDAO ldao = new LessonDAO();
        CourseDAO cdao = new CourseDAO();
        TopicDAO tdao = new TopicDAO();

        String action = request.getParameter("action");
        if ("view".equals(action)) {
            try {
                long lessonId = Long.parseLong(request.getParameter("lessonId"));
                Lesson lesson = ldao.getLessonById(lessonId);
                if (lesson == null) {
                    request.setAttribute("error", "Lesson not found");
                    request.getRequestDispatcher("managelesson.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("lesson", lesson);
                // Get course and topic for context
                Course course = cdao.getCourseById(lesson.getCourseId());
                request.setAttribute("course", course);
                if (course != null) {
                    Topic topic = tdao.getTopicById(course.getTopic_id());
                    request.setAttribute("topic", topic);
                    request.setAttribute("topicId", topic != null ? topic.getTopic_id() : null);
                    request.setAttribute("courseId", course.getCourse_id());
                }
                // Get quizzes for the lesson
                QuizDAO qdao = new QuizDAO();
                List<Quiz> quizzes = qdao.getQuizzesByLessonId(lessonId);
                int totalQuizzes = quizzes.size();
                int totalPages = (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);
                // Manual pagination to ensure consistency with managequiz.jsp
                int page = 1;
                String pageStr = request.getParameter("page");
                if (pageStr != null) {
                    try {
                        page = Integer.parseInt(pageStr);
                    } catch (NumberFormatException e) {
                        // Default to page 1
                    }
                }
                int start = (page - 1) * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, quizzes.size());
                if (start < quizzes.size()) {
                    quizzes = quizzes.subList(start, end);
                } else {
                    quizzes = List.of();
                    if (totalQuizzes > 0 && page > 1) {
                        request.setAttribute("error", "Page out of range for lesson ID: " + lessonId);
                        page = 1;
                        quizzes = qdao.getQuizzesByLessonId(lessonId).subList(0, Math.min(PAGE_SIZE, totalQuizzes));
                    }
                }
                request.setAttribute("quizzes", quizzes);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("managequiz.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid lesson ID");
                request.getRequestDispatcher("managelesson.jsp").forward(request, response);
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

        String courseIdStr = request.getParameter("courseId");
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

            // Get lessons for the course
            List<Lesson> lessons = ldao.getLessonsByCourseId(courseId);
            int totalLessons = lessons.size();
            int totalPages = (int) Math.ceil((double) totalLessons / PAGE_SIZE);

            // Manual pagination
            int start = (page - 1) * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, lessons.size());
            List<Lesson> pagedLessons = lessons.subList(start, end);

            request.setAttribute("lessons", pagedLessons);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            String message = (String) request.getSession().getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                request.getSession().removeAttribute("message");
            }
            request.getRequestDispatcher("managelesson.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid course ID");
            request.getRequestDispatcher("managecourse.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}