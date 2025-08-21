package controller;

import dal.CourseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.User;

@WebServlet(name = "PurchasedCoursesServlet", urlPatterns = {"/purchased-courses"})
public class PurchasedCoursesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) { response.sendRedirect("login.jsp"); return; }
        User user = (User) session.getAttribute("user");
        if (user == null) { response.sendRedirect("login.jsp"); return; }

        List<Course> courses = new ArrayList<>();
        try {
            CourseDAO courseDAO = new CourseDAO();
            courses = courseDAO.getPurchasedCoursesByUser(user.getUser_id());
        } catch (Exception e) { e.printStackTrace(); }

        request.setAttribute("courses", courses);
        request.getRequestDispatcher("purchased-courses.jsp").forward(request, response);
    }
}



