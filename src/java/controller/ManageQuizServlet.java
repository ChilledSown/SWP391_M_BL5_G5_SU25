package controller;

import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Quiz;

@WebServlet(name = "ManageQuizServlet", urlPatterns = {"/manageQuiz"})
public class ManageQuizServlet extends HttpServlet {

    private final int PAGE_SIZE = 8;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO qdao = new QuizDAO();

        String action = request.getParameter("action");
        String lessonId = request.getParameter("lessonId");
        String topicId = request.getParameter("topicId");
        String courseId = request.getParameter("courseId");
        String searchQuery = request.getParameter("query");

        // Handle the "details" action
        if ("details".equals(action)) {
            try {
                long quizId = Long.parseLong(request.getParameter("quizId"));
                Quiz quiz = qdao.getQuizById(quizId);
                if (quiz == null) {
                    request.setAttribute("error", "Quiz not found");
                    request.setAttribute("topicId", topicId);
                    request.setAttribute("courseId", courseId);
                    request.setAttribute("lessonId", lessonId);
                    request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("quiz", quiz);
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("quizDetails.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid quiz ID");
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
            }
            return;
        }

        // Handle the "edit" action
        if ("edit".equals(action)) {
            try {
                long quizId = Long.parseLong(request.getParameter("quizId"));
                Quiz quiz = qdao.getQuizById(quizId);
                if (quiz == null) {
                    request.setAttribute("error", "Quiz not found");
                    request.setAttribute("topicId", topicId);
                    request.setAttribute("courseId", courseId);
                    request.setAttribute("lessonId", lessonId);
                    request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("quiz", quiz);
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("updatequiz.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid quiz ID");
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
            }
            return;
        }

        // Handle pagination and search
        int page = 1;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
        } catch (NumberFormatException e) {
            // Default to page 1 if invalid
        }

        List<Quiz> quizzes;
        int totalQuizzes = 0;
        if (lessonId != null && !lessonId.trim().isEmpty()) {
            try {
                long lessonIdLong = Long.parseLong(lessonId);
                // Use pagination and search if query is present
                if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                    quizzes = qdao.getQuizzesWithPagination(searchQuery, lessonIdLong, page, PAGE_SIZE);
                    totalQuizzes = qdao.getTotalQuizzes(searchQuery, lessonIdLong);
                } else {
                    quizzes = qdao.getQuizzesByLessonId(lessonIdLong);
                    totalQuizzes = quizzes.size();
                }
                int totalPages = (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);
                if (totalPages == 0) totalPages = 1; // Ensure at least one page
                if (page > totalPages) {
                    page = totalPages;
                    if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                        quizzes = qdao.getQuizzesWithPagination(searchQuery, lessonIdLong, page, PAGE_SIZE);
                    } else {
                        quizzes = qdao.getQuizzesByLessonId(lessonIdLong);
                    }
                }
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid lesson ID: " + lessonId);
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("quizzes", List.of());
                request.setAttribute("currentPage", 1);
                request.setAttribute("totalPages", 1);
                request.setAttribute("searchQuery", searchQuery);
                request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
                return;
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing lessonId parameter.");
            return;
        }

        request.setAttribute("quizzes", quizzes);
        request.setAttribute("topicId", topicId);
        request.setAttribute("courseId", courseId);
        request.setAttribute("lessonId", lessonId);
        request.setAttribute("searchQuery", searchQuery);

        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }
        request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO qdao = new QuizDAO();
        String action = request.getParameter("action");
        String topicId = request.getParameter("topicId");
        String courseId = request.getParameter("courseId");
        String lessonId = request.getParameter("lessonId");

        // Handle "update" action
        if ("update".equals(action)) {
            String quizIdStr = request.getParameter("quizId");
            String question = request.getParameter("question");
            String answerOptions = request.getParameter("answerOptions");
            String correctAnswer = request.getParameter("correctAnswer");

            String error = null;
            try {
                long quizId = Long.parseLong(quizIdStr);
                if (question == null || question.trim().isEmpty()) {
                    error = "Question is required.";
                } else {
                    Quiz existingQuiz = qdao.getQuizByQuestion(question);
                    if (existingQuiz != null && existingQuiz.getQuiz_id() != quizId) {
                        error = "Question already exists.";
                    }
                }

                if (error != null) {
                    Quiz quiz = new Quiz();
                    quiz.setQuiz_id(quizId);
                    quiz.setQuestion(question);
                    quiz.setAnswer_options(answerOptions);
                    quiz.setCorrect_answer(correctAnswer);
                    request.setAttribute("quiz", quiz);
                    request.setAttribute("error", error);
                    request.setAttribute("topicId", topicId);
                    request.setAttribute("courseId", courseId);
                    request.setAttribute("lessonId", lessonId);
                    request.getRequestDispatcher("updatequiz.jsp").forward(request, response);
                    return;
                }

                Quiz quiz = new Quiz();
                quiz.setQuiz_id(quizId);
                quiz.setQuestion(question);
                quiz.setAnswer_options(answerOptions);
                quiz.setCorrect_answer(correctAnswer);

                boolean success = qdao.updateQuiz(quiz);
                request.getSession().setAttribute("message", success ? "Quiz updated successfully" : "Failed to update quiz");
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid quiz ID");
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("manageQuiz.jsp").forward(request, response);
                return;
            }
        }

        String redirectUrl = "manageQuiz";
        if (topicId != null && courseId != null && lessonId != null) {
            redirectUrl += "?topicId=" + topicId + "&courseId=" + courseId + "&lessonId=" + lessonId;
        }
        response.sendRedirect(redirectUrl);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Manages quiz-related operations for the admin dashboard";
    }
}