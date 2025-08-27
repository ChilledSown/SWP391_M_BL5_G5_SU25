
package controller;

import dal.BlogDAO;
import model.Blog;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

@WebServlet(name = "EditBlogServletSeller", urlPatterns = {"/editBlog"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditBlogServletSeller extends HttpServlet {
    private static final String UPLOAD_DIR = "assets/img/uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
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
                    request.setAttribute("action", "update");
                    request.getRequestDispatcher("Add_EditSeller.jsp").forward(request, response);
                } else {
                    session.setAttribute("errorMessage", "Blog not found or you do not have permission to edit it.");
                    response.sendRedirect("listBlogsInstructor");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("errorMessage", "Invalid blog ID.");
                response.sendRedirect("listBlogsInstructor");
            }
        } else {
            response.sendRedirect("listBlogsInstructor");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"instructor".equalsIgnoreCase(user.getRole()) || user.getUser_id() == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get form parameters
        String blogId = request.getParameter("blogId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String existingThumbnail = request.getParameter("existingThumbnail");
        Part filePart = request.getPart("thumbnail");

        // Validation
        boolean hasError = false;
        if (title == null || title.trim().isEmpty()) {
            session.setAttribute("titleError", "Title is required.");
            hasError = true;
        }
        if (content == null || content.trim().isEmpty()) {
            session.setAttribute("contentError", "Content is required.");
            hasError = true;
        }

        // Fetch existing blog
        BlogDAO blogDAO = new BlogDAO();
        Blog existingBlog = null;
        try {
            existingBlog = blogDAO.getBlogById(Long.parseLong(blogId));
            if (existingBlog == null || existingBlog.getCreatedBy() != user.getUser_id().intValue()) {
                session.setAttribute("errorMessage", "Blog not found or you do not have permission to edit it.");
                hasError = true;
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid blog ID.");
            hasError = true;
        }

        // Handle thumbnail logic
        String finalThumbnailUrl = existingThumbnail;
        if (filePart != null && filePart.getSize() > 0) {
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
            filePart.write(uploadPath + File.separator + fileName);
            finalThumbnailUrl = UPLOAD_DIR + "/" + fileName;
        }

        // Check for duplicate title
        if (title != null && !title.trim().isEmpty() && blogDAO.isBlogTitleExists(title, Long.parseLong(blogId))) {
            session.setAttribute("duplicateMessage", "Blog title already exists.");
            hasError = true;
        }

        if (hasError) {
            // Preserve form data
            session.setAttribute("title", title);
            session.setAttribute("content", content);
            session.setAttribute("existingThumbnail", finalThumbnailUrl);
            response.sendRedirect("editBlog?blogId=" + blogId);
            return;
        }

        // Update blog
        Blog blog = new Blog(Long.parseLong(blogId), title, content, finalThumbnailUrl,
                existingBlog.getCreatedAt(), new Timestamp(System.currentTimeMillis()),
                user.getUser_id().intValue(), existingBlog.getCreatedByName());
        blogDAO.updateBlog(blog);

        // Clear session attributes
        session.removeAttribute("titleError");
        session.removeAttribute("contentError");
        session.removeAttribute("thumbnailError");
        session.removeAttribute("duplicateMessage");
        session.removeAttribute("errorMessage");
        session.removeAttribute("title");
        session.removeAttribute("content");
        session.removeAttribute("existingThumbnail");

        response.sendRedirect("listBlogsInstructor");
    }
}
