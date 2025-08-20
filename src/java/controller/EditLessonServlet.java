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
import java.util.logging.Logger;
import java.util.logging.Level;
import model.Lesson;
import java.sql.SQLException;


@WebServlet(name = "EditLessonServlet", urlPatterns = {"/editLesson"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 100 * 1024 * 1024, // 100MB
        maxRequestSize = 150 * 1024 * 1024 // 150MB
)
public class EditLessonServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EditLessonServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Received GET request for editLesson with lessonId={0}, courseId={1}",
                new Object[]{request.getParameter("lessonId"), request.getParameter("courseId")});

        String lessonIdStr = request.getParameter("lessonId");
        String courseIdStr = request.getParameter("courseId");
        if (lessonIdStr == null || lessonIdStr.isEmpty() || courseIdStr == null || courseIdStr.isEmpty()) {
            String error = "Lesson ID and Course ID are required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        Long lessonId, courseId;
        try {
            lessonId = Long.parseLong(lessonIdStr);
            courseId = Long.parseLong(courseIdStr);
        } catch (NumberFormatException e) {
            String error = "Invalid lesson ID or course ID format: " + e.getMessage();
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        LessonDAO dao = new LessonDAO();
        Lesson lesson = dao.getLessonById(lessonId);
        if (lesson == null) {
            String error = "Lesson not found for ID: " + lessonId;
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }
        if (!lesson.getCourseId().equals(courseId)) {
            String error = "Course ID does not match the lesson's course.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }
        LOGGER.log(Level.INFO, "Successfully loaded lesson ID={0} for editing", lessonId);
        request.setAttribute("lesson", lesson);
        request.setAttribute("courseId", courseId);
        request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Received POST request for editLesson with lessonId={0}, courseId={1}",
                new Object[]{request.getParameter("lessonId"), request.getParameter("courseId")});

        request.setCharacterEncoding("UTF-8");
        String lessonIdStr = request.getParameter("lessonId");
        String courseIdStr = request.getParameter("courseId");
        String title = request.getParameter("title");
        String videoUrl = request.getParameter("videoUrl");
        String content = request.getParameter("content");
        Part videoFilePart = null;
        try {
            videoFilePart = request.getPart("videoFile");
        } catch (IOException | ServletException e) {
            String error = "Error accessing video file: " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        // Validate inputs
        if (lessonIdStr == null || lessonIdStr.trim().isEmpty() || courseIdStr == null || courseIdStr.trim().isEmpty()) {
            String error = "Lesson ID and Course ID are required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }
        if (title == null || title.trim().isEmpty()) {
            String error = "Title is required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }
        if ((videoUrl == null || videoUrl.trim().isEmpty()) && (videoFilePart == null || videoFilePart.getSize() == 0)) {
            String error = "Either Video URL or Video File is required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }
        if (videoUrl != null && !videoUrl.trim().isEmpty() && !videoUrl.matches("^(https?://).+")) {
            String error = "Video URL must be a valid URL (e.g., start with http:// or https://).";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        Long lessonId, courseId;
        try {
            lessonId = Long.parseLong(lessonIdStr);
            courseId = Long.parseLong(courseIdStr);
        } catch (NumberFormatException e) {
            String error = "Invalid lesson ID or course ID format: " + e.getMessage();
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        LessonDAO dao = new LessonDAO();
        // Validate lesson existence and course ID match
        Lesson existingLesson = dao.getLessonById(lessonId);
        if (existingLesson == null) {
            String error = "Lesson not found for ID: " + lessonId;
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }
        if (!existingLesson.getCourseId().equals(courseId)) {
            String error = "Course ID does not match the lesson's course.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        // Validate duplicate title
        if (dao.isTitleDuplicateForUpdate(title.trim(), courseId, lessonId)) {
            String error = "A lesson with this title already exists for the course.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("videoUrl", videoUrl);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdStr);
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
            return;
        }

        // Handle video file upload
        String finalVideoUrl = videoUrl != null ? videoUrl.trim() : "";
        if (videoFilePart != null && videoFilePart.getSize() > 0) {
            String fileName = Paths.get(videoFilePart.getSubmittedFileName()).getFileName().toString();
            String contentType = videoFilePart.getContentType();
            long fileSize = videoFilePart.getSize();

            LOGGER.log(Level.INFO, "Processing video file upload: name={0}, size={1}, type={2}",
                    new Object[]{fileName, fileSize, contentType});

            // Validate file
            if (!(contentType.equals("video/mp4") || contentType.equals("video/webm") || contentType.equals("video/ogg"))) {
                String error = "Only MP4, WebM, or OGG video files are allowed.";
                LOGGER.log(Level.WARNING, error);
                request.setAttribute("errorMessage", error);
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdStr);
                request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
                return;
            }
            if (fileSize > 100 * 1024 * 1024) {
                String error = "Video file size exceeds 100MB limit.";
                LOGGER.log(Level.WARNING, error);
                request.setAttribute("errorMessage", error);
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdStr);
                request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
                return;
            }

            // Save file
            String uploadPath = getServletContext().getRealPath("/") + "assets/videos/uploads";
            LOGGER.log(Level.INFO, "Resolved upload path: {0}", uploadPath);
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                try {
                    uploadDir.mkdirs();
                    LOGGER.log(Level.INFO, "Created upload directory: {0}", uploadPath);
                } catch (SecurityException e) {
                    String error = "Cannot create upload directory: " + e.getMessage();
                    LOGGER.log(Level.SEVERE, error, e);
                    request.setAttribute("errorMessage", error);
                    request.setAttribute("title", title);
                    request.setAttribute("videoUrl", videoUrl);
                    request.setAttribute("content", content);
                    request.setAttribute("courseId", courseIdStr);
                    request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
                    return;
                }
            }
            if (!uploadDir.canWrite()) {
                String error = "No write permission for upload directory: " + uploadPath;
                LOGGER.log(Level.SEVERE, error);
                request.setAttribute("errorMessage", error);
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdStr);
                request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
                return;
            }
            String savedFileName = System.currentTimeMillis() + "_" + fileName;
            try {
                videoFilePart.write(uploadPath + File.separator + savedFileName);
                finalVideoUrl = "assets/videos/uploads/" + savedFileName;
                LOGGER.log(Level.INFO, "Successfully saved video file: {0}", savedFileName);
            } catch (IOException e) {
                String error = "Error uploading video file: " + e.getMessage();
                LOGGER.log(Level.SEVERE, error, e);
                request.setAttribute("errorMessage", error);
                request.setAttribute("title", title);
                request.setAttribute("videoUrl", videoUrl);
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdStr);
                request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
                return;
            }
        }

        // Update lesson
        Lesson lesson = new Lesson();
        lesson.setLessonId(lessonId);
        lesson.setCourseId(courseId);
        lesson.setTitle(title.trim());
        lesson.setVideoUrl(finalVideoUrl);
        lesson.setContent(content != null ? content.trim() : "");
        lesson.setUpdatedAt(new Date());

        try {
            dao.updateLesson(lesson);
        } catch (Exception ex) {
            Logger.getLogger(EditLessonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOGGER.log(Level.INFO, "Successfully updated lesson ID={0}", lessonId);
        // Trả về thành công
        response.setContentType("text/plain");
        response.getWriter().write("success");
        LOGGER.log(Level.INFO, "Sent success response for lesson ID={0}", lessonId);

    }

    @Override
    public String getServletInfo() {
        return "Handles editing of lessons with server-side validation and video file upload";
    }
}
