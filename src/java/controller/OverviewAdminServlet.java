package controller;

import dal.OverviewAdminDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "OverviewAdminServlet", urlPatterns = {"/overviewadmin"})
public class OverviewAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OverviewAdminDAO dao = new OverviewAdminDAO();

        // Fetch statistics
        int totalUsers = dao.getTotalUsers();
        int totalAdmins = dao.getTotalAdmins();
        int totalInstructors = dao.getTotalInstructors();
        int totalCustomers = dao.getTotalCustomers();
        Map<String, Integer> userGrowthData = dao.getUserGrowthData();

        // Set attributes for JSP
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalAdmins", totalAdmins);
        request.setAttribute("totalInstructors", totalInstructors);
        request.setAttribute("totalCustomers", totalCustomers);
        request.setAttribute("userGrowthData", userGrowthData);

        request.getRequestDispatcher("overviewadmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("overviewadmin");
    }
}