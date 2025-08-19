/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CourseDAO;
import dal.LessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Lesson;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ManageLessonServlet", urlPatterns = {"/manageLessonSeller"})
public class ManageLessonServlet extends HttpServlet {

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
            out.println("<title>Servlet ManageLessonServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageLessonServlet at " + request.getContextPath() + "</h1>");
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
        long courseId = Long.parseLong(request.getParameter("courseId"));
        String title = request.getParameter("title");
        String createdDate = request.getParameter("createdDate");

        int page = 1;
        int pageSize = 2;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Math.max(1, Integer.parseInt(pageParam));
            } catch (NumberFormatException ignored) {
            }
        }
        int offset = (page - 1) * pageSize;

        LessonDAO dao = new LessonDAO();

        // Lấy dữ liệu phân trang
        List<Lesson> lessons = dao.getFilteredLessonsByCoursePaged(courseId, title, createdDate, offset, pageSize);
        int totalItems = dao.countFilteredLessonsByCourse(courseId, title, createdDate);
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

        // Truyền attribute
        request.setAttribute("lessons", lessons);
        request.setAttribute("courseId", courseId);
        request.setAttribute("title", title);
        request.setAttribute("createdDate", createdDate);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Tạo baseUrl để phân trang giữ lại filter
        StringBuilder baseUrl = new StringBuilder("manageLessonSeller?courseId=" + courseId);
        if (title != null && !title.isEmpty()) {
            baseUrl.append("&title=").append(java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8));
        }
        if (createdDate != null && !createdDate.isEmpty()) {
            baseUrl.append("&createdDate=").append(createdDate);
        }
        request.setAttribute("baseUrl", baseUrl.toString());

        request.getRequestDispatcher("manageLessonSeller.jsp").forward(request, response);
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
        processRequest(request, response);
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