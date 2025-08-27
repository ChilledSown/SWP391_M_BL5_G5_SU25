package controller;

import dal.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Quiz;
import model.User;

@WebServlet(name = "QuizDetailSellerServlet", urlPatterns = {"/quizDetailSeller"})
public class QuizDetailSellerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
        try {
            long quizId = Long.parseLong(request.getParameter("quizId"));
            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            long courseId = Long.parseLong(request.getParameter("courseId")); // Retrieve courseId
            QuizDAO dao = new QuizDAO();
            Quiz quiz = dao.getQuizById(quizId);
            if (quiz == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Quiz not found.");
                return;
            }
            request.setAttribute("quiz", quiz);
            request.setAttribute("lessonId", lessonId);
            request.setAttribute("courseId", courseId); // Set courseId as an attribute
            request.getRequestDispatcher("quiz_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz, lesson, or course ID.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving quiz.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method not supported.");
    }

    @Override
    public String getServletInfo() {
        return "Displays details of a specific quiz";
    }
}