/*
 * Click nb://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nb://SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.LessonDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

@WebServlet(name = "DeleteLessonServlet", urlPatterns = {"/deleteLesson"})
public class DeleteLessonServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
User user = (User) session.getAttribute("user");
if (user == null || !"instructor".equalsIgnoreCase(user.getRole())) {
    response.sendRedirect("login.jsp");
    return;
}
        
        String lessonIdStr = request.getParameter("lessonId");
        String courseIdStr = request.getParameter("courseId");

       
        if (lessonIdStr == null || lessonIdStr.trim().isEmpty() || courseIdStr == null || courseIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Lesson ID and Course ID are required.");
            request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
            return;
        }

        Long lessonId, courseId;
        try {
            lessonId = Long.parseLong(lessonIdStr);
            courseId = Long.parseLong(courseIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid lesson ID or course ID format.");
            request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
            return;
        }

        LessonDAO lessonDAO = new LessonDAO();
       
        if (lessonDAO.getLessonById(lessonId) == null) {
            request.setAttribute("errorMessage", "Lesson not found for ID: " + lessonId);
            request.getRequestDispatcher("manageLessonInstructor.jsp").forward(request, response);
            return;
        }

      
        lessonDAO.deleteLesson(lessonId);
        response.sendRedirect("manageLessonInstructor?courseId=" + courseId);
    }

    @Override
    public String getServletInfo() {
        return "Handles deletion of lessons with cascading quiz deletion";
    }
}