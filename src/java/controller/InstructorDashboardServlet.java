package controller;

import dal.BalanceDAO;
import dal.ReviewDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InstructorDashboardServlet", urlPatterns = {"/instructor-dashboard"})
public class InstructorDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        model.User user = (model.User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"seller".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        long createdBy = user.getUser_id();
        BalanceDAO balanceDAO = new BalanceDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        // Fetch data (placeholders until CourseDAO and BlogDAO are implemented)
        Double balance = balanceDAO.getBalance((int) createdBy); // Use Double to handle null
        int totalCourses = 0; // Replace with CourseDAO count
        int totalBlogs = 0; // Replace with BlogDAO count
        int performanceViews = 0; // Replace with actual view count
        List<String> recentActivity = new ArrayList<>();
        recentActivity.add("Logged in at " + new java.util.Date());
        // Add more activities as needed

        request.setAttribute("balance", balance);
        request.setAttribute("totalCourses", totalCourses);
        request.setAttribute("totalBlogs", totalBlogs);
        request.setAttribute("performanceViews", performanceViews);
        request.setAttribute("recentActivity", recentActivity);
        request.getRequestDispatcher("Instructor_Doashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}