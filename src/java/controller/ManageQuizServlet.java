package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.QuizDAO;
import model.Quiz;
import java.util.List;

@WebServlet(name = "ManageQuizServlet", urlPatterns = {"/managequiz"})
public class ManageQuizServlet extends HttpServlet {
    private final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO qdao = new QuizDAO();

        String action = request.getParameter("action");
        String lessonId = request.getParameter("lessonId");
        String topicId = request.getParameter("topicId");
        String courseId = request.getParameter("courseId");

        if ("edit".equals(action)) {
            try {
                long quizId = Long.parseLong(request.getParameter("quizId"));
                Quiz quiz = qdao.getQuizById(quizId);
                if (quiz == null) {
                    request.setAttribute("error", "Quiz not found");
                    request.setAttribute("topicId", topicId);
                    request.setAttribute("courseId", courseId);
                    request.setAttribute("lessonId", lessonId);
                    request.getRequestDispatcher("managequiz.jsp").forward(request, response);
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
                request.getRequestDispatcher("managequiz.jsp").forward(request, response);
            }
            return;
        }

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (Exception e) { }

        String searchQuery = request.getParameter("query");
        List<Quiz> quizzes;
        int totalQuizzes;
        if (lessonId != null && !lessonId.trim().isEmpty()) {
            try {
                long lessonIdLong = Long.parseLong(lessonId);
                quizzes = qdao.getQuizzesByLessonId(lessonIdLong);
                totalQuizzes = quizzes.size();
                int totalPages = (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);
                int start = (page - 1) * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, quizzes.size());
                if (start < quizzes.size()) {
                    quizzes = quizzes.subList(start, end);
                } else {
                    quizzes = List.of();
                    if (totalQuizzes > 0 && page > 1) {
                        request.setAttribute("error", "Page out of range for lesson ID: " + lessonId);
                        page = 1;
                        quizzes = qdao.getQuizzesByLessonId(lessonIdLong).subList(0, Math.min(PAGE_SIZE, totalQuizzes));
                    }
                }
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid lesson ID: " + lessonId);
                request.setAttribute("topicId", topicId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("lessonId", lessonId);
                request.getRequestDispatcher("managequiz.jsp").forward(request, response);
                return;
            }
        } else if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            quizzes = qdao.getQuizzesWithPagination(searchQuery, page, PAGE_SIZE);
            totalQuizzes = qdao.getTotalQuizzes(searchQuery);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil((double) totalQuizzes / PAGE_SIZE));
        } else {
            quizzes = qdao.getQuizzesWithPagination(null, page, PAGE_SIZE);
            totalQuizzes = qdao.getTotalQuizzes(null);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", (int) Math.ceil((double) totalQuizzes / PAGE_SIZE));
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
        request.getRequestDispatcher("managequiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        QuizDAO qdao = new QuizDAO();
        String action = request.getParameter("action");
        String topicId = request.getParameter("topicId");
        String courseId = request.getParameter("courseId");
        String lessonId = request.getParameter("lessonId");

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
                request.getRequestDispatcher("managequiz.jsp").forward(request, response);
                return;
            }
        }
        String redirectUrl = "managequiz";
        if (topicId != null && courseId != null && lessonId != null) {
            redirectUrl += "?topicId=" + topicId + "&courseId=" + courseId + "&lessonId=" + lessonId;
        }
        response.sendRedirect(redirectUrl);
    }
}