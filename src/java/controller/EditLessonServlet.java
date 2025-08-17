/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbproject/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LessonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lesson;

/**
 *
 * @author Admin
 */
@WebServlet(name = "EditLessonServlet", urlPatterns = {"/editLesson"})
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
            // Lấy tham số từ form
            Long lessonId = Long.parseLong(request.getParameter("lessonId"));
            Long courseId = Long.parseLong(request.getParameter("courseId"));
            String title = request.getParameter("title");
            String videoUrl = request.getParameter("videoUrl");
            String content = request.getParameter("content");

            // Tạo đối tượng Lesson và thiết lập giá trị bằng setter
            Lesson lesson = new Lesson();
            lesson.setLessonId(lessonId);
            lesson.setCourseId(courseId);
            lesson.setTitle(title);
            lesson.setVideoUrl(videoUrl);
            lesson.setContent(content);

            // Cập nhật bài học trong cơ sở dữ liệu
            LessonDAO dao = new LessonDAO();
            dao.updateLesson(lesson);

            // Không chuyển hướng vì iframe trong lesson_form.jsp xử lý việc tải lại trang mẹ
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid lesson ID or course ID format.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the lesson.");
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