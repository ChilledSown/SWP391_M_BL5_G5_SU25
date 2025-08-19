package controller;

import dal.QuizDAO;
import model.Quiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "EditQuizSellerServlet", urlPatterns = {"/editQuizSeller"})
public class EditQuizSellerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long quizId = Long.parseLong(request.getParameter("quizId"));
            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            QuizDAO dao = new QuizDAO();
            Quiz quiz = dao.getQuizById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }
            request.setAttribute("quiz", quiz);
            request.setAttribute("lessonId", lessonId);
            request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz or lesson ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving quiz.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long quizId = Long.parseLong(request.getParameter("quizId"));
            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            String question = request.getParameter("question");
            String answerOptionA = request.getParameter("answerOptionA");
            String answerOptionB = request.getParameter("answerOptionB");
            String answerOptionC = request.getParameter("answerOptionC");
            String answerOptionD = request.getParameter("answerOptionD");
            String correctAnswer = request.getParameter("correctAnswer");
            String explanation = request.getParameter("explanation");

            // Validate inputs
            if (question == null || question.trim().isEmpty() ||
                correctAnswer == null || correctAnswer.trim().isEmpty() ||
                explanation == null || explanation.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Question, correct answer, and explanation are required.");
                Quiz quiz = new Quiz();
                quiz.setQuizId(quizId);
                quiz.setLessonId(lessonId);
                quiz.setQuestion(question != null ? question : "");
                quiz.setAnswerOptions(String.join(";", 
                    answerOptionA != null ? answerOptionA : "",
                    answerOptionB != null ? answerOptionB : "",
                    answerOptionC != null ? answerOptionC : "",
                    answerOptionD != null ? answerOptionD : ""));
                quiz.setCorrectAnswer(correctAnswer != null ? correctAnswer : "");
                quiz.setExplanation(explanation != null ? explanation : "");
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
                return;
            }

            // Validate answer options (at least two non-empty options)
            ArrayList<String> options = new ArrayList<>();
            if (answerOptionA != null && !answerOptionA.trim().isEmpty()) options.add(answerOptionA.trim());
            if (answerOptionB != null && !answerOptionB.trim().isEmpty()) options.add(answerOptionB.trim());
            if (answerOptionC != null && !answerOptionC.trim().isEmpty()) options.add(answerOptionC.trim());
            if (answerOptionD != null && !answerOptionD.trim().isEmpty()) options.add(answerOptionD.trim());
            if (options.size() < 2) {
                request.setAttribute("errorMessage", "At least two answer options are required.");
                Quiz quiz = new Quiz();
                quiz.setQuizId(quizId);
                quiz.setLessonId(lessonId);
                quiz.setQuestion(question);
                quiz.setAnswerOptions(String.join(";", 
                    answerOptionA != null ? answerOptionA : "",
                    answerOptionB != null ? answerOptionB : "",
                    answerOptionC != null ? answerOptionC : "",
                    answerOptionD != null ? answerOptionD : ""));
                quiz.setCorrectAnswer(correctAnswer);
                quiz.setExplanation(explanation);
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
                return;
            }

            // Validate correct answer
            String correctAnswerUpper = correctAnswer.trim().toUpperCase();
            if (!correctAnswerUpper.matches("[A-D]") || 
                (correctAnswerUpper.equals("A") && (answerOptionA == null || answerOptionA.trim().isEmpty())) ||
                (correctAnswerUpper.equals("B") && (answerOptionB == null || answerOptionB.trim().isEmpty())) ||
                (correctAnswerUpper.equals("C") && (answerOptionC == null || answerOptionC.trim().isEmpty())) ||
                (correctAnswerUpper.equals("D") && (answerOptionD == null || answerOptionD.trim().isEmpty()))) {
                request.setAttribute("errorMessage", "Correct answer must be A, B, C, or D and correspond to a non-empty option.");
                Quiz quiz = new Quiz();
                quiz.setQuizId(quizId);
                quiz.setLessonId(lessonId);
                quiz.setQuestion(question);
                quiz.setAnswerOptions(String.join(";", 
                    answerOptionA != null ? answerOptionA : "",
                    answerOptionB != null ? answerOptionB : "",
                    answerOptionC != null ? answerOptionC : "",
                    answerOptionD != null ? answerOptionD : ""));
                quiz.setCorrectAnswer(correctAnswer);
                quiz.setExplanation(explanation);
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
                return;
            }

            // Validate duplicate question
            QuizDAO dao = new QuizDAO();
            if (dao.isQuestionDuplicateForUpdate(question.trim(), lessonId, quizId)) {
                request.setAttribute("errorMessage", "This question already exists for the specified lesson.");
                Quiz quiz = new Quiz();
                quiz.setQuizId(quizId);
                quiz.setLessonId(lessonId);
                quiz.setQuestion(question);
                quiz.setAnswerOptions(String.join(";", 
                    answerOptionA != null ? answerOptionA : "",
                    answerOptionB != null ? answerOptionB : "",
                    answerOptionC != null ? answerOptionC : "",
                    answerOptionD != null ? answerOptionD : ""));
                quiz.setCorrectAnswer(correctAnswer);
                quiz.setExplanation(explanation);
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
                return;
            }

            // Combine answer options
            String answerOptions = String.join(";", options);

            Quiz quiz = new Quiz();
            quiz.setQuizId(quizId);
            quiz.setLessonId(lessonId);
            quiz.setQuestion(question.trim());
            quiz.setAnswerOptions(answerOptions);
            quiz.setCorrectAnswer(correctAnswerUpper);
            quiz.setExplanation(explanation.trim());
            quiz.setUpdatedAt(new Date());

            dao.updateQuiz(quiz);
            response.sendRedirect("manageQuiz?lessonId=" + lessonId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz or lesson ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update quiz.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles editing of quizzes with validation for duplicate questions and required explanation";
    }
}