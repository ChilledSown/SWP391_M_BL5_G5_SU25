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
import jakarta.servlet.http.Part;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 2 * 1024 * 1024,   // 2MB
    maxRequestSize = 5 * 1024 * 1024 // 5MB
)
@WebServlet(name = "UpdateCourseServlet", urlPatterns = {"/updateCourse"})
public class UpdateCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 🆔 courseId từ hidden input
        String rawId = request.getParameter("courseId");
        if (rawId == null || rawId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing courseId");
            return;
        }
        long courseId = Long.parseLong(rawId);

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        long topicId = Long.parseLong(request.getParameter("topic_id"));

        // 🔽 Nếu người dùng không chọn ảnh mới thì giữ nguyên ảnh cũ
        String thumbnailUrl = request.getParameter("thumbnail_url");

        Part filePart = request.getPart("thumbnail");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();

            // ✅ Validate ảnh
            if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
                request.getSession().setAttribute("error", "Chỉ cho phép JPG, PNG, GIF.");
                response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
                return;
            }
            if (fileSize > 2 * 1024 * 1024) {
                request.getSession().setAttribute("error", "Ảnh quá lớn, giới hạn là 2MB.");
                response.sendRedirect("blog_course_form.jsp?type=course&action=update&courseId=" + courseId);
                return;
            }

            // ✅ Lưu file vào thư mục uploads
            String uploadPath = getServletContext().getRealPath("/") + "assets/img/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // ✅ Gán lại đường dẫn mới
            thumbnailUrl = "assets/img/uploads/" + fileName;
        }

        // ✅ Cập nhật DB
        CourseDAO dao = new CourseDAO();
        dao.updateCourse(courseId, title, description, price, thumbnailUrl, topicId);

        response.sendRedirect("listCousera");
    }

    @Override
    public String getServletInfo() {
        return "Handles update course logic including thumbnail upload";
    }
}
