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
        String lessonIdRaw = request.getParameter("lessonId");
        String courseIdRaw = request.getParameter("courseId");
        request.setAttribute("lessonId", lessonIdRaw);
        request.setAttribute("courseId", courseIdRaw);
        request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lessonIdRaw = request.getParameter("lessonId");
        String courseIdRaw = request.getParameter("courseId");
        if (lessonIdRaw == null || lessonIdRaw.trim().isEmpty() || courseIdRaw == null || courseIdRaw.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing lesson or course ID.");
            return;
        }
        try {
            long lessonId = Long.parseLong(lessonIdRaw);
            long courseId = Long.parseLong(courseIdRaw);
            request.setAttribute("lessonId", lessonId);
            request.setAttribute("courseId", courseId);
            request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lesson or course ID format.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Log input parameters for debugging
            String question = request.getParameter("question");
            String answerOptionA = request.getParameter("answerOptionA");
            String answerOptionB = request.getParameter("answerOptionB");
            String answerOptionC = request.getParameter("answerOptionC");
            String answerOptionD = request.getParameter("answerOptionD");
            String correctAnswer = request.getParameter("correctAnswer");
            String explanation = request.getParameter("explanation");
            String lessonIdParam = request.getParameter("lessonId");
            String courseIdParam = request.getParameter("courseId");

            System.out.println("CreateQuizSellerServlet - Parameters:");
            System.out.println("question: " + question);
            System.out.println("answerOptionA: " + answerOptionA);
            System.out.println("answerOptionB: " + answerOptionB);
            System.out.println("answerOptionC: " + answerOptionC);
            System.out.println("answerOptionD: " + answerOptionD);
            System.out.println("correctAnswer: " + correctAnswer);
            System.out.println("explanation: " + explanation);
            System.out.println("lessonIdParam: " + lessonIdParam);
            System.out.println("courseIdParam: " + courseIdParam);

            // Validate inputs
            if (question == null || question.trim().isEmpty() ||
                correctAnswer == null || correctAnswer.trim().isEmpty() ||
                explanation == null || explanation.trim().isEmpty() ||
                lessonIdParam == null || lessonIdParam.trim().isEmpty() ||
                courseIdParam == null || courseIdParam.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Question, correct answer, explanation, lesson ID, and course ID are required.");
                request.setAttribute("lessonId", lessonIdParam);
                request.setAttribute("courseId", courseIdParam);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                return;
            }

            // Parse lessonId and courseId
            Long lessonId = Long.parseLong(lessonIdParam);
            Long courseId = Long.parseLong(courseIdParam);

            // Validate answer options (at least two options, including the correct answer)
            ArrayList<String> options = new ArrayList<>();
            if (answerOptionA != null && !answerOptionA.trim().isEmpty()) options.add(answerOptionA.trim());
            if (answerOptionB != null && !answerOptionB.trim().isEmpty()) options.add(answerOptionB.trim());
            if (answerOptionC != null && !answerOptionC.trim().isEmpty()) options.add(answerOptionC.trim());
            if (answerOptionD != null && !answerOptionD.trim().isEmpty()) options.add(answerOptionD.trim());
            if (options.size() < 2) {
                request.setAttribute("errorMessage", "At least two answer options are required.");
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
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
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
                request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
                return;
            }

            // Validate duplicate question
            QuizDAO dao = new QuizDAO();
            if (dao.isQuestionDuplicate(question.trim(), lessonId)) {
                request.setAttribute("errorMessage", "This question already exists for the specified lesson.");
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("question", question);
                request.setAttribute("answerOptionA", answerOptionA);
                request.setAttribute("answerOptionB", answerOptionB);
                request.setAttribute("answerOptionC", answerOptionC);
                request.setAttribute("answerOptionD", answerOptionD);
                request.setAttribute("correctAnswer", correctAnswer);
                request.setAttribute("explanation", explanation);
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
            response.sendRedirect("manageQuizInstructor?lessonId=" + lessonId + "&courseId=" + courseId);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid lesson or course ID format.");
            request.setAttribute("lessonId", request.getParameter("lessonId"));
            request.setAttribute("courseId", request.getParameter("courseId"));
            request.getRequestDispatcher("/quiz_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating quiz.");
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles creation of new quizzes with validation for duplicate questions and required explanation";
    }
}