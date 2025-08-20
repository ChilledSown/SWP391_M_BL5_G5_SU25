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
            if (courseIdRaw == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing courseId");
                return;
            }

            long courseId = Long.parseLong(courseIdRaw);

            LessonDAO lessonDAO = new LessonDAO();
            List<Lesson> lessons = lessonDAO.getLessonsByCourseId(courseId);

            if (lessons == null || lessons.isEmpty()) {
                request.setAttribute("lessons", lessons);
                request.setAttribute("activeLesson", null);
                request.setAttribute("quizzes", null);
                request.getRequestDispatcher("customer-view-lesson.jsp").forward(request, response);
                return;
            }

            // Determine active lesson
            Lesson activeLesson = null;
            String lessonIdRaw = request.getParameter("lessonId");
            if (lessonIdRaw != null && !lessonIdRaw.isEmpty()) {
                try {
                    long lessonId = Long.parseLong(lessonIdRaw);
                    for (Lesson l : lessons) {
                        if (l.getLessonId() == lessonId) {
                            activeLesson = l;
                            break;
                        }
                    }
                } catch (NumberFormatException ignore) {
                    // Fallback handled below: default to first lesson
                }
            }
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


