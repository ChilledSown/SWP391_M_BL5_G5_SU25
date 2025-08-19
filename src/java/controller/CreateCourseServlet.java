package controller;

import dal.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Course;
import model.User;

@WebServlet(name = "CreateCourseServlet", urlPatterns = {"/createCourse"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1MB
    maxFileSize = 2 * 1024 * 1024,   // 2MB
    maxRequestSize = 5 * 1024 * 1024 // 5MB
)
public class CreateCourseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // ✅ Lấy user đăng nhập
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ✅ Lấy thông tin form
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        int price = Integer.parseInt(request.getParameter("price"));
        long topicId = Long.parseLong(request.getParameter("topic_id"));

        // ✅ Xử lý ảnh thumbnail upload
        Part filePart = request.getPart("thumbnail");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String contentType = filePart.getContentType();
        long fileSize = filePart.getSize();

        // ✅ Validate ảnh
        if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/gif"))) {
            session.setAttribute("error", "❌ Chỉ cho phép JPG, PNG, GIF.");
            response.sendRedirect("blog_course_form.jsp");
            return;
        }
        if (fileSize > 2 * 1024 * 1024) {
            session.setAttribute("error", "❌ Ảnh quá lớn, giới hạn là 2MB.");
            response.sendRedirect("blog_course_form.jsp");
            return;
        }

        // ✅ Lưu file vào thư mục assets/img/uploads
        String uploadPath = getServletContext().getRealPath("/") + "assets/img/uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        // ✅ Đảm bảo không ghi đè file trùng tên (tự động đổi tên)
        String savedFileName = System.currentTimeMillis() + "_" + fileName;
        filePart.write(uploadPath + File.separator + savedFileName);

        // ✅ Đường dẫn lưu vào DB
        String thumbnailUrl = "assets/img/uploads/" + savedFileName;

        // ✅ Kiểm tra trùng tiêu đề
        CourseDAO dao = new CourseDAO();
        boolean isDuplicate = dao.isTitleExists(title);

        // ✅ Thêm vào DB
        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setPrice(price);
        course.setThumbnail_url(thumbnailUrl);
        course.setCreated_at(new Date());
        course.setTopic_id(topicId);

        dao.insertCourse(course, currentUser.getUser_id());

        // ✅ Nếu tiêu đề trùng, gửi cảnh báo
        if (isDuplicate) {
            session.setAttribute("duplicateMessage", "⚠️ Course title already exists. You may want to use a different title.");
        }

        response.sendRedirect("listCousera");
    }
}
