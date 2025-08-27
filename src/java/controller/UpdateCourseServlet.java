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
import model.Course; // Đảm bảo import lớp Course
import model.User;

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
        
        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
        request.setCharacterEncoding("UTF-8");
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
        String thumbnailUrlParam = request.getParameter("thumbnail_url"); // Giá trị từ hidden input
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
        // Xử lý thumbnail
        String thumbnailUrl = null;
        CourseDAO dao = new CourseDAO();
        Course existingCourse = dao.getCourseById(courseId);
        if (existingCourse != null) {
            thumbnailUrl = existingCourse.getThumbnail_url(); // Giá trị ảnh cũ làm mặc định
        }
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();
            if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
                session.setAttribute("thumbnailError", "Only JPG, PNG, or GIF files are allowed.");
                hasError = true;
            }
            if (fileSize > 2 * 1024 * 1024) {
                session.setAttribute("thumbnailError", "Image size exceeds the 2MB limit.");
                hasError = true;
            }
            if (!hasError) {
                String uploadPath = getServletContext().getRealPath("/") + "assets/img/uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                String savedFileName = System.currentTimeMillis() + "_" + fileName;
                filePart.write(uploadPath + File.separator + savedFileName);
                thumbnailUrl = "assets/img/uploads/" + savedFileName;
            }
        } else if ("null".equals(thumbnailUrlParam)) {
            thumbnailUrl = null; // Xóa ảnh khi nhấn "X"
        } // Nếu không upload và không nhấn "X", giữ ảnh cũ
        // If there are validation errors, redirect back to form
        if (hasError) {
            response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
            return;
        }
        // Kiểm tra trùng lặp tiêu đề chỉ khi tiêu đề thay đổi
        if (existingCourse != null && !title.equals(existingCourse.getTitle()) && dao.isTitleExistsExceptCurrent(title, courseId)) {
            session.setAttribute("duplicateMessage", "Course title already exists. You may want to use a different title.");
            response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
            return;
        }
        // Cập nhật DB
        dao.updateCourse(courseId, title, description, Integer.parseInt(priceStr), thumbnailUrl, Long.parseLong(topicIdStr));
        response.sendRedirect("listCourses");
    }

    @Override
    public String getServletInfo() {
        return "Handles update course logic including thumbnail upload";
    }
}