package controller;

import dal.QuizDAO;
import model.Quiz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import model.User;

@WebServlet(name = "EditQuizSellerServlet", urlPatterns = {"/editQuizSeller"})
public class EditQuizSellerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
        
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
            // Retrieve parameters
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

            // Log input parameters for debugging
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

            // Validate required IDs
            if (quizIdRaw == null || quizIdRaw.trim().isEmpty() ||
                lessonIdRaw == null || lessonIdRaw.trim().isEmpty() ||
                courseIdRaw == null || courseIdRaw.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Quiz ID, lesson ID, and course ID are required.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }

            // Parse IDs
            long quizId = Long.parseLong(quizIdRaw);
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);

            // Validate question
            if (question == null || question.trim().isEmpty()) {
                request.setAttribute("questionError", "Question is required.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }

            // Validate answer options (at least two non-empty, no duplicates)
            ArrayList<String> options = new ArrayList<>();
            if (answerOptionA != null && !answerOptionA.trim().isEmpty()) options.add(answerOptionA.trim());
            if (answerOptionB != null && !answerOptionB.trim().isEmpty()) options.add(answerOptionB.trim());
            if (answerOptionC != null && !answerOptionC.trim().isEmpty()) options.add(answerOptionC.trim());
            if (answerOptionD != null && !answerOptionD.trim().isEmpty()) options.add(answerOptionD.trim());
            if (options.size() < 2) {
                request.setAttribute("optionsError", "At least two answer options are required.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }

            // Check for duplicate answer options (case-insensitive)
            Set<String> uniqueOptions = new HashSet<>();
            for (String option : options) {
                if (!uniqueOptions.add(option.toLowerCase())) {
                    request.setAttribute("optionsError", "Answer options must not be duplicates.");
                    forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                    return;
                }
            }

            // Validate correct answer
            if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
                request.setAttribute("correctAnswerError", "Correct answer is required.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }
            String correctAnswerUpper = correctAnswer.trim().toUpperCase();
            if (!correctAnswerUpper.matches("[A-D]")) {
                request.setAttribute("correctAnswerError", "Correct answer must be A, B, C, or D.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }
            if ((correctAnswerUpper.equals("A") && (answerOptionA == null || answerOptionA.trim().isEmpty())) ||
                (correctAnswerUpper.equals("B") && (answerOptionB == null || answerOptionB.trim().isEmpty())) ||
                (correctAnswerUpper.equals("C") && (answerOptionC == null || answerOptionC.trim().isEmpty())) ||
                (correctAnswerUpper.equals("D") && (answerOptionD == null || answerOptionD.trim().isEmpty()))) {
                request.setAttribute("correctAnswerError", "Correct answer must correspond to a non-empty option.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }

            // Validate explanation
            if (explanation == null || explanation.trim().isEmpty()) {
                request.setAttribute("explanationError", "Explanation is required.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }

            // Validate duplicate question
            QuizDAO dao = new QuizDAO();
            if (dao.isQuestionDuplicateForUpdate(question.trim(), lessonId, quizId)) {
                request.setAttribute("questionError", "This question already exists for the specified lesson.");
                forwardToForm(request, response, quizIdRaw, lessonIdRaw, courseIdRaw, question, answerOptionA, answerOptionB, answerOptionC, answerOptionD, correctAnswer, explanation);
                return;
            }

            // Combine answer options
            String answerOptions = String.join(";", options);

            // Update quiz
            Quiz quiz = new Quiz();
            quiz.setQuizId(quizId);
            quiz.setLessonId(lessonId);
            quiz.setQuestion(question.trim());
            quiz.setAnswerOptions(answerOptions);
            quiz.setCorrectAnswer(correctAnswerUpper);
            quiz.setExplanation(explanation.trim());
            quiz.setUpdatedAt(new Date());
            dao.updateQuiz(quiz);

            // Redirect on success
            response.sendRedirect("manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz, lesson, or course ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update quiz: " + e.getMessage());
        }
    }

    private void forwardToForm(HttpServletRequest request, HttpServletResponse response, String quizId, String lessonId, String courseId,
                              String question, String answerOptionA, String answerOptionB, String answerOptionC,
                              String answerOptionD, String correctAnswer, String explanation)
            throws ServletException, IOException {
        Quiz quiz = new Quiz();
        if (quizId != null && !quizId.trim().isEmpty()) quiz.setQuizId(Long.parseLong(quizId));
        if (lessonId != null && !lessonId.trim().isEmpty()) quiz.setLessonId(Long.parseLong(lessonId));
        quiz.setQuestion(question != null ? question : "");
        quiz.setAnswerOptions(String.join(";",
            answerOptionA != null ? answerOptionA : "",
            answerOptionB != null ? answerOptionB : "",
            answerOptionC != null ? answerOptionC : "",
            answerOptionD != null ? answerOptionD : ""));
        quiz.setCorrectAnswer(correctAnswer != null ? correctAnswer : "");
        quiz.setExplanation(explanation != null ? explanation : "");
        request.setAttribute("quiz", quiz);
        request.setAttribute("lessonId", lessonId);
        request.setAttribute("courseId", courseId);
        request.getRequestDispatcher("quiz_form.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles editing of quizzes with server-side validation for all fields, including duplicate answer options";
    }
}