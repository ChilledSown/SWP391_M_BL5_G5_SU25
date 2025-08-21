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
        if (user == null || !"seller".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int createdBy = user.getUser_id().intValue();
        BalanceDAO balanceDAO = new BalanceDAO();
        
        // Fetch balance and transactions
        double balance = balanceDAO.getBalance(createdBy);
        List<BalanceDTOSeller> transactions = balanceDAO.getTransactions(createdBy);

        // Set attributes for JSP
        request.setAttribute("balance", balance);
        request.setAttribute("transactions", transactions);

        // Forward to balance.jsp
        request.getRequestDispatcher("balance.jsp").forward(request, response);
    }
}