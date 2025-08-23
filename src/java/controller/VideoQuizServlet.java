package controller;

import dal.VideoQuizDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.VideoQuiz;

@WebServlet(name="VideoQuizServlet", urlPatterns={"/video-quiz"})
public class VideoQuizServlet extends HttpServlet {

    private VideoQuizDAO videoQuizDAO;

    @Override
    public void init() throws ServletException {
        videoQuizDAO = new VideoQuizDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        System.out.println("VideoQuizServlet - Action: " + action);
        
        if ("get-quizzes".equals(action)) {
            getVideoQuizzes(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        System.out.println("VideoQuizServlet - Action: " + action);
        
        if ("submit-answer".equals(action)) {
            submitAnswer(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid action");
        }
    }

    private void getVideoQuizzes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String lessonIdStr = request.getParameter("lessonId");
            System.out.println("VideoQuizServlet - Getting quizzes for lesson: " + lessonIdStr);
            
            if (lessonIdStr == null || lessonIdStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing lessonId parameter");
                return;
            }
            
            long lessonId = Integer.parseInt(lessonIdStr);
            List<VideoQuiz> quizzes = videoQuizDAO.getVideoQuizzesByLessonId(lessonId);
            
            System.out.println("VideoQuizServlet - Found " + quizzes.size() + " quizzes for lesson " + lessonId);
            
            // Set response type to plain text
            response.setContentType("text/plain;charset=UTF-8");
            
            if (quizzes.isEmpty()) {
                response.getWriter().write("No quizzes found for this lesson");
                return;
            }
            
            // Build plain text response with custom delimiters
            StringBuilder responseText = new StringBuilder();
            for (VideoQuiz quiz : quizzes) {
                responseText.append("QUIZ|")
                          .append(quiz.getVideoQuizId()).append("|")
                          .append(quiz.getLessonId()).append("|")
                          .append(quiz.getTimestamp()).append("|")
                          .append(quiz.getQuestion()).append("|")
                          .append(quiz.getAnswerOptions()).append("|")
                          .append(quiz.getCorrectAnswer()).append("|")
                          .append(quiz.getExplanation() != null ? quiz.getExplanation() : "")
                          .append("\n");
                
                System.out.println("VideoQuizServlet - Added quiz: " + quiz.getQuestion() + " at " + quiz.getTimestamp() + "s");
            }
            
            response.getWriter().write(responseText.toString());
            System.out.println("VideoQuizServlet - Response sent successfully");
            
        } catch (NumberFormatException e) {
            System.out.println("VideoQuizServlet - Invalid lessonId format: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid lessonId format");
        } catch (Exception e) {
            System.out.println("VideoQuizServlet - Error getting quizzes: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error");
        }
    }

    private void submitAnswer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String quizIdStr = request.getParameter("quizId");
            String selectedAnswer = request.getParameter("selectedAnswer");
            
            System.out.println("VideoQuizServlet - Submitting answer for quiz: " + quizIdStr + ", answer: " + selectedAnswer);
            
            if (quizIdStr == null || selectedAnswer == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing quizId or selectedAnswer");
                return;
            }
            
            long quizId = Long.parseLong(quizIdStr);
            VideoQuiz quiz = videoQuizDAO.getVideoQuizById(quizId);
            
            if (quiz == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Quiz not found");
                return;
            }
            boolean isCorrect = quiz.getCorrectAnswer().equals(selectedAnswer);
            StringBuilder responseText = new StringBuilder();
            responseText.append("RESULT|")
                      .append(isCorrect ? "CORRECT" : "INCORRECT").append("|")
                      .append(quiz.getCorrectAnswer()).append("|")
                      .append(quiz.getExplanation() != null ? quiz.getExplanation() : "");
            
            // Set response type to plain text
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write(responseText.toString());
            
            System.out.println("VideoQuizServlet - Answer submitted: " + (isCorrect ? "CORRECT" : "INCORRECT"));
            
        } catch (NumberFormatException e) {
            System.out.println("VideoQuizServlet - Invalid quizId format: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid quizId format");
        } catch (Exception e) {
            System.out.println("VideoQuizServlet - Error submitting answer: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal server error");
        }
    }
}
