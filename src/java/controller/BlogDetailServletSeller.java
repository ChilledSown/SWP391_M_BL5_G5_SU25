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

@WebServlet(name = "BlogDetailServlet", urlPatterns = {"/blogDetail"})
public class BlogDetailServletSeller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"seller".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String blogId = request.getParameter("blogId");
        if (blogId != null && !blogId.isEmpty()) {
            try {
                BlogDAO blogDAO = new BlogDAO();
                Blog blog = blogDAO.getBlogById(Long.parseLong(blogId));
                if (blog != null && blog.getCreatedBy() == user.getUser_id().intValue()) {
                    request.setAttribute("blog", blog);
                    request.getRequestDispatcher("BlogDetailSeller.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Blog not found or you do not have permission to view it.");
                    request.getRequestDispatcher("BlogDetailSeller.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid blog ID.");
                request.getRequestDispatcher("BlogDetailSeller.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Blog ID is missing.");
            request.getRequestDispatcher("BlogDetailSeller.jsp").forward(request, response);
        }
    }
}