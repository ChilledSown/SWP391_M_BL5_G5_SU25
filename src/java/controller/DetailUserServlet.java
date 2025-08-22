package controller;

import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

@WebServlet(name = "DetailUserServlet", urlPatterns = {"/detailuser"})
public class DetailUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            response.sendRedirect("manageuser");
            return;
        }

        try {
            long userId = Long.parseLong(userIdStr);
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserById(userId);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("detailuser.jsp").forward(request, response);
            } else {
                response.sendRedirect("manageuser");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("manageuser");
        }
    }
}