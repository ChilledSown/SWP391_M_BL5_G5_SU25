package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.AdminListDAO;
import model.Lesson;
import model.Quiz;
import java.util.List;

@WebServlet(name = "AdminQuizServlet", urlPatterns = {"/adminquiz"})
public class AdminQuizServlet extends HttpServlet {

    private static final int PAGE_SIZE = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AdminListDAO dao = new AdminListDAO();

        // Retrieve and validate parameters
        String lessonIdStr = request.getParameter("lessonId");
        String courseIdStr = request.getParameter("courseId");
        String topicIdStr = request.getParameter("topicId");
        String action = request.getParameter("action");

        Long lessonId;
        try {
            lessonId = Long.parseLong(lessonIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            request.setAttribute("error", "Invalid lesson ID");
            request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
            return;
        }

        Long courseId;
        try {
            courseId = Long.parseLong(courseIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            request.setAttribute("error", "Invalid course ID");
            request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
            return;
        }

        Long topicId;
        try {
            topicId = Long.parseLong(topicIdStr);
        } catch (NumberFormatException | NullPointerException e) {
            request.setAttribute("error", "Invalid topic ID");
            request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
            return;
        }

        // Fetch the selected lesson
        Lesson selectedLesson = dao.getLessonById(lessonId);
        if (selectedLesson == null) {
            request.setAttribute("error", "Lesson not found");
            request.getRequestDispatcher("adminlesson.jsp").forward(request, response);
            return;
        }

        // Handle "view" action for quiz details
        if ("view".equals(action)) {
            try {
                long quizId = Long.parseLong(request.getParameter("quizId"));
                Quiz quiz = dao.getQuizById(quizId);
                if (quiz == null) {
                    request.setAttribute("error", "Quiz not found");
                    request.getRequestDispatcher("adminquiz.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("quiz", quiz);
                request.setAttribute("lessonId", lessonId);
                request.setAttribute("courseId", courseId);
                request.setAttribute("topicId", topicId);
                request.getRequestDispatcher("adminquizdetail.jsp").forward(request, response);
                return;
            } catch (NumberFormatException | NullPointerException e) {
                request.setAttribute("error", "Invalid quiz ID");
                request.getRequestDispatcher("adminquiz.jsp").forward(request, response);
                return;
            }
        }

        // Handle pagination
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                // Default to page 1
            }
        }

        // Handle search query
        String searchQuery = request.getParameter("query");
        List<Quiz> quizzes;
        int totalQuizzes;
        try {
            quizzes = dao.getQuizzesWithPagination(searchQuery, page, PAGE_SIZE, lessonId);
            totalQuizzes = dao.getTotalQuizzes(searchQuery, lessonId);
            if (quizzes.isEmpty() && totalQuizzes == 0 && (searchQuery == null || searchQuery.trim().isEmpty())) {
                request.setAttribute("error", "No quizzes available for this lesson.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "Error fetching quizzes: " + e.getMessage());
            request.getRequestDispatcher("adminquiz.jsp").forward(request, response);
            return;
        }

        int totalPages = (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1; // Ensure at least one page

        // Set attributes for JSP
        request.setAttribute("selectedLesson", selectedLesson);
        request.setAttribute("quizzes", quizzes);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchQuery", searchQuery != null ? searchQuery : "");
        request.setAttribute("courseId", courseId);
        request.setAttribute("topicId", topicId);

        // Handle session message
        String message = (String) request.getSession().getAttribute("message");
        if (message != null) {
            request.setAttribute("message", message);
            request.getSession().removeAttribute("message");
        }

        request.getRequestDispatcher("adminquiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Delegate to doGet for simplicity
    }
}