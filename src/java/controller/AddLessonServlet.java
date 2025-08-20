package controller;

import dal.LessonDAO;
import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.util.Date;
import java.sql.*;
import model.Lesson;

@WebServlet(name = "AddLessonServlet", urlPatterns = {"/addLesson"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 100 * 1024 * 1024, // 100MB
        maxRequestSize = 150 * 1024 * 1024 // 150MB
)
public class AddLessonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String videoUrl = request.getParameter("videoUrl");
        String content = request.getParameter("content");
        String courseIdParam = request.getParameter("courseId");
        Part videoFilePart = request.getPart("videoFile");

        // Validate inputs
        if (courseIdParam == null || courseIdParam.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Course ID is required.");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Title is required.");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if ((videoUrl == null || videoUrl.trim().isEmpty()) && (videoFilePart == null || videoFilePart.getSize() == 0)) {
            request.setAttribute("errorMessage", "Either Video URL or Video File is required.");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if (videoUrl != null && !videoUrl.trim().isEmpty() && !videoUrl.matches("^(https?://).+")) {
            request.setAttribute("errorMessage", "Video URL must be a valid URL (e.g., start with http:// or https://).");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        Long courseId;
        try {
            courseId = Long.parseLong(courseIdParam);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course ID format.");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        // Validate duplicate title
        LessonDAO dao = new LessonDAO();
        if (dao.isTitleDuplicate(title.trim(), courseId)) {
            request.setAttribute("errorMessage", "A lesson with this title already exists for the course.");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        // Handle video file upload
        String finalVideoUrl = videoUrl != null ? videoUrl.trim() : "";
        if (videoFilePart != null && videoFilePart.getSize() > 0) {
            String fileName = Paths.get(videoFilePart.getSubmittedFileName()).getFileName().toString();
            String contentType = videoFilePart.getContentType();
            long fileSize = videoFilePart.getSize();

            // Validate file
            if (!(contentType.equals("video/mp4") || contentType.equals("video/webm") || contentType.equals("video/ogg"))) {
                request.setAttribute("errorMessage", "Only MP4, WebM, or OGG video files are allowed.");
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdParam);
                request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
                return;
            }
            if (fileSize > 100 * 1024 * 1024) {
                request.setAttribute("errorMessage", "Video file size exceeds 100MB limit.");
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdParam);
                request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
                return;
            }

            // Save file
            String uploadPath = getServletContext().getRealPath("/") + "assets/video/course";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String savedFileName = System.currentTimeMillis() + "_" + fileName;
            try {
                videoFilePart.write(uploadPath + File.separator + savedFileName);
                finalVideoUrl = "assets/video/course/" + savedFileName;
            } catch (IOException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error uploading video file.");
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdParam);
                request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
                return;
            }
        }

        // Create lesson
        Lesson lesson = new Lesson();
        lesson.setTitle(title.trim());
        lesson.setVideoUrl(finalVideoUrl);
        lesson.setContent(content != null ? content.trim() : "");
        lesson.setCourseId(courseId);
        lesson.setCreatedAt(new Date());

        long lessonId = dao.insertLesson(lesson);
        if (lessonId == -1) {
            request.setAttribute("errorMessage", "Failed to create lesson due to a database error.");
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        // Respond with success to trigger parent page reload
        response.setContentType("text/plain");
        response.getWriter().write("success");
    }

    @Override
    public String getServletInfo() {
        return "Handles creation of new lessons with server-side validation and video file upload";
    }
}
