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

        if (user == null || user.getUser_id() == null || !"instructor".equalsIgnoreCase(user.getRole())) {

            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        int createdBy = user.getUser_id().intValue();
        BalanceDAO balanceDAO = new BalanceDAO();
        String status = request.getParameter("status");
        String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String searchTerm = request.getParameter("searchTerm");
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
        List<BalanceDTOSeller> transactions = balanceDAO.getTransactions(createdBy, status, fromDate, toDate, searchTerm, page, pageSize);
        int totalTransactions = balanceDAO.getTransactionCount(createdBy, status, fromDate, toDate, searchTerm);
        request.setAttribute("balance", balance);
        request.setAttribute("transactions", transactions);
        request.setAttribute("status", status);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("searchTerm", searchTerm);
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
        if (user == null || user.getUser_id() == null || !"instructor".equalsIgnoreCase(user.getRole())) {
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
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                String searchTerm = request.getParameter("searchTerm");
                if (fromDate != null && !fromDate.isEmpty()) {
                    redirectUrl += "?fromDate=" + URLEncoder.encode(fromDate, "UTF-8");
                }
                if (toDate != null && !toDate.isEmpty()) {
                    redirectUrl += (redirectUrl.contains("?") ? "&" : "?") + "toDate=" + URLEncoder.encode(toDate, "UTF-8");
                }
                if (searchTerm != null && !searchTerm.isEmpty()) {
                    redirectUrl += (redirectUrl.contains("?") ? "&" : "?") + "searchTerm=" + URLEncoder.encode(searchTerm, "UTF-8");
                }
                if (message != null) {
                    redirectUrl += (redirectUrl.contains("?") ? "&" : "?") + "message=" + URLEncoder.encode(message, "UTF-8");
                } else if (errorMessage != null) {
                    redirectUrl += (redirectUrl.contains("?") ? "&" : "?") + "errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
                }
                response.sendRedirect(redirectUrl);
                return;
            } catch (NumberFormatException e) {
                errorMessage = "Invalid order ID.";
                String redirectUrl = request.getContextPath() + "/balance?errorMessage=" + URLEncoder.encode(errorMessage, "UTF-8");
                String fromDate = request.getParameter("fromDate");
                String toDate = request.getParameter("toDate");
                String searchTerm = request.getParameter("searchTerm");
                if (fromDate != null && !fromDate.isEmpty()) {
                    redirectUrl += "&fromDate=" + URLEncoder.encode(fromDate, "UTF-8");
                }
                if (toDate != null && !toDate.isEmpty()) {
                    redirectUrl += "&toDate=" + URLEncoder.encode(toDate, "UTF-8");
                }
                if (searchTerm != null && !searchTerm.isEmpty()) {
                    redirectUrl += "&searchTerm=" + URLEncoder.encode(searchTerm, "UTF-8");
                }
                response.sendRedirect(redirectUrl);
                return;
            }
        }
        doGet(request, response);
    }
}