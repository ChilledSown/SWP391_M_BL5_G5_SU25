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

        // Fetch balance and transactions
        double balance = balanceDAO.getBalance(createdBy);
        List<BalanceDTOSeller> transactions = balanceDAO.getTransactions(createdBy, status, page, pageSize);
        int totalTransactions = balanceDAO.getTransactionCount(createdBy, status);

        // Set attributes for JSP
        request.setAttribute("balance", balance);
        request.setAttribute("transactions", transactions);
        request.setAttribute("status", status);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalTransactions / pageSize));

        // Forward to balance.jsp
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
            try {
                long orderId = Long.parseLong(request.getParameter("orderId"));
                String status = request.getParameter("status");
                if (status != null && (status.equals("pending") || status.equals("completed") || status.equals("cancelled"))) {
                    BalanceDAO balanceDAO = new BalanceDAO();
                    BalanceDTOSeller transaction = balanceDAO.getTransactionByOrderId(orderId, user.getUser_id().intValue());
                    if (transaction != null) {
                        boolean success = balanceDAO.updatePaymentStatus(orderId, status);
                        request.setAttribute("message", success ? "Payment status updated successfully." : "Failed to update payment status.");
                    } else {
                        request.setAttribute("errorMessage", "Transaction not found or you do not have permission to update it.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Invalid status.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid order ID.");
            }
        }

        doGet(request, response);
    }
}