
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

@WebServlet(name = "CreateBlogServletSeller", urlPatterns = {"/createBlog"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class CreateBlogServletSeller extends HttpServlet {
    private static final String UPLOAD_DIR = "assets/img/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"instructor".equalsIgnoreCase(user.getRole()) || user.getUser_id() == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        Part filePart = request.getPart("thumbnail");

        boolean hasError = false;

        // Validation
        if (title == null || title.trim().isEmpty()) {
            session.setAttribute("titleError", "Title is required.");
            hasError = true;
        }
        if (content == null || content.trim().isEmpty()) {
            session.setAttribute("contentError", "Content is required.");
            hasError = true;
        }
        if (filePart == null || filePart.getSize() == 0) {
            session.setAttribute("thumbnailError", "Thumbnail file is required.");
            hasError = true;
        }
        BlogDAO blogDAO = new BlogDAO();
        if (title != null && !title.trim().isEmpty() && blogDAO.isBlogTitleExists(title, null)) {
            session.setAttribute("duplicateMessage", "Blog title already exists.");
            hasError = true;
        }

        if (hasError) {
            // Preserve form data
            session.setAttribute("title", title);
            session.setAttribute("content", content);
            response.sendRedirect("createBlog");
            return;
        }

        // Handle file upload
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        filePart.write(uploadPath + File.separator + fileName);
        String finalThumbnailUrl = UPLOAD_DIR + "/" + fileName;

        // Create blog
        Blog blog = new Blog(0, title, content, finalThumbnailUrl, new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), user.getUser_id().intValue(), null);
        blogDAO.createBlog(blog);

        // Clear session attributes
        session.removeAttribute("titleError");
        session.removeAttribute("contentError");
        session.removeAttribute("thumbnailError");
        session.removeAttribute("duplicateMessage");
        session.removeAttribute("title");
        session.removeAttribute("content");

        response.sendRedirect("listBlogsInstructor");
    }
}
