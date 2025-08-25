/*
 * Customer lesson viewer servlet
 */
package controller;

import dal.CourseDAO;
import dal.LessonDAO;
import dal.QuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Course;
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
            if (courseIdRaw != null && !courseIdRaw.isEmpty()) {
                courseId = Long.valueOf(courseIdRaw);
            }
            if (lessonIdRaw != null && !lessonIdRaw.isEmpty()) {
                lessonId = Long.valueOf(lessonIdRaw);
            }

            LessonDAO lessonDAO = new LessonDAO();
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
            List<Lesson> lessons = lessonDAO.getLessonsByCourseId(courseId);
            Lesson activeLesson = null;

            if (lessons == null || lessons.isEmpty()) {
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessons", lessons);
                request.setAttribute("activeLesson", null);
                request.setAttribute("quizzes", null);
                request.getRequestDispatcher("customer-view-lesson.jsp").forward(request, response);
                return;
            }
            if (lessonId != null) {
                for (Lesson l : lessons) {
                    if (l.getLessonId().equals(lessonId)) {
                        activeLesson = l;
                        break;
                    }
                }
            }
            if (activeLesson == null) {
                activeLesson = lessons.get(0);
            }
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> quizzes = quizDAO.getQuizzesByLessonId(activeLesson.getLessonId());
            CourseDAO courseDAO = new CourseDAO();
            Course course = courseDAO.getCourseById(courseId);
            
            request.setAttribute("courseId", courseId);
            request.setAttribute("course", course);
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

