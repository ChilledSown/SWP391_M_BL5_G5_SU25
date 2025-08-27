package controller;

import dal.CourseDAO;
import dal.LessonDAO;
import dal.QuizDAO;
import model.Course;
import model.Lesson;
import model.User;
import utils.GeminiUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name="CustomerQuizPreparationServlet", urlPatterns={"/customer-quiz-preparation"})
public class CustomerQuizPreparationServlet extends HttpServlet {

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
            List<Lesson> lessons = lessonDAO.getLessonsByCourseId(courseId);
            int totalCourseQuizzes = quizDAO.countQuizzesByCourseId(courseId);
            if (course == null || lesson == null) {
                response.sendRedirect("purchased-courses");
                return;
            }
            
            request.setAttribute("course", course);
            request.setAttribute("lesson", lesson);
            request.setAttribute("lessons", lessons);
            request.setAttribute("totalCourseQuizzes", totalCourseQuizzes);
            request.getRequestDispatcher("customer-quiz-preparation.jsp").forward(request, response);
            
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
            
            CourseDAO courseDAO = new CourseDAO();
            LessonDAO lessonDAO = new LessonDAO();
            
            Course course = courseDAO.getCourseById(courseId);
            Lesson lesson = lessonDAO.getLessonById(lessonId);
            
            if (course != null && lesson != null) {
                // Generate prompt for Gemini
                String prompt = generatePrompt(course, lesson);
                
                // Call Gemini API to generate response
                String aiResponse = callGeminiAPI(prompt);
                
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(aiResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Course or lesson not found");
            }
            
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid data format");
        }
    }
    
    private String generatePrompt(Course course, Lesson lesson) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an intelligent AI teacher. Please help students review their knowledge before taking a quiz.\n\n");
        prompt.append("Course Information:\n");
        prompt.append("- Course Name: ").append(course.getTitle()).append("\n");
        prompt.append("- Course Description: ").append(course.getDescription()).append("\n");
        prompt.append("- Current Lesson: ").append(lesson.getTitle()).append("\n");
        prompt.append("- Lesson Content: ").append(lesson.getContent()).append("\n\n");
        prompt.append("Please create a concise and easy-to-understand summary of the key points to remember in this lesson. ");
        prompt.append("Provide tips and suggestions to help students perform well in the upcoming quiz. ");
        prompt.append("Respond in plain text only - no markdown formatting, no bold, no special characters. ");
        prompt.append("Use simple text with bullet points (•) for lists. Respond in English, be brief and friendly.");
        
        return prompt.toString();
    }
    
    private String callGeminiAPI(String prompt) {
        return GeminiUtil.generateResponse(prompt);
    }
}
