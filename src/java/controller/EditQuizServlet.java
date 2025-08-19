package controller;

import dal.QuizDAO;
import model.Quiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "EditQuizServlet", urlPatterns = {"/editQuiz"})
public class EditQuizServlet extends HttpServlet {

    // Handle GET: Display quiz form with data from quizId
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

    // Handle POST: Save changes when form is submitted
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
            if (question == null || question.trim().isEmpty()
                    || answerOptionA == null || answerOptionA.trim().isEmpty()
                    || answerOptionB == null || answerOptionB.trim().isEmpty()
                    || answerOptionC == null || answerOptionC.trim().isEmpty()
//                    || answerOptionD == null || answerOptionD.trim().isEmpty()
//                    || correctAnswer == null || correctAnswer.trim().isEmpty()
                    || explanation == null || explanation.trim().isEmpty()) {
                request.setAttribute("errorMessage", "All fields are required.");
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

            // Validate correct answer
            if (!correctAnswer.matches("[A-D]")) {
                request.setAttribute("errorMessage", "Correct answer must be A, B, C, or D.");
                Quiz quiz = new Quiz();
                quiz.setQuizId(quizId);
                quiz.setLessonId(lessonId);
                quiz.setQuestion(question);
                quiz.setAnswerOptions(String.join(";", answerOptionA, answerOptionB, answerOptionC, answerOptionD));
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
                quiz.setAnswerOptions(String.join(";", answerOptionA, answerOptionB, answerOptionC, answerOptionD));
                quiz.setCorrectAnswer(correctAnswer);
                quiz.setExplanation(explanation);
                request.setAttribute("quiz", quiz);
                request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
                return;
            }

            // Combine answer options
            String answerOptions = String.join(";",
                    answerOptionA.trim(),
                    answerOptionB.trim(),
                    answerOptionC.trim(),
                    answerOptionD.trim());

            Quiz quiz = new Quiz();
            quiz.setQuizId(quizId);
            quiz.setLessonId(lessonId);
            quiz.setQuestion(question.trim());
            quiz.setAnswerOptions(answerOptions);
            quiz.setCorrectAnswer(correctAnswer.trim());
            quiz.setExplanation(explanation.trim());
            quiz.setUpdatedAt(new Date());

            dao.updateQuiz(quiz);
            response.sendRedirect("manageQuiz?lessonId=" + lessonId);
        } catch (NumberFormatException e) {
//            response.sendError("Invalid quiz or lesson ID");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update quiz.");
        }
    }
}
