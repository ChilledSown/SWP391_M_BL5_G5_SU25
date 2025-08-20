package controller;

import dal.CourseDAO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 2 * 1024 * 1024, // 2MB
    maxRequestSize = 5 * 1024 * 1024 // 5MB
)
@WebServlet(name = "UpdateCourseServlet", urlPatterns = {"/updateCourse"})
public class UpdateCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // courseId từ hidden input
        String rawId = request.getParameter("courseId");
        long courseId;
        if (rawId == null || rawId.isEmpty()) {
            session.setAttribute("error", "Missing course ID.");
            response.sendRedirect("blog_course_form.jsp?type=course&action=update");
            return;
        }
        try {
            courseId = Long.parseLong(rawId);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Invalid course ID format.");
            response.sendRedirect("blog_course_form.jsp?type=course&action=update");
            return;
        }

        // Lấy thông tin form
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String topicIdStr = request.getParameter("topic_id");
        String thumbnailUrl = request.getParameter("thumbnail_url");
        Part filePart = request.getPart("thumbnail");

        // Validate form fields
        boolean hasError = false;
        if (title == null || title.trim().isEmpty()) {
            session.setAttribute("titleError", "Please enter a course title.");
            hasError = true;
        }
        if (description == null || description.trim().isEmpty()) {
            session.setAttribute("descriptionError", "Please enter a course description.");
            hasError = true;
        }
        if (priceStr == null || priceStr.trim().isEmpty()) {
            session.setAttribute("priceError", "Please enter a course price.");
            hasError = true;
        } else {
            try {
                int price = Integer.parseInt(priceStr);
                if (price < 0) {
                    session.setAttribute("priceError", "Price must be 0 or greater.");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                session.setAttribute("priceError", "Invalid price format. Please enter a valid number.");
                hasError = true;
            }
        }
        if (topicIdStr == null || topicIdStr.trim().isEmpty()) {
            session.setAttribute("topicError", "Please select a topic.");
            hasError = true;
        } else {
            try {
                Long.parseLong(topicIdStr);
            } catch (NumberFormatException e) {
                session.setAttribute("topicError", "Invalid topic ID format.");
                hasError = true;
            }
        }
        if ((thumbnailUrl == null || thumbnailUrl.equals("null")) && (filePart == null || filePart.getSize() == 0)) {
            session.setAttribute("thumbnailError", "Please provide a thumbnail URL or select an image file.");
            hasError = true;
        }

        // If there are validation errors, redirect back to form
        if (hasError) {
            response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
            return;
        }

        // Xử lý ảnh thumbnail
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();

            // Validate ảnh
            if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
                session.setAttribute("thumbnailError", "Only JPG, PNG, or GIF files are allowed.");
                response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
                return;
            }
            if (fileSize > 2 * 1024 * 1024) {
                session.setAttribute("thumbnailError", "Image size exceeds the 2MB limit.");
                response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
                return;
            }

            // Lưu file vào thư mục uploads
            String uploadPath = getServletContext().getRealPath("/") + "assets/img/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Gán lại đường dẫn mới
            thumbnailUrl = "assets/img/uploads/" + fileName;
        }

        // Cập nhật DB
        CourseDAO dao = new CourseDAO();
        dao.updateCourse(courseId, title, description, Integer.parseInt(priceStr), thumbnailUrl, Long.parseLong(topicIdStr));
        response.sendRedirect("listCousera");
    }

    @Override
    public String getServletInfo() {
        return "Handles update course logic including thumbnail upload";
    }
}