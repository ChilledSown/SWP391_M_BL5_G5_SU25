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
import java.net.URLEncoder;
import java.util.List;

@WebServlet(name = "BalanceServlet", urlPatterns = {"/balance"})
public class BalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"seller".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        int createdBy = user.getUser_id().intValue();
        BalanceDAO balanceDAO = new BalanceDAO();
        String status = request.getParameter("status");
        int page = 1;
        int pageSize = 10;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        double balance = balanceDAO.getBalance(createdBy);
        List<BalanceDTOSeller> transactions = balanceDAO.getTransactions(createdBy, status, page, pageSize);
        int totalTransactions = balanceDAO.getTransactionCount(createdBy, status);
        request.setAttribute("balance", balance);
        request.setAttribute("transactions", transactions);
        request.setAttribute("status", status);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalTransactions / pageSize));
        String message = request.getParameter("message");
        String errorMessage = request.getParameter("errorMessage");
        if (message != null) {
            request.setAttribute("message", message);
        }
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        request.getRequestDispatcher("/instructor_balance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.getUser_id() == null || !"seller".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String action = request.getParameter("action");
        if ("updateStatus".equals(action)) {
            String message = null;
            String errorMessage = null;
            try {
                long orderId = Long.parseLong(request.getParameter("orderId"));
                String status = request.getParameter("status");
                if (status != null && (status.equals("completed") || status.equals("cancelled"))) {
                    BalanceDAO balanceDAO = new BalanceDAO();
                    BalanceDTOSeller transaction = balanceDAO.getTransactionByOrderId(orderId, user.getUser_id().intValue());
                    if (transaction != null) {
                        boolean success = balanceDAO.updatePaymentStatus(orderId, status);
                        if (success) {
                            if ("completed".equals(status)) {
                                message = "Successfully activated.";
                            } else {
                                message = "Status updated successfully.";
                            }
                        } else {
                            errorMessage = "Failed to update payment status.";
                        }
                    } else {
                        errorMessage = "Transaction not found or you do not have permission to update it.";
                    }
                } else {
                    errorMessage = "Invalid status. Only 'completed' or 'cancelled' are allowed.";
                }
                String redirectUrl = request.getContextPath() + "/balance";
                if (message != null) {
                    redirectUrl += "?message=" + URLEncoder.encode(message, "UTF-8");
                } else if (errorMessage != null) {
                    redirectUrl += "?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
                }
                response.sendRedirect(redirectUrl);
                return;
            } catch (NumberFormatException e) {
                errorMessage = "Invalid order ID.";
                response.sendRedirect(request.getContextPath() + "/balance?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8"));
                return;
            }
        }
        doGet(request, response);
    }
}