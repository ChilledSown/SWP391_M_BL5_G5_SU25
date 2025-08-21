/*
 * Customer lesson viewer servlet
 */
package controller;

import dal.LessonDAO;
import dal.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Lesson;
import model.Quiz;

@WebServlet(name = "CustomerViewLessonServlet", urlPatterns = {"/customer-view-lesson"})
public class CustomerViewLessonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String courseIdRaw = request.getParameter("courseId");
            String lessonIdRaw = request.getParameter("lessonId");

            Long courseId = null;
            Long lessonId = null;

            // If courseId is present, parse it
            if (courseIdRaw != null && !courseIdRaw.isEmpty()) {
                courseId = Long.parseLong(courseIdRaw);
            }
            // If lessonId is present, parse it
            if (lessonIdRaw != null && !lessonIdRaw.isEmpty()) {
                lessonId = Long.parseLong(lessonIdRaw);
            }

            LessonDAO lessonDAO = new LessonDAO();

            // Derive courseId from lessonId if needed
            if (courseId == null && lessonId != null) {
                Lesson lesson = lessonDAO.getLessonById(lessonId);
                if (lesson != null) {
                    courseId = lesson.getCourseId();
                }
            }

            if (courseId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing courseId");
                return;
            }

            System.out.println("CustomerViewLessonServlet: Loading lessons for courseId = " + courseId);

            List<Lesson> lessons = lessonDAO.getLessonsByCourseId(courseId);
            System.out.println("CustomerViewLessonServlet: Found " + (lessons != null ? lessons.size() : 0) + " lessons");

            // Determine active lesson
            Lesson activeLesson = null;

            if (lessons == null || lessons.isEmpty()) {
                // No lessons found for this course
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessons", lessons);
                request.setAttribute("activeLesson", null);
                request.setAttribute("quizzes", null);
                request.getRequestDispatcher("customer-view-lesson.jsp").forward(request, response);
                return;
            }

            // Try to select by provided lessonId first
            if (lessonId != null) {
                for (Lesson l : lessons) {
                    if (l.getLessonId().equals(lessonId)) {
                        activeLesson = l;
                        break;
                    }
                }
            }

            // If no specific lesson found, use the first one
            if (activeLesson == null) {
                activeLesson = lessons.get(0);
            }

            // Load quizzes of the active lesson (used for the sidebar quiz entry)
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> quizzes = quizDAO.getQuizzesByLessonId(activeLesson.getLessonId());

            request.setAttribute("courseId", courseId);
            request.setAttribute("lessons", lessons);
            request.setAttribute("activeLesson", activeLesson);
            request.setAttribute("quizzes", quizzes);

            request.getRequestDispatcher("customer-view-lesson.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters");
        }
    }
}

