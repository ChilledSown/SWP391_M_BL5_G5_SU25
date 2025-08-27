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
        String searchQuery = request.getParameter("search");
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Invalid page number.");
            }
        }
        int pageSize = 6;
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogs;
        int totalBlogs;
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            blogs = blogDAO.getBlogsByTitle(searchQuery, page, pageSize);
            totalBlogs = blogDAO.getTotalBlogsByTitle(searchQuery);
        } else {
            blogs = blogDAO.getBlogs(page, pageSize, null);
            totalBlogs = blogDAO.getTotalBlogs(null);
        }
        String ajax = request.getParameter("ajax");
        if ("true".equals(ajax)) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            StringBuilder json = new StringBuilder("[");
            for (int i = 0; i < blogs.size(); i++) {
                Blog blog = blogs.get(i);
                json.append("{")
                    .append("\"blogId\":").append(blog.getBlogId()).append(",")
                    .append("\"title\":\"").append(escapeJson(blog.getTitle())).append("\",")
                    .append("\"content\":\"").append(escapeJson(blog.getContent() != null ? blog.getContent() : "")).append("\",")
                    .append("\"thumbnailUrl\":\"").append(escapeJson(blog.getThumbnailUrl() != null ? blog.getThumbnailUrl() : "")).append("\",")
                    .append("\"createdAt\":\"").append(blog.getCreatedAt() != null ? blog.getCreatedAt().toString() : "").append("\",")
                    .append("\"createdByName\":\"").append(escapeJson(blog.getCreatedByName() != null ? blog.getCreatedByName() : "")).append("\"")
                    .append("}");
                if (i < blogs.size() - 1) {
                    json.append(",");
                }
            }
            json.append("]");
            response.getWriter().write(json.toString());
        } else {
            int totalPages = (int) Math.ceil((double) totalBlogs / pageSize);
            request.setAttribute("blogs", blogs);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery != null ? searchQuery : "");
            request.getRequestDispatcher("blog.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "POST method is not supported.");
        request.getRequestDispatcher("blog.jsp").forward(request, response);
    }

    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}