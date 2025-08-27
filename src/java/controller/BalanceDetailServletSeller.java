package controller;

import dal.BalanceDAO;
import model.BalanceDTOSeller;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "BalanceDetailServletSeller", urlPatterns = {"/balanceDetail"})
public class BalanceDetailServletSeller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"instructor".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String orderId = request.getParameter("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            try {
                BalanceDAO balanceDAO = new BalanceDAO();
                BalanceDTOSeller transaction = balanceDAO.getTransactionByOrderId(Long.parseLong(orderId), user.getUser_id().intValue());
                if (transaction != null) {
                    User buyer = balanceDAO.getBuyerByOrderId(Long.parseLong(orderId));
                    request.setAttribute("transaction", transaction);
                    request.setAttribute("buyer", buyer);
                    String message = request.getParameter("message");
                    String errorMessage = request.getParameter("errorMessage");
                    if (message != null) {
                        request.setAttribute("message", message);
                    }
                    if (errorMessage != null) {
                        request.setAttribute("errorMessage", errorMessage);
                    }
                } else {
                    request.setAttribute("errorMessage", "Transaction not found or you do not have permission to view it.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid order ID.");
            }
        } else {
            request.setAttribute("errorMessage", "Order ID is missing.");
        }
        request.getRequestDispatcher("/BalanceDetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}