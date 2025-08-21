package controller;

import dal.BalanceDAO;
import dal.UserDAO;
import model.BalanceDTOSeller;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "BalanceDetailServlet", urlPatterns = {"/balanceDetail"})
public class BalanceDetailServletSeller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User seller = (User) session.getAttribute("user");
        if (seller == null || !"seller".equalsIgnoreCase(seller.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String orderId = request.getParameter("orderId");
        if (orderId != null && !orderId.isEmpty()) {
            try {
                BalanceDAO balanceDAO = new BalanceDAO();
                UserDAO userDAO = new UserDAO();

                // Fetch transaction
                BalanceDTOSeller transaction = balanceDAO.getTransactionByOrderId(Long.parseLong(orderId), seller.getUser_id().intValue());
                if (transaction == null) {
                    request.setAttribute("errorMessage", "Transaction not found or you do not have permission to view it.");
                    request.getRequestDispatcher("BalanceDetail.jsp").forward(request, response);
                    return;
                }

                // Fetch buyer
                long buyerId = balanceDAO.getBuyerIdForOrder(Long.parseLong(orderId));
                User buyer = userDAO.getUserById(buyerId);
                if (buyer == null) {
                    request.setAttribute("errorMessage", "Buyer information not found.");
                    request.getRequestDispatcher("BalanceDetail.jsp").forward(request, response);
                    return;
                }

                // Set attributes
                request.setAttribute("transaction", transaction);
                request.setAttribute("buyer", buyer);
                request.getRequestDispatcher("BalanceDetail.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Invalid order ID.");
                request.getRequestDispatcher("BalanceDetail.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Order ID is missing.");
            request.getRequestDispatcher("BalanceDetail.jsp").forward(request, response);
        }
    }
}