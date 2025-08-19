package controller;

import dal.QuizDAO;
import model.Quiz;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "EditQuizServlet", urlPatterns = {"/editQuiz"})
public class EditQuizServlet extends HttpServlet {

    // Xử lý GET: Hiển thị form quiz với dữ liệu từ quizId
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long quizId = Long.parseLong(request.getParameter("quizId"));
            long lessonId = Long.parseLong(request.getParameter("lessonId"));

            QuizDAO dao = new QuizDAO();
            Quiz quiz = dao.getQuizById(quizId); // Bạn sẽ thêm hàm này bên dưới

            request.setAttribute("quiz", quiz);
            request.setAttribute("lessonId", lessonId);
            request.getRequestDispatcher("quiz_form.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid quiz or lesson ID");
        }
    }

    // Xử lý POST: Lưu thay đổi khi submit form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long quizId = Long.parseLong(request.getParameter("quizId"));
            long lessonId = Long.parseLong(request.getParameter("lessonId"));
            String question = request.getParameter("question");
            String answerOptions = request.getParameter("answerOptions").replaceAll("\\r?\\n", ";").trim();
            String correctAnswer = request.getParameter("correctAnswer");
            String explanation = request.getParameter("explanation");

            Quiz quiz = new Quiz();
            quiz.setQuizId(quizId);
            quiz.setLessonId(lessonId);
            quiz.setQuestion(question);
            quiz.setAnswerOptions(answerOptions);
            quiz.setCorrectAnswer(correctAnswer);
            quiz.setExplanation(explanation);
            quiz.setUpdatedAt(new java.util.Date());

            QuizDAO dao = new QuizDAO();
            dao.updateQuiz(quiz);

            response.sendRedirect("manageQuiz?lessonId=" + lessonId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update quiz.");
        }
    }
}
