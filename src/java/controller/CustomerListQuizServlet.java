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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.QuizResult;

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
            int answeredQuestions = 0;
            //Map to store detailed result of every question
            Map<Long, QuizResult> quizResults = new HashMap<>();
            
            for (Quiz quiz : allQuizzes) {
                String userAnswer = request.getParameter("answer_" + quiz.getQuizId());
                boolean isCorrect = false;
                boolean isAnswered = false;
                
                if (userAnswer != null && !userAnswer.trim().isEmpty()) {
                    isAnswered = true;
                    answeredQuestions++;
                    if (userAnswer.equals(quiz.getCorrectAnswer())) {
                        isCorrect = true;
                        correctAnswers++;
                    }
                }
                QuizResult result = new QuizResult();
                result.setQuizId(quiz.getQuizId());
                result.setUserAnswer(userAnswer);
                result.setCorrectAnswer(quiz.getCorrectAnswer());
                result.setCorrect(isCorrect);
                result.setAnswered(isAnswered);
                result.setQuestion(quiz.getQuestion());
                result.setExplanation(quiz.getExplanation());
                quizResults.put(quiz.getQuizId(), result);
            }
            
            // Calculate score
            double score = totalQuestions > 0 ? (double) correctAnswers / totalQuestions * 100 : 0;
            double answeredPercentage = totalQuestions > 0 ? (double) answeredQuestions / totalQuestions * 100 : 0;
            
            // Determine pass/not pass status
            boolean isPassed = score >= 100; // Need 100% to pass
            String grade = getGrade(score);
            
            int totalCourseQuizzes = allQuizzes.size();
            CourseDAO courseDAO = new CourseDAO();
            LessonDAO lessonDAO = new LessonDAO();
            Course course = courseDAO.getCourseById(courseId);
            Lesson lesson = lessonDAO.getLessonById(lessonId);
            
            request.setAttribute("quizzes", allQuizzes);
            request.setAttribute("quizResults", quizResults);
            request.setAttribute("totalQuestions", totalQuestions);
            request.setAttribute("correctAnswers", correctAnswers);
            request.setAttribute("answeredQuestions", answeredQuestions);
            request.setAttribute("score", Math.round(score * 100.0) / 100.0); 
            request.setAttribute("answeredPercentage", Math.round(answeredPercentage * 100.0) / 100.0);
            request.setAttribute("isPassed", isPassed);
            request.setAttribute("grade", grade);
            request.setAttribute("showResults", true);
            request.setAttribute("courseId", courseId);
            request.setAttribute("lessonId", lessonId);
            request.setAttribute("totalCourseQuizzes", totalCourseQuizzes);
            request.setAttribute("course", course);
            request.setAttribute("lesson", lesson);
            
            request.getRequestDispatcher("customer-list-quiz.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("purchased-courses");
        }
    }
    
    /**
     * Determine grade base on score
     */
    private String getGrade(double score) {
        if (score >= 100) return "A+ (Excellent)";
        else if (score >= 90) return "A (Very Good)";
        else if (score >= 80) return "B (Good)";
        else if (score >= 70) return "C (Average)";
        else if (score >= 60) return "D (Below Average)";
        else return "F (Fail)";
    }
}
