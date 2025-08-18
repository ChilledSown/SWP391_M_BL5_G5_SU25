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
        maxFileSize = 2 * 1024 * 1024, // 2MB
        maxRequestSize = 5 * 1024 * 1024 // 5MB
)
@WebServlet(name = "UpdateCourseServlet", urlPatterns = {"/updateCourse"})
public class UpdateCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

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

        //  Lấy thông tin thumbnail từ form
        String thumbnailUrl = request.getParameter("thumbnail_url"); // "null", "file", hoặc URL
        Part filePart = request.getPart("thumbnail");

        if ("null".equals(thumbnailUrl)) {
            // ❌ User xoá ảnh
            thumbnailUrl = null;

        } else if (thumbnailUrl != null && thumbnailUrl.startsWith("http")) {
            // ✅ User nhập URL → dùng luôn, KHÔNG xử lý filePart nữa

        } else if ("file".equals(thumbnailUrl) || (thumbnailUrl == null && filePart != null && filePart.getSize() > 0)) {
            // ✅ Người dùng chọn file ảnh từ máy

            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();

            // Validate
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

            // Lưu ảnh
            String uploadPath = getServletContext().getRealPath("/") + "assets/img/uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String savedFileName = System.currentTimeMillis() + "_" + fileName;
            filePart.write(uploadPath + File.separator + savedFileName);
            thumbnailUrl = "assets/img/uploads/" + savedFileName;
        } // Nếu thumbnailUrl vẫn không được gán → giữ nguyên ảnh cũ (nếu cần)
        else {
            // Nếu không nhập gì và không có file, giữ nguyên ảnh cũ (DAO sẽ xử lý nếu cần)
            // Có thể để nguyên thumbnailUrl như ban đầu gửi về form
        }

        CourseDAO dao = new CourseDAO();
        dao.updateCourse(courseId, title, description, price, thumbnailUrl, topicId);

        response.sendRedirect("listCousera");
    }

    @Override
    public String getServletInfo() {
        return "Handles course update including optional thumbnail update";
    }
}
