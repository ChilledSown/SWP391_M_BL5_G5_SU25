package controller;

import dal.BlogDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;

@WebServlet(name = "BlogServlet", urlPatterns = {"/blog"})
public class BlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs = blogDAO.getAllBlogs();
        request.setAttribute("blogs", blogs);
        request.getRequestDispatcher("blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
