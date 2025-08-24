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
        String lessonIdRaw = request.getParameter("lessonId");
        String courseIdRaw = request.getParameter("courseId");
        String quizIdRaw = request.getParameter("quizId");
        if (lessonIdRaw == null || lessonIdRaw.trim().isEmpty() || 
            courseIdRaw == null || courseIdRaw.trim().isEmpty() || 
            quizIdRaw == null || quizIdRaw.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing quiz, lesson, or course ID.");
            return;
        }
        try {
            long quizId = Long.parseLong(quizIdRaw);
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);
            QuizDAO dao = new QuizDAO();
            Quiz quiz = dao.getQuizById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }
            request.setAttribute("quiz", quiz);
            request.setAttribute("lessonId", lessonId);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz, lesson, or course ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving quiz.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Log input parameters for debugging
            String quizIdRaw = request.getParameter("quizId");
            String lessonIdRaw = request.getParameter("lessonId");
            String courseIdRaw = request.getParameter("courseId");
            String question = request.getParameter("question");
            String answerOptionA = request.getParameter("answerOptionA");
            String answerOptionB = request.getParameter("answerOptionB");
            String answerOptionC = request.getParameter("answerOptionC");
            String answerOptionD = request.getParameter("answerOptionD");
            String correctAnswer = request.getParameter("correctAnswer");
            String explanation = request.getParameter("explanation");

            System.out.println("EditQuizSellerServlet - Parameters:");
            System.out.println("quizId: " + quizIdRaw);
            System.out.println("lessonId: " + lessonIdRaw);
            System.out.println("courseId: " + courseIdRaw);
            System.out.println("question: " + question);
            System.out.println("answerOptionA: " + answerOptionA);
            System.out.println("answerOptionB: " + answerOptionB);
            System.out.println("answerOptionC: " + answerOptionC);
            System.out.println("answerOptionD: " + answerOptionD);
            System.out.println("correctAnswer: " + correctAnswer);
            System.out.println("explanation: " + explanation);

            // Validate inputs
            if (quizIdRaw == null || quizIdRaw.trim().isEmpty() ||
                lessonIdRaw == null || lessonIdRaw.trim().isEmpty() ||
                courseIdRaw == null || courseIdRaw.trim().isEmpty() ||
                question == null || question.trim().isEmpty() ||
                correctAnswer == null || correctAnswer.trim().isEmpty() ||
                explanation == null || explanation.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Quiz ID, lesson ID, course ID, question, correct answer, and explanation are required.");
                Quiz quiz = new Quiz();
                quiz.setQuizId(quizIdRaw != null ? Long.parseLong(quizIdRaw) : 0);
                quiz.setLessonId(lessonIdRaw != null ? Long.parseLong(lessonIdRaw) : 0);
                quiz.setQuestion(question != null ? question : "");
                quiz.setAnswerOptions(String.join(";", 
                    answerOptionA != null ? answerOptionA : "",
                    answerOptionB != null ? answerOptionB : "",
                    answerOptionC != null ? answerOptionC : "",
                    answerOptionD != null ? answerOptionD : ""));
                quiz.setCorrectAnswer(correctAnswer != null ? correctAnswer : "");
                quiz.setExplanation(explanation != null ? explanation : "");
                request.setAttribute("quiz", quiz);
                request.setAttribute("lessonId", lessonIdRaw);
                request.setAttribute("courseId", courseIdRaw);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
                request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
                return;
            }

            // Parse IDs
            long quizId = Long.parseLong(quizIdRaw);
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);

            // Validate answer options (at least two options, including the correct answer)
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
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
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
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
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
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
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
            response.sendRedirect("manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz, lesson, or course ID.");
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