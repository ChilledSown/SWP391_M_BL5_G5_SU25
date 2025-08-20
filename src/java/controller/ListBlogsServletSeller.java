/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Blog;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ListBlogsServletSeller", urlPatterns = {"/listBlogsSeller"})
public class ListBlogsServletSeller extends HttpServlet {

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
            out.println("<title>Servlet ListBlogsServletSeller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListBlogsServletSeller at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get logged-in seller's ID (assuming stored in session)
        HttpSession session = request.getSession();
        Integer creatorId = (Integer) session.getAttribute("sellerId"); // Replace with actual session attribute
        if (creatorId == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if not authenticated
            return;
        }

        // Get search parameters
        String title = request.getParameter("title");
        String createdDate = request.getParameter("createdDate");

        // Fetch blogs using BlogDAO
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs = blogDAO.getBlogsByCreatorId(creatorId);

        // Filter blogs by title and createdDate (if provided)
        if (title != null && !title.isEmpty()) {
            blogs.removeIf(blog -> !blog.getTitle().toLowerCase().contains(title.toLowerCase()));
        }
        if (createdDate != null && !createdDate.isEmpty()) {
            blogs.removeIf(blog -> !blog.getCreatedAt().toString().equals(createdDate));
        }

        // Store blogs and search parameters
        request.setAttribute("blogs", blogs);
        session.setAttribute("blogTitle", title);
        session.setAttribute("blogCreatedDate", createdDate);

        // Forward to seller_blog.jsp
        request.getRequestDispatcher("seller_blog.jsp").forward(request, response);
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
