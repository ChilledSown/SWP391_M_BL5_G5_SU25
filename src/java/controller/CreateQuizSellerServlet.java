package controller;

import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import model.Quiz;

@WebServlet(name = "CreateQuizSellerServlet", urlPatterns = {"/createQuizSeller"})
public class CreateQuizSellerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String question = request.getParameter("question");
            String answerOptionA = request.getParameter("answerOptionA");
            String answerOptionB = request.getParameter("answerOptionB");
            String answerOptionC = request.getParameter("answerOptionC");
            String answerOptionD = request.getParameter("answerOptionD");
            String correctAnswer = request.getParameter("correctAnswer");
            String explanation = request.getParameter("explanation");
            String lessonIdParam = request.getParameter("lessonId");

            // Validate inputs
            if (question == null || question.trim().isEmpty()
                    || correctAnswer == null || correctAnswer.trim().isEmpty()
                    || explanation == null || explanation.trim().isEmpty()
                    || lessonIdParam == null || lessonIdParam.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Question, correct answer, explanation, and lesson ID are required.");
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                return;
            }

            // Validate answer options (at least two non-empty options)
            ArrayList<String> options = new ArrayList<>();
            if (answerOptionA != null && !answerOptionA.trim().isEmpty()) {
                options.add(answerOptionA.trim());
            }
            if (answerOptionB != null && !answerOptionB.trim().isEmpty()) {
                options.add(answerOptionB.trim());
            }
            if (answerOptionC != null && !answerOptionC.trim().isEmpty()) {
                options.add(answerOptionC.trim());
            }
            if (answerOptionD != null && !answerOptionD.trim().isEmpty()) {
                options.add(answerOptionD.trim());
            }
            if (options.size() < 2) {
                request.setAttribute("errorMessage", "At least two answer options are required.");
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                return;
            }

            Long lessonId = Long.parseLong(lessonIdParam);

            // Validate correct answer
            String correctAnswerUpper = correctAnswer.trim().toUpperCase();
            if (!correctAnswerUpper.matches("[A-D]")
                    || (correctAnswerUpper.equals("A") && (answerOptionA == null || answerOptionA.trim().isEmpty()))
                    || (correctAnswerUpper.equals("B") && (answerOptionB == null || answerOptionB.trim().isEmpty()))
                    || (correctAnswerUpper.equals("C") && (answerOptionC == null || answerOptionC.trim().isEmpty()))
                    || (correctAnswerUpper.equals("D") && (answerOptionD == null || answerOptionD.trim().isEmpty()))) {
                request.setAttribute("errorMessage", "Correct answer must be A, B, C, or D and correspond to a non-empty option.");
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                return;
            }

            // Validate duplicate question
            QuizDAO dao = new QuizDAO();
            if (dao.isQuestionDuplicate(question.trim(), lessonId)) {
                request.setAttribute("errorMessage", "This question already exists for the specified lesson.");
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                return;
            }

            // Combine answer options
            String answerOptions = String.join(";", options);

            Quiz quiz = new Quiz();
            quiz.setQuestion(question.trim());
            quiz.setAnswerOptions(answerOptions);
            quiz.setCorrectAnswer(correctAnswerUpper);
            quiz.setExplanation(explanation.trim());
            quiz.setLessonId(lessonId);
            quiz.setCreatedAt(new Date());
            quiz.setUpdatedAt(new Date());

            dao.createQuiz(quiz);
            response.sendRedirect("manageQuiz?lessonId=" + lessonId);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid lesson ID format.");
            request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error creating quiz.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles creation of new quizzes with validation for duplicate questions and required explanation";
    }
}
