package controller;

import dal.BlogDAO;
import model.Blog;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "BlogDetailServletSeller", urlPatterns = {"/blogDetailSeller"})
public class BlogDetailServletSeller extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BlogDetailServletSeller.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"instructor".equalsIgnoreCase(user.getRole())) {

            LOGGER.warning("Unauthorized access attempt to /blogDetail");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String blogId = request.getParameter("blogId");
        if (blogId != null && !blogId.isEmpty()) {
            try {
                BlogDAO blogDAO = new BlogDAO();
                Blog blog = blogDAO.getBlogById(Long.parseLong(blogId));
                if (blog != null && blog.getCreatedBy() == user.getUser_id().intValue()) {
                    request.setAttribute("blog", blog);
                } else {
                    request.setAttribute("errorMessage", "Blog not found or you do not have permission to view it.");
                }
            } catch (NumberFormatException e) {
                LOGGER.log(Level.SEVERE, "Invalid blog ID: " + blogId, e);
                request.setAttribute("errorMessage", "Invalid blog ID.");
            }
        } else {
            request.setAttribute("errorMessage", "Blog ID is missing.");
        }

        request.getRequestDispatcher("/BlogDetailSeller.jsp").forward(request, response);
    }
}