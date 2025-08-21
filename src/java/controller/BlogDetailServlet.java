package controller;

import dal.BlogDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Blog;

@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blog_details"})
public class BlogDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                long blogId = Long.parseLong(idStr);
                BlogDAO blogDAO = new BlogDAO();
                Blog blog = blogDAO.getBlogById(blogId);
                if (blog != null) {
                    request.setAttribute("blog", blog);              
                } else {
                    request.setAttribute("message", "Blog not found for ID: " + blogId);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid blog ID format: " + idStr);
            }
        } else {
            request.setAttribute("message", "Blog ID is required.");
        }
        request.getRequestDispatcher("blog_details.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "POST method is not supported.");
        request.getRequestDispatcher("blog_details.jsp").forward(request, response);
    }
}