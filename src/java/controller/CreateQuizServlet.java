package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.QuizDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import model.Quiz;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CreateQuizServlet", urlPatterns = {"/createQuiz"})
public class CreateQuizServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateQuizServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateQuizServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        try {
            String question = request.getParameter("question");
            String answerOptionsRaw = request.getParameter("answerOptions");
            String correctAnswer = request.getParameter("correctAnswer");
            String explanation = request.getParameter("explanation");
            Long lessonId = Long.parseLong(request.getParameter("lessonId"));

            // Gộp các dòng thành một chuỗi duy nhất phân cách bằng ;
            String answerOptions = answerOptionsRaw.trim().replaceAll("\r\n", "; ").replaceAll("\n", "; ");

            Quiz quiz = new Quiz();
            quiz.setQuestion(question);
            quiz.setAnswerOptions(answerOptions);
            quiz.setCorrectAnswer(correctAnswer);
            quiz.setExplanation(explanation);
            quiz.setLessonId(lessonId);
            quiz.setCreatedAt(new Date());
            quiz.setUpdatedAt(new Date());

            QuizDAO dao = new QuizDAO();
            dao.createQuiz(quiz);

            response.sendRedirect("manageQuiz?lessonId=" + lessonId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error creating quiz.");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
