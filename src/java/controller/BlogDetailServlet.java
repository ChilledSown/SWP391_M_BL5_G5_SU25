package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Blog;

@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blog_details"})
public class BlogDetailServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(BlogDetailServlet.class.getName());

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
                    LOGGER.log(Level.INFO, "Fetched blog with ID: {0}", blogId);
                    request.setAttribute("blog", blog);
                    request.getRequestDispatcher("blog_details.jsp").forward(request, response);
                } else {
                    LOGGER.log(Level.WARNING, "Blog not found for ID: {0}", blogId);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Blog not found");
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Invalid blog ID format: {0}", idStr);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid blog ID");
            }
        } else {
            LOGGER.log(Level.WARNING, "Blog ID is required");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Blog ID is required");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không cần xử lý POST cho trang chi tiết blog
    }
}