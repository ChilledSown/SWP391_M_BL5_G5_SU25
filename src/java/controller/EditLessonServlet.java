/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbproject/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LessonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import model.Lesson;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditLessonServlet", urlPatterns = {"/editLesson"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,     // 1MB
    maxFileSize = 500 * 1024 * 1024,     // 500MB
    maxRequestSize = 550 * 1024 * 1024   // 550MB tổng request
)
public class EditLessonServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method to load lesson data for editing.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy tham số lessonId và courseId từ URL
            String lessonIdStr = request.getParameter("lessonId");
            String courseId = request.getParameter("courseId");

            // Nếu có lessonId, lấy dữ liệu bài học từ cơ sở dữ liệu
            if (lessonIdStr != null && !lessonIdStr.isEmpty()) {
                try {
                    Long lessonId = Long.parseLong(lessonIdStr);
                    LessonDAO dao = new LessonDAO();
                    Lesson lesson = dao.getLessonById(lessonId);
                    if (lesson != null) {
                        request.setAttribute("lesson", lesson);
                    } else {
                        request.setAttribute("error", "Lesson not found for ID: " + lessonId);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid lesson ID format.");
                }
            }

            // Đặt courseId vào request để sử dụng trong form
            request.setAttribute("courseId", courseId);

            // Chuyển tiếp đến lesson_form.jsp
            request.getRequestDispatcher("/lesson_form.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while loading the lesson form.");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method to update lesson data.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            request.setCharacterEncoding("UTF-8");

            Long lessonId = Long.parseLong(request.getParameter("lessonId"));
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            String title = request.getParameter("title");
            String videoUrl = request.getParameter("videoUrl");
            String content = request.getParameter("content");

            // Nếu người dùng xóa video (dấu ❌), trường này sẽ có giá trị "true"
            boolean removeVideo = "true".equals(request.getParameter("removeVideo"));

            Part filePart = request.getPart("videoFile");

            String finalVideoUrl = null;

            if (!removeVideo) {
                if (videoUrl != null && !videoUrl.trim().isEmpty()) {
                    finalVideoUrl = videoUrl.trim();
                } else if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String contentType = filePart.getContentType();
                    long fileSize = filePart.getSize();

                    if (!contentType.equals("video/mp4")) {
                        response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Chỉ cho phép video .mp4");
                        return;
                    }

                    if (fileSize > 500 * 1024 * 1024) {
                        response.sendError(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "Video vượt quá 500MB");
                        return;
                    }

                    String uploadPath = getServletContext().getRealPath("/") + "assets/video/course";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    String filePath = uploadPath + File.separator + fileName;
                    filePart.write(filePath);

                    finalVideoUrl = "assets/video/course/" + fileName;
                }
            }

            Lesson lesson = new Lesson();
            lesson.setLessonId(lessonId);
            lesson.setCourseId(courseId);
            lesson.setTitle(title);
            lesson.setVideoUrl(finalVideoUrl);
            lesson.setContent(content);

            LessonDAO dao = new LessonDAO();
            dao.updateLesson(lesson);

            // Không cần redirect, vì đang dùng iframe (tránh reload toàn trang)

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật bài học");
        }
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for editing lessons and loading lesson form";
    }
}