package controller;

import dal.OverviewAdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OverviewAdminServlet", urlPatterns = {"/overviewadmin"})
public class OverviewAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OverviewAdminDAO dao = new OverviewAdminDAO();

        // Fetch statistics
        int totalUsers = dao.getTotalUsers();
        int totalCourses = dao.getTotalCourses();
        List<Map<String, Object>> courseEnrollments = dao.getCourseEnrollmentCounts();
        Map<String, Integer> userGrowthData = dao.getUserGrowthData();
        Map<String, Integer> enrollmentGrowthData = dao.getEnrollmentGrowthData();

        // Set attributes for JSP
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalCourses", totalCourses);
        request.setAttribute("courseEnrollments", courseEnrollments);
        request.setAttribute("userGrowthData", userGrowthData);
        request.setAttribute("enrollmentGrowthData", enrollmentGrowthData);

        request.getRequestDispatcher("overviewadmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("overviewadmin");
    }
}