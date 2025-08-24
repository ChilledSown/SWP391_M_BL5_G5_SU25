package controller;

import dal.CourseDAO;
import dal.LessonDAO;
import dal.QuizDAO;
import model.Course;
import model.Lesson;
import model.Quiz;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="CustomerListQuizServlet", urlPatterns={"/customer-list-quiz"})
public class CustomerListQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        
        String courseIdStr = request.getParameter("courseId");
        String lessonIdStr = request.getParameter("lessonId");
        
        if (courseIdStr == null || lessonIdStr == null) {
            response.sendRedirect("purchased-courses");
            return;
        }
        
        try {
            Long courseId = Long.parseLong(courseIdStr);
            Long lessonId = Long.parseLong(lessonIdStr);
            
            CourseDAO courseDAO = new CourseDAO();
            LessonDAO lessonDAO = new LessonDAO();
            QuizDAO quizDAO = new QuizDAO();
            
            Course course = courseDAO.getCourseById(courseId);
            Lesson lesson = lessonDAO.getLessonById(lessonId);
            List<Quiz> allQuizzes = quizDAO.getQuizzesByCourseId(courseId);
            int totalCourseQuizzes = quizDAO.countQuizzesByCourseId(courseId);
            
            if (course == null || lesson == null) {
                response.sendRedirect("purchased-courses");
                return;
            }
            
            request.setAttribute("course", course);
            request.setAttribute("lesson", lesson);
            request.setAttribute("quizzes", allQuizzes);
            request.setAttribute("totalCourseQuizzes", totalCourseQuizzes);
            
            request.getRequestDispatcher("customer-list-quiz.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("purchased-courses");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login");
            return;
        }
        
        String courseIdStr = request.getParameter("courseId");
        String lessonIdStr = request.getParameter("lessonId");
        
        try {
            Long courseId = Long.parseLong(courseIdStr);
            Long lessonId = Long.parseLong(lessonIdStr);
            
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> allQuizzes = quizDAO.getQuizzesByCourseId(courseId);
            int totalQuestions = allQuizzes.size();
            int correctAnswers = 0;
            
            for (Quiz quiz : allQuizzes) {
                String userAnswer = request.getParameter("answer_" + quiz.getQuizId());
                if (userAnswer != null && userAnswer.equals(quiz.getCorrectAnswer())) {
                    correctAnswers++;
                }
            }
            
            double score = totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;

            int totalCourseQuizzes = quizDAO.countQuizzesByCourseId(courseId);
            
            request.setAttribute("quizzes", allQuizzes);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("correctAnswers", correctAnswers);
            request.setAttribute("score", score);
            request.setAttribute("showResults", true);
            request.setAttribute("courseId", courseId);
            request.setAttribute("lessonId", lessonId);
            request.setAttribute("totalCourseQuizzes", totalCourseQuizzes);
            
            request.getRequestDispatcher("customer-list-quiz.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("purchased-courses");
        }
    }
}
