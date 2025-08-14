package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Course;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ListCouseraSeller", urlPatterns = {"/listCousera"})
//@WebServlet(urlPatterns = {"/listCousera"})
public class ListCouseraSeller extends HttpServlet {

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
            out.println("<title>Servlet ListCouseraSeller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCouseraSeller at " + request.getContextPath() + "</h1>");
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
//   @Override
//protected void doGet(HttpServletRequest request, HttpServletResponse response)
//        throws ServletException, IOException {
//
//    HttpSession session = request.getSession();
//    model.User currentUser = (model.User) session.getAttribute("currentUser");
//
    ////    if (currentUser == null || !"seller".equals(currentUser.getRole())) {
////        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập.");
////        return;
////    }
//
//    CourseDAO dao = new CourseDAO();
//    List<Course> courses = dao.getCoursesByCreator(currentUser.getUser_id().intValue());
//
//    request.setAttribute("courses", courses);
//    request.getRequestDispatcher("list_course.jsp").forward(request, response);
//}
// check truoc khi luu sesssion
    
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("userId");
        int userId = 1; // mặc định
        if (idParam != null && !idParam.isEmpty()) {
            try {
                userId = Integer.parseInt(idParam);
            } catch (NumberFormatException ignored) {
            }
        }

        CourseDAO dao = new CourseDAO();
        List<Course> courses = dao.getCoursesByCreator(userId);

        request.setAttribute("courses", courses);
        request.getRequestDispatcher("seller.jsp").forward(request, response);
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
