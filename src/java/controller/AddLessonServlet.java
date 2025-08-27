package controller;

import dal.LessonDAO;
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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Lesson;
import model.User;

@WebServlet(name = "AddLessonServlet", urlPatterns = {"/addLesson"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 100 * 1024 * 1024, // 100MB
        maxRequestSize = 150 * 1024 * 1024 // 150MB
)
public class AddLessonServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddLessonServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.log(Level.INFO, "Received POST request for addLesson with parameters: {0}",
                request.getParameterMap());
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String courseIdParam = request.getParameter("courseId");
        Part videoFilePart = null;

        try {
            videoFilePart = request.getPart("videoFile");
        } catch (IOException | ServletException e) {
            String error = "Error accessing video file: " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        // Validate inputs
        if (courseIdParam == null || courseIdParam.trim().isEmpty()) {
            String error = "Course ID is required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        if (!courseIdParam.matches("\\d+")) {
            String error = "Course ID must be a numeric value.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        if (title == null || title.trim().isEmpty()) {
            String error = "Title is required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        if (videoFilePart == null || videoFilePart.getSize() == 0) {
            String error = "Video file is required.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        Long courseId;
        try {
            courseId = Long.parseLong(courseIdParam.trim());
            LOGGER.log(Level.INFO, "Parsed courseId: {0}", courseId);
        } catch (NumberFormatException e) {
            String error = "Invalid course ID format: " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        LessonDAO dao = new LessonDAO();
        if (dao.isTitleDuplicate(title.trim(), courseId)) {
            String error = "A lesson with this title already exists for the course.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        String fileName = Paths.get(videoFilePart.getSubmittedFileName()).getFileName().toString();
        String contentType = videoFilePart.getContentType();
        long fileSize = videoFilePart.getSize();
        LOGGER.log(Level.INFO, "Processing video file upload: name={0}, size={1}, type={2}",
                new Object[]{fileName, fileSize, contentType});

        if (!(contentType.equals("video/mp4") || contentType.equals("video/webm") || contentType.equals("video/ogg"))) {
            String error = "Only MP4, WebM, or OGG video files are allowed.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        if (fileSize > 100 * 1024 * 1024) {
            String error = "Video file size exceeds 100MB limit.";
            LOGGER.log(Level.WARNING, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        String uploadPath = getServletContext().getRealPath("/") + "assets/video/course";
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
                request.setAttribute("content", content);
                request.setAttribute("courseId", courseIdParam);
                request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
                return;
            }
        }

        if (!uploadDir.canWrite()) {
            String error = "No write permission for upload directory: " + uploadPath;
            LOGGER.log(Level.SEVERE, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        String savedFileName = System.currentTimeMillis() + "_" + fileName;
        try {
            videoFilePart.write(uploadPath + File.separator + savedFileName);
            LOGGER.log(Level.INFO, "Successfully saved video file: {0}", savedFileName);
        } catch (IOException e) {
            String error = "Error uploading video file: " + e.getMessage();
            LOGGER.log(Level.SEVERE, error, e);
            request.setAttribute("errorMessage", error);
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
            String error = "Failed to create lesson due to a database error.";
            LOGGER.log(Level.SEVERE, error);
            request.setAttribute("errorMessage", error);
            request.setAttribute("title", title);
            request.setAttribute("content", content);
            request.setAttribute("courseId", courseIdParam);
            request.getRequestDispatcher("lesson_form.jsp").forward(request, response);
            return;
        }

        LOGGER.log(Level.INFO, "Successfully created lesson ID={0}", lessonId);
        response.setContentType("text/plain");
        response.getWriter().write("success");
        LOGGER.log(Level.INFO, "Sent success response for lesson ID={0}", lessonId);
    }

    @Override
    public String getServletInfo() {
        return "Handles creation of new lessons with server-side validation and video file upload, ensuring proper error reporting.";
    }
}