/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LessonDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import model.Lesson;

/**
 *
 * @author Admin
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 500L * 1024 * 1024, // 500MB
        maxRequestSize = 600L * 1024 * 1024 // Tổng request
)
@WebServlet(name = "AddLessonServlet", urlPatterns = {"/addLesson"})
public class AddLessonServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddLessonServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddLessonServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String videoUrl = request.getParameter("videoUrl");
        String content = request.getParameter("content");
        long courseId = Long.parseLong(request.getParameter("courseId"));

        Part videoFilePart = request.getPart("videoFile");
        String finalVideoUrl = null;

        if (videoUrl != null && !videoUrl.trim().isEmpty()) {
            finalVideoUrl = videoUrl.trim(); // Ưu tiên dùng URL nếu có

        } else if (videoFilePart != null && videoFilePart.getSize() > 0) {
            String submittedFileName = Paths.get(videoFilePart.getSubmittedFileName()).getFileName().toString();
            String contentType = videoFilePart.getContentType();

            if (!contentType.equals("video/mp4")) {
                response.getWriter().write("Invalid format. Only .mp4 allowed");
                return;
            }
            if (videoFilePart.getSize() > 500L * 1024 * 1024) {
                response.getWriter().write("Video too large. Max 500MB allowed");
                return;
            }

            String uploadPath = getServletContext().getRealPath("/") + "assets/video/course";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String savedFileName = System.currentTimeMillis() + "_" + submittedFileName;
            videoFilePart.write(uploadPath + File.separator + savedFileName);

            finalVideoUrl = "assets/video/course/" + savedFileName;
        }

        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setVideoUrl(finalVideoUrl);
        lesson.setContent(content);
        lesson.setCourseId(courseId);
        lesson.setCreatedAt(new Date());

        new LessonDAO().insertLesson(lesson);
        response.getWriter().write("success");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
