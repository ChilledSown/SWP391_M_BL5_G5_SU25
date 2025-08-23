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
import java.time.LocalDate;

@WebServlet(name = "CreateBlogServlet", urlPatterns = {"/createBlog"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class CreateBlogServletSeller extends HttpServlet {

    private static final String UPLOAD_DIR = "assets/img/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // Check if user is logged in and is a seller
        if (user == null || !"seller".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get form parameters
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String thumbnailUrl = request.getParameter("thumbnail_url");
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
        if (thumbnailUrl == null || thumbnailUrl.trim().isEmpty() || thumbnailUrl.equals("null")) {
            if (filePart == null || filePart.getSize() == 0) {
                session.setAttribute("thumbnailError", "Thumbnail URL or file is required.");
                hasError = true;
            }
        }

        // Check for duplicate title
        BlogDAO blogDAO = new BlogDAO();
        if (blogDAO.isBlogTitleExists(title, null)) { // No excludeBlogId for create
            session.setAttribute("duplicateMessage", "Blog title already exists.");
            hasError = true;
        }

        if (hasError) {
            response.sendRedirect("Add_EditSeller.jsp?action=create");
            return;
        }

        // Handle file upload
        String fileName = null;
        if (filePart != null && filePart.getSize() > 0) {
            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
            filePart.write(uploadPath + File.separator + fileName);
            thumbnailUrl = UPLOAD_DIR + "/" + fileName;
        }

        // Create blog
        Blog blog = new Blog(null, title, content, thumbnailUrl, LocalDate.now(), LocalDate.now(), user.getUser_id().intValue()); // Updated constructor
        blogDAO.createBlog(blog);

        // Redirect to blog management
        response.sendRedirect("listBlogsSeller");
    }
}
