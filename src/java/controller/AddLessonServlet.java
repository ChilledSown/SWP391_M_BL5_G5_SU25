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
import model.Lesson;

@WebServlet(name = "AddLessonServlet", urlPatterns = {"/addLesson"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 100 * 1024 * 1024,
        maxRequestSize = 150 * 1024 * 1024
)
public class AddLessonServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String courseIdParam = request.getParameter("courseId");
        Part videoFilePart = request.getPart("videoFile");

        if (courseIdParam == null || courseIdParam.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Course ID is required.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if (!courseIdParam.matches("\\d+")) {
            request.setAttribute("errorMessage", "Course ID must be a numeric value.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Title is required.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if (videoFilePart == null || videoFilePart.getSize() == 0) {
            request.setAttribute("errorMessage", "Video file is required.");
            request.setAttribute("title", title);
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
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        LessonDAO dao = new LessonDAO();
        if (dao.isTitleDuplicate(title.trim(), courseId)) {
            request.setAttribute("errorMessage", "A lesson with this title already exists for the course.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        String fileName = Paths.get(videoFilePart.getSubmittedFileName()).getFileName().toString();
        String contentType = videoFilePart.getContentType();
        long fileSize = videoFilePart.getSize();

        if (!(contentType.equals("video/mp4") || contentType.equals("video/webm") || contentType.equals("video/ogg"))) {
            request.setAttribute("errorMessage", "Only MP4, WebM, or OGG video files are allowed.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        if (fileSize > 100 * 1024 * 1024) {
            request.setAttribute("errorMessage", "Video file size exceeds 100MB limit.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        String uploadPath = getServletContext().getRealPath("/") + "assets/video/course";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String savedFileName = System.currentTimeMillis() + "_" + fileName;
        try {
            videoFilePart.write(uploadPath + File.separator + savedFileName);
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error uploading video file.");
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }
        String finalVideoUrl = "assets/video/course/" + savedFileName;

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
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        response.setContentType("text/plain");
        response.getWriter().write("success");
    }

    @Override
    public String getServletInfo() {
        return "Handles creation of new lessons with server-side validation and video file upload";
    }
}